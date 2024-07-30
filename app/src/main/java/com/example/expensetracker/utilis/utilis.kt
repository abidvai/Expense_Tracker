package com.example.expensetracker.utilis

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object utilis {

    fun HumanReadableDate(dateInMillis: Any): String {
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }

    fun DateToMillis(dateString: String): Long {
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return try {
            dateFormatter.parse(dateString)?.time ?: 0L
        } catch (e: ParseException) {
            0L
        }
    }
}