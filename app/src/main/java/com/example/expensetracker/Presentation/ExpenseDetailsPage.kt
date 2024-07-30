package com.example.expensetracker.Presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ui.theme.greenColor

@Composable
fun ExpenseDetailsPage(
    name: String?,
    amount: String?,
    category: String?,
    type: String?,
    date: String?,
    navController: NavHostController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topBar, topSection, card) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.topbar),
                contentDescription = "topBar",
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
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
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Transaction Details",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(topSection.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints

                    }
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .shadow(16.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (type == "Income") {
                            Image(
                                painter = painterResource(id = R.drawable.rich),
                                contentDescription = null,
                                Modifier.size(120.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.expense_1),
                                contentDescription = null,
                                Modifier.size(120.dp)
                            )
                        }
                        Card(
                            modifier = Modifier
                                .height(60.dp)
                                .width(120.dp)
                                .padding(16.dp),
                            elevation = CardDefaults.elevatedCardElevation(16.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(if (type == "Income") Color.Green else Color.Red),
                                contentAlignment = Alignment.Center
                            ) {
                                type?.let {
                                    Text(text = type)
                                }
                            }
                        }
                        amount?.let {
                            Text(
                                text = "$ $amount",
                                color = if (type == "Income") greenColor else Color.Red
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(20.dp))

                        DetailsSection(
                            name = name,
                            category = category,
                            date = date,
                            type = type,
                            amount = amount
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsSection(
    name: String?,
    category: String?,
    date: String?,
    type: String?,
    amount: String?,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        Text(text = "Transaction Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        if (type != null) {
            DetailsRow(key = "Status", value = type)
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (name != null) {
            DetailsRow(key = "Name", value = name)
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (category != null) {
            DetailsRow(key = "Category", value = category)
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (date != null) {
            DetailsRow(key = "Date", value = date)
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (amount != null) {
            DetailsRow(key = "Amount", value = amount)
        }
        Spacer(modifier = Modifier.height(26.dp))
        OutlinedButton(
            onClick = {
                Toast.makeText(context, "Receipt Downloaded", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Download Receipt")
        }
    }
}

@Composable
fun DetailsRow(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = key, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text(text = value, fontWeight = FontWeight.Light, fontSize = 16.sp)
    }
}
