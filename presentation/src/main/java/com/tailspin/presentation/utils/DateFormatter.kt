package com.tailspin.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateFormatter {
        private val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

    fun formatDate(time : Long) : String {
        return dateFormatter.format(Date(time))
    }
}