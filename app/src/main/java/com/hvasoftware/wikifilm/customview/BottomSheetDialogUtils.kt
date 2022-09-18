package com.hvasoftware.wikifilm.customview

import android.content.Context
import android.view.View
import android.view.WindowManager
import com.circle.us.customview.StatusBarUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hvasoftware.wikifilm.R


class BottomSheetDialogUtils(private val context: Context) {
    companion object {
        var contentView: View? = null
    }

    private val TAG = "BottomSheetDialogUtils"
    private var mBottomSheetDialog: BottomSheetDialog? =
        BottomSheetDialog(context, R.style.BottomSheetDialog)


    init {
        mBottomSheetDialog?.let { StatusBarUtil.setStatusBarByView(it) }
    }


    interface IClickButton {
        fun onClickOk(number: Int)
        fun onClickCancel()
    }

    interface IPickerLanguage {
        fun onClick(languageCodeTo: String)
    }


    private fun closeBottomDialog() {
        if (mBottomSheetDialog != null && mBottomSheetDialog!!.isShowing) {
            mBottomSheetDialog!!.dismiss()
            mBottomSheetDialog!!.cancel()
            mBottomSheetDialog = null
            contentView = null
        }
    }

//
//    fun showBottomSheetLanguage(iPickerLanguage: IPickerLanguage) {
//        val sheetView = View.inflate(context, R.layout.bs_dialog_choose_language, null)
//        contentView = sheetView
//
//        val rvLanguage = sheetView.findViewById<RecyclerView>(R.id.rvLanguage)
//        val ibClose = sheetView.findViewById<ImageButton>(R.id.ibClose)
//        val mListLanguage: ArrayList<Language> = ArrayList()
//        for (code in TranslateLanguage.getAllLanguages()) {
//            mListLanguage.add(Language(code))
//        }
//        val adapterLanguage = AdapterLanguage(context, itemClickedListener = {
//            iPickerLanguage.onClick(it.code)
//            closeBottomDialog()
//        })
//        rvLanguage.adapter = adapterLanguage
//        adapterLanguage.setData(mListLanguage)
//
//        ibClose.setOnClickListener {
//            closeBottomDialog()
//        }
//
//        mBottomSheetDialog!!.behavior.isDraggable = true
//        mBottomSheetDialog!!.setCancelable(true)
//        mBottomSheetDialog!!.setContentView(sheetView)
//        mBottomSheetDialog!!.showDialog()
//    }

//
//    fun showBottomSheetTranslate(activity: Activity, code: String, content: String) {
//        val sheetView = View.inflate(context, R.layout.bs_dialog_normal, null)
//        contentView = sheetView
//
//        val tvTitle = sheetView.findViewById<TextView>(R.id.tvTitle)
//        val tvMessage = sheetView.findViewById<TextView>(R.id.tvMessage)
//        val pbLoading = sheetView.findViewById<ProgressBar>(R.id.pbLoading)
//        val tvClose = sheetView.findViewById<TextView>(R.id.tvClose)
//
//
//        val options = TranslatorOptions.Builder()
//            .setSourceLanguage(TranslateLanguage.ENGLISH)
//            .setTargetLanguage(code)
//            .build()
//        val translation = Translation.getClient(options)
//        val conditions = DownloadConditions.Builder().requireWifi().build()
//        translation.downloadModelIfNeeded(conditions)
//            .addOnSuccessListener {
//                translation.translate(content)
//                    .addOnSuccessListener { translatedText ->
//                        tvMessage.text = translatedText
//                        pbLoading.visibility = View.GONE
//                    }.addOnFailureListener { exception ->
//                        Log.wtf("translateQuote", exception.message)
//                    }
//            }.addOnFailureListener { exception ->
//                Log.wtf("translateQuote", exception.message)
//            }
//
//
//        tvClose.setOnClickListener {
//            closeBottomDialog()
//        }
//        mBottomSheetDialog!!.behavior.isDraggable = false
//        mBottomSheetDialog!!.setCancelable(false)
//        mBottomSheetDialog!!.setContentView(sheetView)
//        mBottomSheetDialog!!.showDialog()
//    }


    private fun BottomSheetDialog.showDialog() {
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        // Show the dialog with NavBar hidden.
        show()

        // Set the dialog to focusable again.
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }
}