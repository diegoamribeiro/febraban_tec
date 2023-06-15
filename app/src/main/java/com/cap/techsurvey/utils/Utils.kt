package com.cap.techsurvey.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext


object Utils {

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = activity.currentFocus
        currentFocusView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun ImageView.loadImage(imageUrl: String, viewGroup: ViewGroup) {
        Glide.with(viewGroup)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }

    fun putTelCelMask(text: String): String{
        val number = onlyNumbers(text.trim())
        val mask = if (number.length > 10) {
            "(##) #####-####"
        } else {
            "(##) ####-####"
        }
        return process(mask, number)
    }

    fun isNameValid(name: String): Boolean {
        val nameRegex = "^[A-Z][a-z]*\\s[A-Z][a-z]*$".toRegex()
        return nameRegex.matches(name)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternetConnection(@ApplicationContext appContext: Context): Boolean {

        val connectivityManager: ConnectivityManager = appContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager
            .getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
                else -> {}
            }
        }
        return false
    }
    fun generateQRCode(text: String, imageView: ImageView) {
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) BLACK else WHITE)
                }
            }
            imageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }


    fun hideStatusBar(window: Window){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun onlyNumbers(numberWithMask: String): String {
        return Regex("[^0-9]").replace(numberWithMask, "")
    }

    private fun process(mask: String, text: String): String{
        val number = onlyNumbers(text.trim()).toCharArray()
        var textMask = ""
        var numberIndex = 0
        for (maskItem in mask) {
            if (maskItem != '#') {
                textMask += maskItem
            } else if(numberIndex < number.size) {
                textMask += number[numberIndex]
                numberIndex++
            } else {
                break
            }
        }
        return textMask
    }
}