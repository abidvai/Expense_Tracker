package com.example.expensetracker.NavGraph.Routes

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object AddExpense : Routes("addScreen")
    object DetailsExpense : Routes("detailsExpense/{name}/{amount}/{category}/{type}/{date}") {
        fun createRoute(
            name: String?,
            amount: String?,
            category: String?,
            type: String?,
            date: String?
        ): String {
            val encodedName = name?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "null"
            val encodedAmount = amount?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "null"
            val encodedCategory = category?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "null"
            val encodedType = type?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "null"
            val encodedDate = date?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "null"
            return "detailsExpense/$encodedName/$encodedAmount/$encodedCategory/$encodedType/$encodedDate"
        }

    }
    object ProfileScreen : Routes("profileScreen")
    object UpdateExpense : Routes("update_expense") {
        fun createUpdateRoute(expenseId: Int): String {
            return "update_expense/$expenseId"
        }
    }
}


