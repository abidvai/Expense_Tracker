package com.example.expensetracker.Presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.AddExpenseViewModel
import com.example.expensetracker.ViewModel.HomeScreenViewModel
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.greenColor
import kotlinx.coroutines.launch


@Composable
fun CardItems(modifier: Modifier, income: String, expense: String, balance: String) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(greenColor)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                ) {
                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        Text(
                            text = "Total Balance",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = balance,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.dot3),
                            contentDescription = "menu",
                            Modifier.size(30.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    CardFinanceItem(
                        title = "Income",
                        image = R.drawable.income,
                        titleColor = Color.Green,
                        amount = income,
                        amountColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    CardFinanceItem(
                        title = "Expense",
                        image = R.drawable.expense,
                        titleColor = Color.Yellow,
                        amount = expense,
                        amountColor = MaterialTheme.colorScheme.outlineVariant
                    )

                }
            }

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            offset = DpOffset(x = 170.dp, y = (-100).dp),
            modifier = Modifier.background(greenColor)
        ) {
            DropdownMenuItem(text = { Text(text = "Category") }, onClick = {
                expanded = false
                Toast.makeText(context, "Your Category List will be here", Toast.LENGTH_SHORT)
                    .show()
            })
            Divider()
            DropdownMenuItem(
                text = { Text(text = "Income Statement") },
                onClick = {
                    expanded = false
                    Toast.makeText(context, "Your Income List will be here", Toast.LENGTH_SHORT)
                        .show()
                })
            Divider()
            DropdownMenuItem(
                text = { Text(text = "Expense Statement") },
                onClick = {
                    expanded = false
                    Toast.makeText(context, "Your Expense List will be here", Toast.LENGTH_SHORT)
                        .show()
                })
        }
    }
}


@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    list: List<ExpenseEntity>,
    viewModel: AddExpenseViewModel,
    navController: NavHostController
) {
    val customCorutine = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Recent Transactions", fontSize = 20.sp)
                Text(
                    text = "See All",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        items(list) { expense ->
            expense.id?.let {
                TransactionItems(
                    title = expense.name,
                    amount = "${if (expense.type == "Income") "+" else "-"}${expense.amount}",
                    icon = if (expense.type == "Income") R.drawable.rich else R.drawable.expense_1,
                    date = expense.date,
                    color = if (expense.type == "Income") Color.Green else Color.Red,
                    navController = navController,
                    category = expense.category,
                    type = expense.type,
                    onDelete = {
                        customCorutine.launch {
                            viewModel.deleteExpense(expense)
                        }
                    },
                    id = it
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}
