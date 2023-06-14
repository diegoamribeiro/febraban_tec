package com.cap.techsurvey.ui

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cap.techsurvey.databinding.FragmentOnboardingBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.utils.Utils.generateQRCode
import com.cap.techsurvey.utils.viewBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.DeviceRgb


class OnboardFragment : Fragment() {


    private val binding: FragmentOnboardingBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val option1 = listOf<Option>(
            Option(1, "Opção 1", null, isSelected = false),
            Option(2, "Opção 2", null, isSelected = false),
            Option(3, "Opção 3", "Respondeu", isSelected = true),
            Option(4, "Opção 4", null, isSelected = false),
        )
        val option2 = listOf<Option>(
            Option(1, "Opção 1", null, isSelected = false),
            Option(2, "Opção 2", null, isSelected = false),
            Option(3, "Opção 3", "Respondeu", isSelected = true),
            Option(4, "Opção 4", null, isSelected = false),
        )
        val option3 = listOf<Option>(
            Option(1, "Opção 1", null, isSelected = false),
            Option(2, "Opção 2", null, isSelected = false),
            Option(3, "Opção 3", "Respondeu", isSelected = true),
            Option(4, "Opção 4", null, isSelected = false),
        )
        val option4 = listOf<Option>(
            Option(1, "Opção 1", null, isSelected = false),
            Option(2, "Opção 2", null, isSelected = false),
            Option(3, "Opção 3", "Respondeu", isSelected = true),
            Option(4, "Opção 4", null, isSelected = false),
        )

        val listQuestions = listOf<Question>(
            Question(id = 1, text = "Primeira pergunta", options = option1),
            Question(id = 2, text = "Cor preferida", options = option2),
            Question(id = 3, text = "Nome da mãe", options = option3),
            Question(id = 4, text = "Faculdade", options = option4)
        )

        val survey = Survey(
            id = 14,
            name = "Diego Ribeiro",
            email = "dmribeiro87@gmail.com",
            company = "Banco Cora",
            questions = listQuestions,
            url = "https://github.com/cjnhust/ebook_collection/blob/master/Kotlin%20in%20Action.pdf"
        )

        val timestamp: String = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault()).format(Date())
        val fileName = "${survey.company}_$timestamp.pdf"

        val pdfFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

        val writer = PdfWriter(pdfFile)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
        val normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)

// Adicione os dados da pesquisa ao documento
        document.add(Paragraph("Survey ID: ${survey.id}").setFont(normalFont).setFontSize(12f))

        val title = Paragraph("Name: ${survey.name}")
            .setFont(boldFont)
            .setFontSize(16f)
            .setFontColor(ColorConstants.WHITE)
            .setBackgroundColor(ColorConstants.BLUE)
        document.add(title)

        document.add(Paragraph("Email: ${survey.email}").setFont(normalFont).setFontSize(12f))
        document.add(Paragraph("Company: ${survey.company}").setFont(normalFont).setFontSize(12f))



        for (question in survey.questions) {
            document.add(Paragraph("Question ID: ${question.id}").setFont(normalFont).setFontSize(12f))
            document.add(Paragraph("Pergunta: ${question.text}").setFont(boldFont).setFontSize(14f))
            // Para cada opção na questão...
            for (option in question.options) {
                val paragraph: Paragraph
                val darkGreen = DeviceRgb(0, 100, 0)
                paragraph = if (option.isSelected) {
                    Paragraph("Resposta: ${option.text}")
                        .setFont(boldFont)
                        .setFontSize(12f)
                        .setFontColor(darkGreen) // Texto em negrito e verde escuro
                } else {
                    Paragraph("Resposta: ${option.text}")
                        .setFont(normalFont)
                        .setFontSize(12f)
                }
                document.add(paragraph)
            }
        }

        document.close()

        val url = "https://github.com/cjnhust/ebook_collection/blob/master/Kotlin%20in%20Action.pdf"
        //generateQRCode(url, binding.ivCode)
        return binding.root
    }


}