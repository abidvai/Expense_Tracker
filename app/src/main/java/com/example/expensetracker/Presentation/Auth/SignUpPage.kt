package com.example.firebaseauth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.ViewModel.AuthStatus
import com.example.expensetracker.ViewModel.AuthViewModel

@Composable
fun SignUpPage(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val authState = authViewModel.user.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthStatus.Authenticated -> {
                navController.navigate(Routes.LoginPage.route)
            }

            is AuthStatus.Error -> {
                Toast.makeText(context, "SignUp Failed Try again.", Toast.LENGTH_SHORT).show()
            }

            AuthStatus.Loading -> {
                Unit
            }
            AuthStatus.Unauthenticated -> Unit
            null -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = email, onValueChange = {
            email = it
        },
            label = {
                Text(text = "Email")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = password, onValueChange = {
            password = it
        },
            label = {
                Text(text = "Password")
            })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = name, onValueChange = {
            name = it
        }, label = {
                Text(text = "Name")
            })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = phone, onValueChange = {
            phone = it
        },
            label = {
                Text(text = "Phone")
            })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = city, onValueChange = {
            city = it
        },
            label = {
                Text(text = "City")
            })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(onClick = {
            authViewModel.signUp(email, password, name, phone, city)
        }) {
            Text(text = "Sign Up")
        }
    }
}