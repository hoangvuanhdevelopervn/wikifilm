/*
 * Bản quyền thuộc về công ty HVA Software
 */

package com.hvasoftware.wikifilm.customview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.hvasoftware.wikifilm.R


class AlertDialogUtils(val context: Context) {
    private var mDialog: Dialog? = Dialog(context, R.style.DialogEnable)

    interface IAlertDialogNormal {
        fun onOk()
        fun onCancel()
    }

    interface IAlertDialogChooseImage {
        fun onTakePhoto()
        fun onPickImageFromGallery()
    }


    interface IAlertDialogInputEmail {
        fun onOk(emailAddress: String)
        fun onCancel()
    }

    interface IAlertDialogOneButton {
        fun onOk()
    }

    init {
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showDialogNormal(
        title: String,
        message: String,
        okMessage: String,
        cancelMessage: String,
        iAlertDialogNormal: IAlertDialogNormal
    ) {
        mDialog!!.setContentView(R.layout.alert_dialog_normal)
        val tvTitle: TextView = mDialog!!.findViewById(R.id.tvTitle)
        val tvMessage: TextView = mDialog!!.findViewById(R.id.tvMessage)
        val tvOk: TextView = mDialog!!.findViewById(R.id.tvOk)
        val tvCancel: TextView = mDialog!!.findViewById(R.id.tvCancel)
        tvTitle.text = title
        tvMessage.text = message
        tvOk.text = okMessage
        tvCancel.text = cancelMessage

        tvCancel.setOnClickListener {
            iAlertDialogNormal.onCancel()
            closeDialog()
        }

        tvOk.setOnClickListener {
            iAlertDialogNormal.onOk()
            closeDialog()
        }

        mDialog!!.show()
    }


//    fun showDialogOneButton(
//        title: String,
//        message: String,
//        messageButton: String,
//        iAlertDialogHuyHenThanhCong: IAlertDialogOneButton
//    ) {
//        mDialog!!.setContentView(R.layout.alert_dialog_one_button)
//        val tvTitle: TextView = mDialog!!.findViewById(R.id.tvTitle)
//        val tvMessage: TextView = mDialog!!.findViewById(R.id.tvMessage)
//        val tvOk: TextView = mDialog!!.findViewById(R.id.tvOk)
//        tvTitle.text = title
//        tvMessage.text = message
//        tvOk.text = messageButton
//        tvOk.setOnClickListener {
//            iAlertDialogHuyHenThanhCong.onOk()
//            closeDialog()
//        }
//        mDialog!!.show()
//    }
//
//    fun showDialogChoosePickImage(
//            iAlertDialogChooseImage: IAlertDialogChooseImage
//    ) {
//        mDialog!!.setContentView(R.layout.alert_dialog_image)
//        val tvTakePhoto: TextView = mDialog!!.findViewById(R.id.tvTakePhoto)
//        val tvImageGallery: TextView = mDialog!!.findViewById(R.id.tvChooseFile)
//        val tvCancel: TextView = mDialog!!.findViewById(R.id.tvCancel)
//
//        tvTakePhoto.setOnClickListener {
//            iAlertDialogChooseImage.onTakePhoto()
//            closeDialog()
//        }
//
//        tvImageGallery.setOnClickListener {
//            iAlertDialogChooseImage.onPickImageFromGallery()
//            closeDialog()
//        }
//
//        tvCancel.setOnClickListener {
//            closeDialog()
//        }
//        mDialog!!.showDialog()
//    }


    private fun Dialog.showDialog() {
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        // Show the dialog with NavBar hidden.
        show()

        // Set the dialog to focusable again.
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    private fun closeDialog() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.cancel()
        }
    }

}