package com.example.expensetracker.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.expensetracker.NavGraph.NavGraph
import com.example.expensetracker.ViewModel.AddExpenseViewModel
import com.example.expensetracker.ViewModel.AddExpenseViewModelFactory
import com.example.expensetracker.ViewModel.AuthViewModel
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels ()
        setContent {
            ExpenseTrackerTheme {
                val viewModel = AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(viewModel = viewModel, authViewModel = authViewModel)
                }
            }
        }
    }
}
