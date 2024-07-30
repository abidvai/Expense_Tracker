package com.example.expensetracker.Presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardFinanceItem(title: String, image: Int, titleColor: Color, amount: String, amountColor: Color){
    Column(){
        Row {
            Icon(painter = painterResource(id = image), contentDescription = "income",
                Modifier.size(22.dp), tint = Color.Green)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = title, fontSize = 16.sp, color = titleColor,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = amount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = amountColor,
            modifier = Modifier.padding(start = 8.dp, end = 10.dp)
        )
    }
}