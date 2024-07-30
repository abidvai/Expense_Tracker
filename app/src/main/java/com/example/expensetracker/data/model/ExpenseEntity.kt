package com.example.expensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String,
    val amount: Double,
    val category: String,
    val date: String,
    val type: String
)

@Entity(tableName = "profileInfo")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String
)