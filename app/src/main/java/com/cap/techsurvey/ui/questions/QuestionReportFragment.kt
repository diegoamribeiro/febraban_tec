package com.cap.techsurvey.ui.questions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionReportBinding
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.Utils.generateQRCode
import com.cap.techsurvey.utils.viewBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.colors.DeviceRgb
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
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfContentByte


class QuestionReportFragment : Fragment() {

    private val binding: FragmentQuestionReportBinding by viewBinding()
    private val args: QuestionReportFragmentArgs by navArgs()
    private var totalScore = 0.0
    private lateinit var survey: Survey


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("***ReportSurvey", args.currentSurvey.toString())
        calculateScore()
        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user,
            result = totalScore,
            questions = args.currentSurvey.questions
        )
        drawPdf()
    }

    private fun goToFinish() {
        val action = QuestionReportFragmentDirections.actionNavReportToNavFinish(survey)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun drawPdf() {
        val timestamp: String = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault()).format(Date())
        val fileName = "${survey.user.company}_$timestamp.pdf"
        val pdfFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

        val pdfWriter = PdfWriter(pdfFile)
        val pdfDoc = PdfDocument(pdfWriter)
        val document = Document(pdfDoc)
        val page = pdfDoc.addNewPage()

        val canvas = PdfCanvas(page)

        val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
        val normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)

        // Adding texts to PDF
        canvas.beginText()
        canvas.setFontAndSize(normalFont, 12f)
        canvas.moveText(50.0, page.pageSize.height.toDouble() - 50)
        canvas.showText("Survey ID: ${survey.id}")
        canvas.endText()

        canvas.beginText()
        canvas.setFontAndSize(boldFont, 16f)
        canvas.moveText(50.0, page.pageSize.height.toDouble() - 70)
        canvas.showText("Name: ${survey.user.name}")
        canvas.endText()

        canvas.beginText()
        canvas.setFontAndSize(normalFont, 12f)
        canvas.moveText(50.0, page.pageSize.height.toDouble() - 90)
        canvas.showText("Email: ${survey.user.email}")
        canvas.endText()

        canvas.beginText()
        canvas.setFontAndSize(normalFont, 12f)
        canvas.moveText(50.0, page.pageSize.height.toDouble() - 110)
        canvas.showText("Company: ${survey.user.company}")
        canvas.endText()

        canvas.beginText()
        canvas.setFontAndSize(normalFont, 12f)
        canvas.moveText(50.0, page.pageSize.height.toDouble() - 130)
        canvas.showText("Phone: ${survey.user.phone}")
        canvas.endText()

        // Draw the doughnut chart with the result
        val result = (survey.result ?: 0.0) * 36 // Convert to percentage and scale for degrees
        val centerX = (page.pageSize.width / 2).toDouble()
        val centerY = (page.pageSize.height / 2).toDouble()

        canvas.setFillColor(DeviceRgb(86, 189, 246)) // Using cian_blue color
        canvas.arc(centerX - 100, centerY - 100, centerX + 100, centerY + 100, 0.0, result)
        canvas.lineTo(centerX, centerY)
        canvas.fill()

        // Draw the remaining part
        canvas.setFillColor(DeviceRgb(6, 17, 37)) // Using component_bg color
        canvas.arc(centerX - 100, centerY - 100, centerX + 100, centerY + 100, result, 360.0 - result)
        canvas.lineTo(centerX, centerY)
        canvas.fill()

        // Draw the inner circle to create the "Doughnut" effect
        canvas.setFillColor(DeviceRgb(255, 255, 255)) // Color.WHITE
        canvas.circle(centerX, centerY, 70.0)
        canvas.fill()

        // Add the result text in the center of the doughnut
        val formattedResult = if (survey.result!! > 9.9) {
            Math.round(survey.result!!).toString()
        } else {
            "%.1f".format(survey.result)
        }

        val estimatedWidth = formattedResult.length * 30f / 2
        canvas.beginText()
        canvas.setFontAndSize(boldFont, 30f)
        canvas.setColor(ColorConstants.BLACK, true)
        canvas.moveText(centerX - estimatedWidth / 2 - 20, centerY - 40) // Ajustado aqui
        canvas.showText( "$formattedResult/10")
        canvas.endText()

        pdfDoc.close()

        val storageRef = Firebase.storage.reference
        val pdfRef = storageRef.child("pdfs/$fileName")

        pdfRef.putFile(Uri.fromFile(pdfFile)).addOnSuccessListener {
            pdfRef.downloadUrl.addOnSuccessListener { uri ->
                val url = uri.toString()
                survey.url = url
                updateSurvey(survey)
                goToFinish()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            configureEmail(
                args.currentSurvey.user.email!!,
                "RESULTADO DA PESQUISA",
                pdfFile
            )
            Log.d("***Email","Enviou e-mail para ${args.currentSurvey.user.email}")
        }

    }

    // Envia url do pdf pro firestore
    private fun updateSurvey(survey: Survey) {
        val surveyProvider = SurveyProvider()
        surveyProvider.update(survey).addOnSuccessListener {
            Log.d("SurveyUpdate", "Survey updated successfully with new PDF URL")
        }.addOnFailureListener { e ->
            Log.w("SurveyUpdate", "Error updating document", e)
        }
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
            Log.d("***EmailException", e.toString())
            e.printStackTrace()
        }
    }

    private fun calculateScore() {
        val score1 = args.currentSurvey.questions?.get(0)?.score
        val score2 = args.currentSurvey.questions?.get(1)?.score
        val score3 = args.currentSurvey.questions?.get(2)?.score
        val score4 = args.currentSurvey.questions?.get(3)?.score
        val score5 = args.currentSurvey.questions?.get(4)?.score
        val score6 = args.currentSurvey.questions?.get(5)?.score
        val score7 = args.currentSurvey.questions?.get(6)?.score
        val score8 = args.currentSurvey.questions?.get(7)?.score
        val score9 = args.currentSurvey.questions?.get(8)?.score
        val score10 = args.currentSurvey.questions?.get(9)?.score
        val score11 = args.currentSurvey.questions?.get(10)?.score
        val score12 = args.currentSurvey.questions?.get(11)?.score
        val score13 = args.currentSurvey.questions?.get(12)?.score
        val score14 = args.currentSurvey.questions?.get(13)?.score

        totalScore = ((score1?.times(2) ?: 0.0) +
                (score2?.times(2) ?: 0.0) +
                (score3 ?: 0.0) +
                (score4 ?: 0.0) +
                (score5 ?: 0.0) +
                (score6?.times(2) ?: 0.0) +
                (score7 ?: 0.0) +
                (score8 ?: 0.0) +
                ((score9 ?: 0.0) +
                        (score10 ?: 0.0) +
                        (score11 ?: 0.0) +
                        (score12 ?: 0.0) +
                        (score13 ?: 0.0) +
                        (score14 ?: 0.0)).times(3)) / 2.9

        Log.d("***TotalScore = ", totalScore.toString())
    }


}