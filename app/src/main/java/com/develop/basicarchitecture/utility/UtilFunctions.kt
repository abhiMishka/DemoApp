package com.develop.basicarchitecture.utility

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Preconditions
import com.develop.basicarchitecture.TopApplicationClass
import java.text.SimpleDateFormat
import java.util.*


class UtilFunctions {
    companion object {
        fun toast(message: String) {
            Toast.makeText(TopApplicationClass.getInstance(), message, Toast.LENGTH_SHORT).show()
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}