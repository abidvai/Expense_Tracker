package com.example.expensetracker.Repository

import com.example.expensetracker.data.Dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpense: Flow<List<ExpenseEntity>> = expenseDao.getAll()

    suspend fun expenseInsert(expenseEntity: ExpenseEntity){
        expenseDao.insertExpense(expenseEntity)
    }
    suspend fun expenseUpdate(expenseEntity: ExpenseEntity){
        expenseDao.updateExpense(expenseEntity)
    }
    suspend fun expenseDelete(expenseEntity: ExpenseEntity){
        expenseDao.deleteExpense(expenseEntity)
    }

    fun getExpenseById(id: Int): Flow<ExpenseEntity?> {
        return expenseDao.getExpenseById(id)
    }

}