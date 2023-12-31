package com.cap.techsurvey.ui.questions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentFinishBinding
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.Utils.visible
import com.cap.techsurvey.utils.viewBinding
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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


class FinishFragment : Fragment() {

    private val binding: FragmentFinishBinding by viewBinding()
    private val args: FinishFragmentArgs by navArgs()
    private val animationDuration = 3000L
    private lateinit var survey: Survey
    private lateinit var surveyProvider: SurveyProvider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatsValues(args.currentSurvey.result!!)
        surveyProvider = SurveyProvider()

        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user,
            result = args.currentSurvey.result,
            questions = args.currentSurvey.questions
        )

        Log.d("***FinishUrl", args.currentSurvey.url.toString())
        Log.d("***FinishScore", args.currentSurvey.toString())
        //generateQRCode(args.currentSurvey.url!!, binding.ivQrcode)

        // Delay for a few seconds, then create the PDF
        Handler(Looper.getMainLooper()).postDelayed({
            createPdfFromView(binding.root, survey)
            binding.btNext.visible()
            binding.tvEmail.visible()
        },  5000)

    }

    private fun setStatsValues(stat: Double) {
        animateText(binding.mtvStats, stat)
        setProgressValues(stat)
        setListeners()
    }

    private fun setListeners(){
        binding.btNext.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_finish_to_nav_thanks)
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

    private fun createPdfFromView(view: View, survey: Survey) {
        view.post {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)

            val pdfDoc = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(view.width, view.height, 1).create()
            val page = pdfDoc.startPage(pageInfo)

            val canvasPdf = page.canvas
            val paint = Paint()
            val matrix = Matrix()

            canvasPdf.drawBitmap(bitmap, matrix, paint)

            val textPaint = Paint().apply {
                color = Color.WHITE
                textSize = 18f
            }

            var textOffsetY = 100f // Increase offset for text to move it up
            textPaint.textSize = 22f
            canvasPdf.drawText("Survey ID: ${survey.id}", 50f, view.height - textOffsetY, textPaint)
            canvasPdf.drawText("Name: ${survey.user.name}", 50f, view.height - (textOffsetY + 20f), textPaint)
            canvasPdf.drawText("Email: ${survey.user.email}", 50f, view.height - (textOffsetY + 40f), textPaint)
            canvasPdf.drawText("Company: ${survey.user.company}", 50f, view.height - (textOffsetY + 60f), textPaint)
            canvasPdf.drawText("Phone: ${survey.user.phone}", 50f, view.height - (textOffsetY + 80f), textPaint)

            textOffsetY += 50f // Adjust this value to change the space

            textPaint.isFakeBoldText = false
            canvasPdf.drawText("jamile.leao@capgemini.com", 50f, view.height - (textOffsetY + 98f), textPaint)
            canvasPdf.drawText("Financial Services", 50f, view.height - (textOffsetY + 117f), textPaint)
            textPaint.isFakeBoldText = true
            textPaint.textSize = 30f
            canvasPdf.drawText("Jamile Leão", 50f, view.height - (textOffsetY + 138f), textPaint)
            textPaint.isFakeBoldText = false
            textPaint.textSize = 30f
            canvasPdf.drawText("Fale com uma especialista:", 50f, view.height - (textOffsetY + 165f), textPaint)

            pdfDoc.finishPage(page)





            val sdf = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault())
            val fileName = "${survey.user.company}_${sdf.format(Date())}.pdf"
            val outputPath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/$fileName"

            try {
                val pdfFile = File(outputPath)
                pdfDoc.writeTo(FileOutputStream(pdfFile))
                pdfDoc.close()

                // Upload PDF to Firebase and update the survey
                val storageRef = Firebase.storage.reference
                val pdfRef = storageRef.child("pdfs/$fileName")

                pdfRef.putFile(Uri.fromFile(pdfFile)).addOnSuccessListener {
                    pdfRef.downloadUrl.addOnSuccessListener { uri ->
                        val url = uri.toString()
                        survey.url = url
                        updateSurvey(survey)
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show()
                }

                // Email properties
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
                    val emailAddresses = arrayOf(
                        survey.user.email,
                        "jamile.leao@capgemini.com",
                    )
                    val recipients = emailAddresses.map { InternetAddress(it) }.toTypedArray()
                    mimeMessage.addRecipients(
                        Message.RecipientType.TO,
                        recipients
                    )
                    mimeMessage.subject = "Survey Result"

                    // Create a multipart message
                    val multipart = MimeMultipart()

                    // Part one is the text message
                    val messageBodyPart = MimeBodyPart()
                    messageBodyPart.setText("Attached is the result of your survey.")
                    multipart.addBodyPart(messageBodyPart)

                    // Part two is the attachment
                    val attachPart = MimeBodyPart()
                    val source = FileDataSource(pdfFile)
                    attachPart.dataHandler = DataHandler(source)
                    attachPart.fileName = pdfFile.name
                    multipart.addBodyPart(attachPart)

                    // Add the complete message parts to the message
                    mimeMessage.setContent(multipart)

                    lifecycleScope.launch(Dispatchers.IO) {
                        Transport.send(mimeMessage)
                    }
                } catch (e: MessagingException) {
                    Log.d("***EmailException", e.toString())
                    e.printStackTrace()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }




    fun configureEmail(email: String, subject: String, file: File) {
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
            messageBodyPart.setText("Você recebeu seu resultado!")
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

    private fun updateSurvey(survey: Survey) {
        surveyProvider.update(survey).addOnSuccessListener {
            Log.d("SurveyUpdate", "Survey updated successfully with new PDF URL")
        }.addOnFailureListener { e ->
            Log.w("SurveyUpdate", "Error updating document", e)
        }
    }



}