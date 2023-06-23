package com.cap.techsurvey.ui.questions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionReportBinding
import com.cap.techsurvey.utils.viewBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.layout.Document
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
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


class QuestionReportFragment : Fragment() {

    private val binding: FragmentQuestionReportBinding by viewBinding()
    private val args: QuestionReportFragmentArgs by navArgs()
    private val animationDuration = 3000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendEmail()
        Log.d("***Report", args.currentSurvey.result.toString())
        //organizeData()
        setListeners()
        setStatsValues(args.currentSurvey.result!!)
    }

    private fun setListeners(){

    }

    private fun setStatsValues(stat: Double) {
        animateText(binding.mtvStats, stat)
        setProgressValues(stat)
    }



    private fun drawPdf() {

    }


    private fun sendEmail() {

        val survey = args.currentSurvey

        val timestamp: String = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault()).format(
            Date()
        )
        val fileName = "${survey.user.company}_$timestamp.pdf"

        val pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )

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
        document.add(
            Paragraph("Company: ${survey.user.company}").setFont(normalFont).setFontSize(12f)
        )

//        for (question in survey.questions!!) {
//            document.add(Paragraph("Question ID: ${question.id}").setFont(normalFont).setFontSize(12f))
//            // Para cada opção na questão...
//            for (option in question.options) {
//                val paragraph: Paragraph
//                val darkGreen = DeviceRgb(0, 100, 0)
//                paragraph = if (option.isSelected) {
//                    Paragraph("Resposta: ${option.text}")
//                        .setFont(boldFont)
//                        .setFontSize(12f)
//                        .setFontColor(darkGreen) // Texto em negrito e verde escuro
//                } else {
//                    Paragraph("Resposta: ${option.text}")
//                        .setFont(normalFont)
//                        .setFontSize(12f)
//                }
//                document.add(paragraph)
//            }
//        }

        document.close()

        lifecycleScope.launch(Dispatchers.IO) {
            configureEmail(
                args.currentSurvey.user.email!!,
                "RESULTADO DA PESQUISA",
                pdfFile
            )
        }


        val url = "https://github.com/cjnhust/ebook_collection/blob/master/Kotlin%20in%20Action.pdf"
        //generateQRCode(url, binding.ivCode)


    }

    private fun configureEmail(email: String, subject: String, file: File) {
        val properties = Properties()
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.starttls.enable", "true")
        properties.put("mail.smtp.host", "smtp.office365.com")
        properties.put("mail.smtp.port", "587")

        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("capgemini-febraban-survey@outlook.com", "TechSurvey@2023")
            }
        })

        val mimeMessage = MimeMessage(session)
        try {
            mimeMessage.setFrom(InternetAddress("capgemini-febraban-survey@outlook.com"))
            mimeMessage.addRecipients(
                Message.RecipientType.TO,
                InternetAddress(email).toString()
            )
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


    private fun animateText(mtv: MaterialTextView, statValue: Double) {
        val animator = ValueAnimator.ofFloat(0f, statValue.toFloat())
        animator.addUpdateListener { animation ->
            val animatedFloat = animation.animatedValue as Float
            val animatedValue = if (animatedFloat == 10f) {
                animatedFloat.toInt().toString()
            } else {
                String.format("%.1f", animatedFloat)
            }
            mtv.text = getString(R.string.text_result_stats, animatedValue)
        }
        animator.duration = animationDuration
        animator.start()
    }
    private fun setProgressValues(statValue: Double) {
        val progressIndicator = binding.progressResult
        val progress = statValue.toInt() * 100
        val animator = ObjectAnimator.ofInt(progressIndicator, "progress", progress)
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }



    private fun updateData(){

    }

}