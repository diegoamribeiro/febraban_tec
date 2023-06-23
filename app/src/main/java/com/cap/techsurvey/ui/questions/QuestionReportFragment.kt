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
        drawPdf()
        Log.d("***Report", args.currentSurvey.toString())
        //organizeData()
        setListeners()
        setStatsValues(args.currentSurvey.result!!)
    }

    private fun setListeners(){
        binding.btNext.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack(R.id.nav_onboard, false)
        }
    }

    private fun setStatsValues(stat: Double) {
        animateText(binding.mtvStats, stat)
        setProgressValues(stat)
    }


    private fun drawPdf() {
        val survey = args.currentSurvey

        val timestamp: String = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault()).format(
            Date()
        )
        val fileName = "${survey.user.company}_$timestamp.pdf"

        val pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )

        val pdfDoc = PdfDocument(PdfWriter(pdfFile))
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

        canvas.beginText()
        canvas.setFontAndSize(normalFont, 12f)
        canvas.moveText(50.0, page.pageSize.height.toDouble() - 150)
        canvas.showText("Result: ${survey.result}")
        canvas.endText()

        // Draw the pie chart with the result
        val result = survey.result ?: 0.0
        val remain = 100.0 - result

        val centerX = (page.pageSize.width / 2).toDouble()
        val centerY = (page.pageSize.height / 2).toDouble()

        // Draw the completed part
        canvas.setFillColor(DeviceRgb(10, 31, 68))
        canvas.arc(centerX - 100, centerY - 100, centerX + 100, centerY + 100, 0.0, (result * 3.6))
        canvas.lineTo(centerX, centerY)
        canvas.fill()

        // Draw the remaining part
        canvas.setFillColor(DeviceRgb(6, 17, 37))
        canvas.arc(centerX - 100, centerY - 100, centerX + 100, centerY + 100, (result * 3.6), (remain * 3.6))
        canvas.lineTo(centerX, centerY)
        canvas.fill()

        pdfDoc.close()

//        val storageRef = Firebase.storage.reference
//        val pdfRef = storageRef.child("pdfs/$fileName")
//
//        pdfRef.putFile(Uri.fromFile(pdfFile)).addOnSuccessListener {
//            pdfRef.downloadUrl.addOnSuccessListener { uri ->
//                val url = uri.toString()
//                survey.url = url
//                updateSurvey(survey) //chamando a função para atualizar a survey
//            }
//        }.addOnFailureListener {
//            // Handle any errors
//        }

        lifecycleScope.launch(Dispatchers.IO) {
            configureEmail(
                args.currentSurvey.user.email!!,
                "RESULTADO DA PESQUISA",
                pdfFile
            )
        }
        //generateQRCode(survey.url!!, binding.ivQrcode)
    }




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


}