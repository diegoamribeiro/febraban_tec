package com.cap.techsurvey.ui

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentOnboardingBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.utils.Utils.generateQRCode
import com.cap.techsurvey.utils.Utils.loadImage
import com.cap.techsurvey.utils.viewBinding
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream(pdfFile))
        document.open()


        // Adiciona os dados da survey ao documento
        document.add(Paragraph("Survey ID: ${survey.id}"))
        document.add(Paragraph("Name: ${survey.name}"))
        document.add(Paragraph("Email: ${survey.email}"))
        document.add(Paragraph("Company: ${survey.company}"))

        for (question in survey.questions) {
            document.add(Paragraph("Question ID: ${question.id}"))
            document.add(Paragraph("Pergunta: ${question.text}"))
            // Para cada opção na questão...
            for (option in question.options) {
                if (option.isSelected){
                    document.add(Paragraph("ID: ${option.id}"))
                    document.add(Paragraph("Alternativa: ${option.text}"))
                    document.add(Paragraph("Resposta: ${option.answer}"))
                }
            }
        }

        document.close()

        val url = "https://github.com/cjnhust/ebook_collection/blob/master/Kotlin%20in%20Action.pdf"
        //generateQRCode(url, binding.ivCode)
        return binding.root
    }











}