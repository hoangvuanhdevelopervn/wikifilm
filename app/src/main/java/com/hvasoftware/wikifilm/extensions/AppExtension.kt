package com.hvasoftware.wikifilm.extensions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hvasoftware.wikifilm.R
import java.util.*
import kotlin.math.ln
import kotlin.math.pow


internal fun Activity.logger(message: String) {
    Log.d(this.localClassName, "===========================> $message")
}


internal fun Fragment.logger(message: String) {
    Log.d(this.requireActivity().localClassName, "======================> $message")
}


internal fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

internal fun Fragment.toastLong(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


internal fun getUUID(): String {
    return UUID.randomUUID().toString()
}

internal fun getMessageId(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

internal fun Context.copyToClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip =
        ClipData.newPlainText(getString(R.string.app_name), text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
}




internal fun Context.shareText(content: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_TEXT, content)
    intent.type = "text/plain"
    startActivity(Intent.createChooser(intent, "Share To:"))
}

internal fun invalidEmail(content: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(content).matches()
}

internal fun Context.getDeviceId(): String {
    return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
}


internal fun Context.getCountry(): String {
    return Locale.getDefault().country
}

internal fun Context.getDeviceInfo(): String {
    return "SDK: ${Build.VERSION.SDK_INT} MANUFACTURER: ${Build.MANUFACTURER}"
}


internal fun validatePassword(password: String): Boolean {
    return password.isNotEmpty() && password.length > 5
}

fun formatAmountNumber(count: Long): String {
    if (count < 1000) return "" + count
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f%c", count / 1000.0.pow(exp.toDouble()), "kMBTPE"[exp - 1])
}

internal fun Context.getAuthorOccupation(occupation: String): String {
    val firstCharacter = occupation.trim().substring(0, 1).toUpperCase()
    return "$firstCharacter${occupation.trim().substring(1)}"
}


internal fun getDocumentId(content: String): String {
    var documentId = ""
    val arrayContent = content.split(" ")
    for (word in arrayContent) {
        documentId = "$documentId${word.substring(0, 1)}"
    }
    if (documentId.contains("-")) {
        documentId = documentId.replace("-", "")
    }
    return documentId
}