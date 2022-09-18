package com.hvasoftware.wikifilm.help

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

object PermissionUtil {

    const val REQUEST_PERMISSION_CAMERA: Int = 1
    const val REQUEST_PERMISSION_WRITE_STORAGE: Int = 2
    const val REQUEST_EXTERNAL_STORAGE_AND_CAMERA: Int = 3
    const val REQUEST_READ_CONTACTS: Int = 4
    const val PERMISSIONS_CAMERA = Manifest.permission.CAMERA
    const val PERMISSIONS_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val PERMISSIONS_READ_CONTACTS = Manifest.permission.READ_CONTACTS


    private fun hasPermissions(
        context: Context?,
        permission: String
    ): Boolean {
        if (context != null) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun checkPermission(
        fragment: Fragment,
        activity: Activity,
        permission: String,
        requestCode: Int
    ): Boolean {
        if (!hasPermissions(activity, permission)) {
            // Do something, when permissions not granted
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                fragment.requestPermissions(
                    arrayOf(permission),
                    requestCode
                )
            } else {
                // Directly request for required permissions, without explanation
                fragment.requestPermissions(
                    arrayOf(permission),
                    requestCode
                )
            }
            return false
        } else {
            // Do something, when permissions are already granted
            return true
        }
    }

    fun checkPermission(
        activity: Activity,
        permission: String,
        requestCode: Int
    ): Boolean {
        if (!hasPermissions(activity, permission)) {
            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    requestCode
                )
            }
            return false
        } else {
            // Do something, when permissions are already granted
            return true
        }
    }

    fun requestPermissionInSetting(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }
}