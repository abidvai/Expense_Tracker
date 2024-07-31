package com.example.expensetracker.Presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.AddExpenseViewModel
import com.example.expensetracker.ViewModel.AddExpenseViewModelFactory
import com.example.expensetracker.ViewModel.AuthViewModel
import com.example.expensetracker.ViewModel.HomeScreenViewModel
import com.example.expensetracker.ViewModel.HomeScreenViewModelFactory

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {

    val context = LocalContext.current
    val viewModel = HomeScreenViewModelFactory(LocalContext.current).create(HomeScreenViewModel::class.java)
    val addviewModel = AddExpenseViewModelFactory(context).create(AddExpenseViewModel::class.java)

    var activeButton by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        bottomBar = {
           BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (topBar, card, list, nameRow, add) = createRefs()
                Image(painter = painterResource(id = R.drawable.topbar),
                    contentDescription = "top bar",
                    modifier = Modifier.constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 42.dp, start = 16.dp, end = 16.dp)
                        .constrainAs(nameRow) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        Text(
                            text = "Welcome Chef",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    listOf(Color.Red, Color.Blue)
                                )
                            )
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Abdullah Al Abid",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp),
                            color = Color.White,
                            style = TextStyle(
                                shadow = Shadow(
                                    Color.Black,
                                    offset = androidx.compose.ui.geometry.Offset(4f, 4f),
                                    blurRadius = 8f
                                )
                            )
                        )
                    }
                    IconButton(onClick = {
                        Toast.makeText(context, "Your Notification Will Appear Here", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.notificatoin_2),
                            contentDescription = "notification",
                            Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }
                }
                val state = viewModel.expense.collectAsState(initial = emptyList())
                val income = viewModel.getIncome(state.value)
                val expense = viewModel.getExpense(state.value)
                val balance = viewModel.getTotalBalance(state.value)
                CardItems(
                    modifier = Modifier.constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }, income, expense, balance
                )

                TransactionList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(list) {
                            top.linkTo(card.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints

                        },
                    list = state.value,
                    viewModel = addviewModel,
                    navController
                )

                Icon(painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "addButton",
                    modifier = Modifier
                        .constrainAs(add) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(bottom = 18.dp, end = 20.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(Routes.AddExpense.route)
                        }
                        .background(Color.Transparent),
                    tint = Color.Black
                )
            }

        }

    }
}



