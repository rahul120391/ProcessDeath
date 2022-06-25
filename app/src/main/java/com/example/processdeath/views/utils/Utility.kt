package com.example.processdeath.views.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Utility @Inject constructor(@ApplicationContext  val context:Context){

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

    fun getString(stringId:Int) = context.resources.getString(stringId)

    fun showMessage(){

    }


    fun showDialog(title:String,message:String,context: WeakReference<Context>,onPositiveButtonClick:()->Unit){
        val dialogBuilder = context.get()?.let { MaterialAlertDialogBuilder(it) }
        dialogBuilder?.setTitle(title)?.setMessage(message)?.setPositiveButton("Ok"){
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

}