package com.cap.techsurvey.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*

class Permissions(val fragment: Fragment) {

    private lateinit var requestPermission: ActivityResultLauncher<Array<String>>
    private var permissionsCurrent = arrayOf("")

    fun registerLocationAndCameraPermission(onSuccess: () -> Unit, onFail: () -> Unit){
        registerPermission(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        ), onSuccess, onFail)
    }

    fun registerLocationPermission(onSuccess: () -> Unit, onFail: () -> Unit){
        registerPermission(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ), onSuccess, onFail)
    }

    fun registerCameraPermission(onSuccess: () -> Unit, onFail: () -> Unit){
        registerPermission(arrayOf(
            Manifest.permission.CAMERA
        ), onSuccess, onFail)
    }

    fun registerPhonePermission(onSuccess: () -> Unit, onFail: () -> Unit){
        registerPermission(arrayOf(
            Manifest.permission.CALL_PHONE
        ), onSuccess, onFail)
    }

    fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission(
                permissionsCurrent
            )
        } else {
            true
        }
    }

    fun getPermission() {
        callPermission(permissionsCurrent)
    }

    private fun registerPermission(permissions: Array<String>, onSuccess: () -> Unit, onFail: () -> Unit) {
        permissionsCurrent = permissions
        requestPermission = fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            for (item in it){
                Log.i("foto", "recebendo status das permissões - "+item.key+" - "+item.value)
            }

            if (hasPermission(permissions)) {
                onSuccess()
            } else {
                onFail()
            }
        }
    }

    private fun callPermission(permissions: Array<String>) {
        requestPermission.launch(permissions)
    }

    private fun hasPermission(permissions: Array<String>): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissions.all {
                ContextCompat.checkSelfPermission(fragment.requireContext(), it) == PackageManager.PERMISSION_GRANTED
            }
        } else {
            true
        }
    }






















    fun requestAllPermissions(requestCode: Int, onPostExecute: () -> Unit) {
        var missingPermissions: Array<String?>? = null
        missingPermissions = filterMissingPermissions(getManifestPermissions())
        if (missingPermissions != null && missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                fragment as Activity,
                missingPermissions,
                requestCode
            )
        } else {
            onPostExecute()
        }
    }

    private fun filterMissingPermissions(permissions: Array<String>?): Array<String?>? {
        var missingPermissions: Array<String?>? = null
        var list: MutableList<String?>? = null
        list = ArrayList()
        if (permissions != null && permissions.isNotEmpty()) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        fragment.requireContext().applicationContext,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    list.add(permission)
                }
            }
        }
        missingPermissions = list.toTypedArray()
        return missingPermissions
    }

    private fun getManifestPermissions(): Array<String>? {
        var permissions: Array<String>? = null
        var info: PackageInfo? = null
        try {
            info = fragment.requireContext().packageManager
                .getPackageInfo(
                    fragment.requireContext().applicationContext.packageName,
                    PackageManager.GET_PERMISSIONS
                )
            permissions = info.requestedPermissions
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("Prime Pay", "Package name not found", e)
        }
        /*if (permissions == null) {
            permissions = arrayOfNulls(0)
        }*/
        return permissions
    }

}