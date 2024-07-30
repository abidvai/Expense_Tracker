package com.example.expensetracker.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Repository.ExpenseRepository
import com.example.expensetracker.data.ExpenseDatabase
import com.example.expensetracker.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean {
        return try {
            repository.expenseInsert(expenseEntity)
            true
        } catch (ex: Throwable) {
            false
        }
    }

    suspend fun deleteExpense(expenseEntity: ExpenseEntity){
        viewModelScope.launch {
            repository.expenseDelete(expenseEntity)
        }
    }
    suspend fun updateExpense(expenseEntity: ExpenseEntity){
        viewModelScope.launch {
            repository.expenseUpdate(expenseEntity)
        }
    }

    fun getExpenseById(id: Int): Flow<ExpenseEntity?> {
        return repository.getExpenseById(id)
    }

    val allExpense: Flow<List<ExpenseEntity>> = repository.allExpense

}

class AddExpenseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            val repository = ExpenseRepository(dao)
            return AddExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}