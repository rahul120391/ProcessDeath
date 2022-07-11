package com.example.processdeath.views.utils

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AlertDialog
import com.example.processdeath.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Utility @Inject constructor(@ApplicationContext  val context:Context,val stringResource: StringResource){

    companion object{
        private const val MIN_PASSWORD_LENGTH = 6
    }

    private var dialog:AlertDialog?=null

    fun isValidEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password:String):Boolean{
        return password.length>= MIN_PASSWORD_LENGTH
    }

    fun showDialog(title:String,message:String,context: WeakReference<Context>,onPositiveButtonClick:()->Unit){
        val dialogBuilder = context.get()?.let { MaterialAlertDialogBuilder(it) }
        dialogBuilder?.setTitle(title)?.setMessage(message)?.setPositiveButton(stringResource.getString(
            R.string.ok)){
            _,_->
            onPositiveButtonClick()
        }?.setNegativeButton("Cancel"){
                _,_->
        }?.setCancelable(false)
        dialog = dialogBuilder?.create()
        if(dialog?.isShowing==false) dialog?.show()
    }

    fun isDialogShowing():Boolean = dialog?.isShowing?:false

    fun dismissDialog(){
        if(dialog?.isShowing==true) dialog?.dismiss()
    }

    data class LanguageMapper(val source:Int,val value:String)
    fun getTransLater(text:String,position:Int,sourceLanguage:String = "en") = callbackFlow {
        val targetLanguage = LocalLanguageChangeHelper.getPersistedData(context, Locale.getDefault().language)
        val options = TranslatorOptions.Builder().setSourceLanguage(sourceLanguage).setTargetLanguage(targetLanguage).build()
        val translator = Translation.getClient(options)
        val task = translator.downloadModelIfNeeded(DownloadConditions.Builder()
            .requireWifi()
            .build()).addOnSuccessListener {
             translator.translate(text).addOnSuccessListener {
                 trySend(LanguageMapper(position,it))
             }.addOnFailureListener {
                 trySend(LanguageMapper(position,text))
             }
        }.addOnFailureListener {
           trySend(LanguageMapper(position,text))
        }
        awaitClose { task.isComplete }
    }

}