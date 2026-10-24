package com.example.appmedianoche.util

import android.content.Context
import android.content.Intent

class Util {
    companion object {
        fun openActivity(context: Context, classScreen: Class<*>) {
            val intent = Intent(context, classScreen)
            context.startActivity(intent)
        }
    }
}