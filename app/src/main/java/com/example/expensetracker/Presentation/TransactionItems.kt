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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.AddExpenseViewModel
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.utilis.utilis
import kotlinx.coroutines.launch

@Composable
fun TransactionItems(
    title: String,
    amount: String,
    icon: Int,
    date: String,
    color: Color,
    navController: NavHostController,
    category: String,
    type: String,
    onDelete: () -> Unit,
    id: Int,
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable {
                expanded = true
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "income",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, fontSize = 16.sp)
                Text(text = date, fontSize = 12.sp)
            }
            Text(
                text = amount,
                fontSize = 16.sp,
                color = color,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 200.dp, y = 0.dp),
            modifier = Modifier.background(greenColor)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "Show Details",
                        fontSize = 20.sp,
                        color = Color.Yellow
                    )
                },
                onClick = {
                    expanded = false
                    navController.navigate(Routes.DetailsExpense.createRoute(
                        name = title ?: "",
                        amount = amount ?: "",
                        category = category ?: "",
                        type = type ?: "",
                        date = date ?: ""
                    ))
                }
            )
            Divider()
            DropdownMenuItem(
                text = {
                    Text(
                        "Update",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                onClick = {
                    expanded = false
                    navController.navigate(Routes.UpdateExpense.createUpdateRoute(id))
                }
            )
            Divider()
            DropdownMenuItem(
                text = {
                    Text(
                        "Delete",
                        fontSize = 20.sp,
                        color = Color.Red
                    )
                },
                onClick = {
                    expanded = false
                    onDelete()
                }
            )
        }
    }
}


@Composable
fun UpdateExpenseScreen(
    navController: NavHostController,
    viewModel: AddExpenseViewModel,
    expenseId: Int
) {
    val expense by viewModel.getExpenseById(expenseId).collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }

    val dateDialogVisible = rememberSaveable { mutableStateOf(false) }
    val dateMillis = rememberSaveable {
        mutableLongStateOf(expense?.date?.let { utilis.DateToMillis(it) } ?: System.currentTimeMillis())
    }
    val context = LocalContext.current
    val typeSelected = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold { innerpadding ->
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topBar, topSection, card) = createRefs()

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
                            navController.navigate(Routes.Home.route) {
                                popUpTo(0)
                            }
                        }
                )
                Spacer(modifier = Modifier.width(60.dp))
                Text(
                    text = "Update Expense",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            LaunchedEffect(expense) {
                expense?.let {
                    name = it.name
                    amount = it.amount.toString()
                    date = it.date
                    category = it.category
                }
            }

            Column(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(topSection.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(20.dp)
                    .fillMaxWidth()
                    .shadow(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(text = "Name", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Amount", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = date,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { dateDialogVisible.value = true },
                    trailingIcon = {
                        IconButton(onClick = { dateDialogVisible.value = true }) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = "dateIcon"
                            )
                        }
                    },
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Category", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    modifier = Modifier.fillMaxWidth()
                )
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
                Button(
                    onClick = {
                        if(name.isNotBlank() && amount.isNotBlank() && date.isNotBlank() && category.isNotBlank()){
                            coroutineScope.launch {
                                expense?.let {
                                    viewModel.updateExpense(
                                        it.copy(
                                            name = name,
                                            amount = amount.toDoubleOrNull() ?: 0.0,
                                            date = utilis.HumanReadableDate(dateMillis.longValue),
                                            category = category,
                                            type = typeSelected.value
                                        )
                                    )
                                }
                                navController.popBackStack()
                                Toast.makeText(context,"Expense Updated Successfully",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = greenColor
                    )
                ) {
                    Text("Update")
                }

                if (dateDialogVisible.value) {
                    ExpenseDatePickerDialogUpdate(
                        initialDateMillis = dateMillis.longValue,
                        onDateSelected = { selectedMillis ->
                            dateMillis.longValue = selectedMillis
                            date = utilis.HumanReadableDate(selectedMillis)
                            dateDialogVisible.value = false
                        },
                        onDismissRequest = {
                            dateDialogVisible.value = false
                        }
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialogUpdate(
    onDateSelected: (date: Long) -> Unit,
    onDismissRequest: () -> Unit,
    initialDateMillis: Long
) {
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    DatePickerDialog(onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = {
            onDateSelected(
                datePickerState.selectedDateMillis ?: 0L
            )
        }) {
            Text(text = "Confirm")
        }
    },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}



