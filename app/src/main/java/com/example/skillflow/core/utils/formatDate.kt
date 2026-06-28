package com.example.skillflow.core.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(date: String): String {
    if (date.isBlank()) return ""

    return try {
        val parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)

        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
        val formatted = parsedDate.format(formatter)

        val words = formatted.split(" ")
        if (words.size >= 2) {
            val day = words[0]
            val month = words[1].replaceFirstChar { it.uppercase(Locale("ru")) }
            val year = words[2]
            "$day $month $year"
        } else {
            formatted
        }
    } catch (e: Exception) {
        date
    }
}