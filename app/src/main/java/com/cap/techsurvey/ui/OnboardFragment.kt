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
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.JavaMailAPI
import com.cap.techsurvey.utils.viewBinding
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
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
        //binding.dottedProgressbar.visibility = View.VISIBLE
        //extractDataAndSendEmail()
        setListeners()
    }

    private fun setListeners(){
        binding.btStart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_onboard_to_nav_security)
        }
    }

    private fun extractDataAndSendEmail() {
        val surveyProvider = SurveyProvider()

        surveyProvider.getAll().addOnSuccessListener { result ->
            val surveys = result.toObjects(Survey::class.java)

            val csvRows = mutableListOf<List<String>>()
            // Add header
            csvRows.add(listOf("Name", "Email", "Company", "Phone", "Score"))

            // Add data
            for (survey in surveys) {
                csvRows.add(
                    listOf(
                        survey.user.name ?: "N/A",
                        survey.user.email ?: "N/A",
                        survey.user.company ?: "N/A",
                        survey.user.phone ?: "N/A",
                        survey.result?.let { "%.2f".format(it) } ?: "N/A"
                    )
                )
            }

            val outputFile =
                File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/surveys.csv")
            csvWriter().writeAll(csvRows, outputFile)

            // Assuming you want to send the email to the same address every time
            lifecycleScope.launch(Dispatchers.IO) {
                configureEmail("dmribeiro87@gmail.com", "Survey Leads", outputFile)
            }


        }.addOnFailureListener { exception ->
            Log.d("TAG", "Error getting documents: ", exception)
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


}