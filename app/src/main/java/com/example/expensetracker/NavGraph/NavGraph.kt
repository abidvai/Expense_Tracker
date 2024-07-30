package com.example.expensetracker.NavGraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.Presentation.AddExpenseScreen
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.Presentation.ExpenseDetailsPage
import com.example.expensetracker.Presentation.HomeScreen
import com.example.expensetracker.Presentation.ProfileScreen
import com.example.expensetracker.Presentation.UpdateExpenseScreen
import com.example.expensetracker.ViewModel.AddExpenseViewModel

@Composable
fun NavGraph(viewModel: AddExpenseViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }
        composable(Routes.AddExpense.route) {
            AddExpenseScreen(navController)
        }
        composable(
            Routes.DetailsExpense.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "null"
            val amount = backStackEntry.arguments?.getString("amount") ?: "null"
            val category = backStackEntry.arguments?.getString("category") ?: "null"
            val type = backStackEntry.arguments?.getString("type") ?: "null"
            val date = backStackEntry.arguments?.getString("date") ?: "null"

            ExpenseDetailsPage(
                name = name,
                amount = amount,
                category = category,
                type = type,
                date = date,
                navController = navController
            )
        }
        composable(Routes.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(
            "${Routes.UpdateExpense.route}/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt("expenseId") ?: -1
            UpdateExpenseScreen(navController = navController, viewModel = viewModel, expenseId = expenseId)
        }
    }
}

