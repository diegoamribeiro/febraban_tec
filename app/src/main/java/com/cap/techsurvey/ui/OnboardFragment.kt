package com.cap.techsurvey.ui

import android.R.id.message
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentOnboardingBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.entities.User
import com.cap.techsurvey.utils.JavaMailAPI
import com.cap.techsurvey.utils.viewBinding
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


class OnboardFragment : Fragment() {


    private val binding: FragmentOnboardingBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

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
            id = 4,
            user = User(
                "Diego Ribeiro",
                "dmribeiro87@gmail.com",
                "Capgemini",
                "Engenharia",
                "Engenheiro",
                "75988593432"
            ),
            questions = listQuestions,
            url = "https://github.com/cjnhust/ebook_collection/blob/master/Kotlin%20in%20Action.pdf"
        )

        val timestamp: String = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault()).format(Date())
        val fileName = "${survey.user.company}_$timestamp.pdf"

        val pdfFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

        val writer = PdfWriter(pdfFile)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
        val normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)

// Adicione os dados da pesquisa ao documento
        document.add(Paragraph("Survey ID: ${survey.id}").setFont(normalFont).setFontSize(12f))

        val title = Paragraph("Name: ${survey.user.name}")
            .setFont(boldFont)
            .setFontSize(16f)
            .setFontColor(ColorConstants.WHITE)
            .setBackgroundColor(ColorConstants.BLUE)
        document.add(title)

        document.add(Paragraph("Email: ${survey.user.email}").setFont(normalFont).setFontSize(12f))
        document.add(Paragraph("Company: ${survey.user.company}").setFont(normalFont).setFontSize(12f))

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

        lifecycleScope.launch(Dispatchers.IO){
//            sendEmail(
//                "dmribeiro87@gmail.com",
//                "Teste",
//                    pdfFile
//                )
        }


        //val url = "https://github.com/cjnhust/ebook_collection/blob/master/Kotlin%20in%20Action.pdf"
        //generateQRCode(url, binding.ivCode)
    }

    private fun setListeners(){
        binding.btStart.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_nav_onboard_to_nav_security)
        }
    }


    private fun sendEmail(email: String, subject: String, file: File) {
        val properties = Properties()
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.starttls.enable", "true")
        properties.put("mail.smtp.host", "smtp.office365.com")
        properties.put("mail.smtp.port", "587")

        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("dinoknot@hotmail.com", "DingomBell@2023")
            }
        })

        val mimeMessage = MimeMessage(session)
        try {
            mimeMessage.setFrom(InternetAddress("dinoknot@hotmail.com"))
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress(email).toString())
            mimeMessage.subject = subject

            // Create a multipart message
            val multipart = MimeMultipart()

            // Part one is the text message
            val messageBodyPart = MimeBodyPart()
            messageBodyPart.setText("Poor Message")
            multipart.addBodyPart(messageBodyPart)

            // Part two is the attachment
            val attachPart = MimeBodyPart()
            val source = FileDataSource(file)
            attachPart.dataHandler = DataHandler(source)
            attachPart.fileName = file.name
            multipart.addBodyPart(attachPart)

            // Add the complete message parts to the message
            mimeMessage.setContent(multipart)

            Transport.send(mimeMessage)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }




}