package com.example.expensetracker.Presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.AddExpenseViewModel
import com.example.expensetracker.ViewModel.AddExpenseViewModelFactory
import com.example.expensetracker.ViewModel.HomeScreenViewModel
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.utilis.utilis
import com.example.expensetracker.utilis.utilis.isFormValid
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(navController: NavHostController) {

    val viewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)

    val corutine = rememberCoroutineScope()
    Scaffold { innerpadding ->
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topBar, topSection, expenseAddCard) = createRefs()

            Image(painter = painterResource(id = R.drawable.topbar), contentDescription = "topBar",
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, start = 20.dp)
                    .constrainAs(topSection) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "backButton",
                    Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigate(Routes.Home.route)
                        }
                )
                Spacer(modifier = Modifier.width(60.dp))
                Text(
                    text = "Add Expense",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            ExpenseAddCard(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(expenseAddCard) {
                    top.linkTo(topSection.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onAddExpenseClick = {
                    corutine.launch {
                        if (viewModel.addExpense(it)) {
                            navController.popBackStack()
                        }
                    }
                })
        }

    }
}

@Composable
fun ExpenseAddCard(modifier: Modifier, onAddExpenseClick: (model: ExpenseEntity) -> Unit) {

    val name = rememberSaveable {
        mutableStateOf("")
    }
    val amount = rememberSaveable {
        mutableStateOf("")
    }
    val date = rememberSaveable {
        mutableLongStateOf(0L)
    }
    val dateDialogVisible = rememberSaveable {
        mutableStateOf(false)
    }
    val typeSelected = rememberSaveable {
        mutableStateOf("")
    }
    val category = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(
        modifier
            .padding(20.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier) {
            Column {
                Text(text = "Name", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    placeholder = {
                        Text(text = "Balance Title")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Amount", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amount.value,
                    onValueChange = {
                        amount.value = it
                    },
                    placeholder = {
                        Text(text = "Enter Amount")
                    },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Category", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = category.value,
                    onValueChange = {
                        category.value = it
                    },
                    placeholder = {
                        Text(text = "Enter expense category")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Type: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    SuggestionChip(onClick = {
                        typeSelected.value = "Income"
                    }, label = {
                        Text(
                            text = "Income",
                            color = if (typeSelected.value == "Income") Color.Black else Color.Gray
                        )
                    },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (typeSelected.value == "Income") Color.Green else Color.White,
                            labelColor = if (typeSelected.value == "Income") Color.White else Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    SuggestionChip(
                        onClick = { typeSelected.value = "Expense" },
                        label = {
                            Text(
                                text = "Expense",
                                color = if (typeSelected.value == "Expense") Color.Black else Color.Gray
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (typeSelected.value == "Expense") Color.Red else Color.White,
                            labelColor = if (typeSelected.value == "Expense") Color.White else Color.Black
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date Picker", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            dateDialogVisible.value = true
                        },
                    value = if (date.longValue == 0L) "" else utilis.HumanReadableDate(date.longValue),
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Select Date Click here -->>>")
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            dateDialogVisible.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = "dateIcon"
                            )
                        }
                    },
                    readOnly = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (isFormValid(
                                name = name.value,
                                amount = amount.value,
                                category = category.value,
                                type = typeSelected.value,
                                date = date.longValue
                            )
                        ) {
                            val model = ExpenseEntity(
                                id = null,
                                name = name.value,
                                amount.value.toDoubleOrNull() ?: 0.0,
                                date = utilis.HumanReadableDate(date.longValue),
                                category = category.value,
                                type = typeSelected.value
                            )
                            onAddExpenseClick(model)
                            Toast.makeText(context, "Expense Added", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Expense")
                }
            }
            if (dateDialogVisible.value) {
                ExpenseDatePickerDialog(onDateSelected = {
                    date.longValue = it
                    dateDialogVisible.value = false
                },
                    onDismissRequest = {
                        dateDialogVisible.value = false
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(onDateSelected: (date: Long) -> Unit, onDismissRequest: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L

    DatePickerDialog(onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Confirm")
        }
    },
        dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
