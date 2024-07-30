package com.example.expensetracker.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.Repository.ExpenseRepository
import com.example.expensetracker.data.ExpenseDatabase
import com.example.expensetracker.data.model.ExpenseEntity

class HomeScreenViewModel(private val repository: ExpenseRepository) : ViewModel(){
    val expense = repository.allExpense

    fun getExpense(list: List<ExpenseEntity>): String {
        var totalExpense = 0.0
        list.forEach {
            if (it.type == "Expense") {
                totalExpense += it.amount
            }
        }
        return "- $$totalExpense"
    }

    fun getIncome(list: List<ExpenseEntity>): String {
        var totalIncome = 0.0
        list.forEach {
            if (it.type == "Income") {
                totalIncome += it.amount
            }
        }
        return "+ $$totalIncome"
    }

    fun getTotalBalance(list: List<ExpenseEntity>): String {
        var balance = 0.0
        for (expense in list) {
            if (expense.type == "Income") {
                balance += expense.amount
            } else {
                balance -= expense.amount
            }
        }
        return "$ $balance"
    }
}

class HomeScreenViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            val repository = ExpenseRepository(dao)
            return HomeScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}