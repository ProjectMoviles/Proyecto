package com.example.appmedianoche.util

import android.content.Context
import android.content.Intent

const val EXTRA_MESSAGE_ID = "com.example.appmedianoche.DishId"

class Util {
    companion object {
        fun openActivity(
            context: Context,
            classScreen: Class<*>,
            extraName: String = "",
            value: String? = null
        ) {
            val intent = Intent(context, classScreen).apply {
                putExtra(extraName, value)
            }
            context.startActivity(intent)
        }
    }
}