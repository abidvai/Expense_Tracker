package com.example.firebaseauth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.ViewModel.AuthStatus
import com.example.expensetracker.ViewModel.AuthViewModel
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.ui.theme.offWhite

@Composable
fun LoginPage(navController: NavHostController, authViewModel: AuthViewModel) {

    val authState = authViewModel.user.observeAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(greenColor)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 180.dp),
            containerColor = greenColor
        ) { innerPadding ->
            LaunchedEffect(authState.value) {
                when (authState.value) {
                    is AuthStatus.Authenticated -> {
                        navController.navigate(Routes.Home.route)
                    }

                    is AuthStatus.Error -> {
                        Toast.makeText(context, "Login Failed Try again.", Toast.LENGTH_SHORT)
                            .show()
                    }

                    AuthStatus.Loading -> Unit
                    AuthStatus.Unauthenticated -> Unit
                    null -> Unit
                }
            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(offWhite)
                    .padding(top = 20.dp, bottom = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (authState.value) {
                    AuthStatus.Loading -> {
                        CircularProgressIndicator()
                    }

                    AuthStatus.Authenticated -> Unit
                    is AuthStatus.Error -> Unit
                    AuthStatus.Unauthenticated -> Unit
                    null -> Unit
                }
                Text(text = "Login to Your Expense Tracker App\n Save more \n control Your Expense",
                    fontSize = 17.sp,
                    color = Color.Blue)
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = email, onValueChange = {
                    email = it
                },
                    label = {
                        Text(text = "Email")
                    })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = password, onValueChange = {
                    password = it
                },
                    label = {
                        Text(text = "Password")
                    })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(onClick = {
                    authViewModel.logIn(email, password)
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    )
                ) {
                    Text(text = "Login",
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't have any Account?")
                    TextButton(onClick = {
                        navController.navigate(Routes.SignUpPage.route)
                    }) {
                        Text(text = "Sign Up")
                    }
                }
            }
        }
    }
}