package com.example.processdeath.views.extensions

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.processdeath.R


inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }


fun View.visible(visible: Boolean){
    visibility = if(visible){
        View.VISIBLE
    }
    else{
        View.GONE
    }
}

fun View.hideKeyboard(context: Context){
    val manager: InputMethodManager? = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager?
    manager?.hideSoftInputFromWindow(
        windowToken, 0
        )
}

fun View.setLayoutWidth(widthMultiplier:Float, addDimenId:Int = R.dimen._0sdp, subtractDimenId:Int = R.dimen._0sdp){
    val params = layoutParams
    params.width = (Resources.getSystem().displayMetrics.widthPixels* widthMultiplier).toInt().
    plus(context?.resources?.getDimension(addDimenId)?.toInt()?:0).
    minus(context?.resources?.getDimension(subtractDimenId)?.toInt()?:0)
    layoutParams = params
}

fun View.setLayoutHeightUsingWidth(widthMultiplier:Float,addDimenId:Int = R.dimen._0sdp,subtractDimenId:Int = R.dimen._0sdp){
    val params = layoutParams
    params.height = (Resources.getSystem().displayMetrics.widthPixels* widthMultiplier).toInt().
    plus(context?.resources?.getDimension(addDimenId)?.toInt()?:0).
    minus(context?.resources?.getDimension(subtractDimenId)?.toInt()?:0)
    layoutParams = params
}