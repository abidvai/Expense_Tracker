package com.example.firebaseauth

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.AuthStatus
import com.example.expensetracker.ViewModel.AuthViewModel
import com.example.expensetracker.ui.theme.greenColor
import com.example.expensetracker.ui.theme.offWhite

@Composable
fun SignUpPage(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }

    val authState = authViewModel.user.observeAsState()
    val context = LocalContext.current
    var passwordEye by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(greenColor)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 70.dp),
            containerColor = greenColor
        ) { innerPadding ->
            LaunchedEffect(authState.value) {
                when (authState.value) {
                    is AuthStatus.Authenticated -> {
                        navController.navigate(Routes.LoginPage.route)
                    }

                    is AuthStatus.Error -> {
                        Toast.makeText(context, "SignUp Failed Try again.", Toast.LENGTH_SHORT)
                            .show()
                    }

                    AuthStatus.Loading -> {
                        Unit
                    }

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
                    .padding(16.dp)
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

                Text(
                    text = "One Step away\n from Your app",
                    fontSize = 25.sp,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(20.dp))

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
                    },
                    trailingIcon = {
                        val image = if(passwordEye){
                            Icons.Outlined.Lock
                        }else{
                            Icons.Outlined.RemoveRedEye
                        }
                        
                        IconButton(onClick = {
                            passwordEye = !passwordEye
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                )

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
                OutlinedButton(
                    onClick = {
                        authViewModel.signUp(email, password, name, phone, city)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(text = "Sign Up")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Already have an Account?")
                    TextButton(onClick = {
                        navController.navigate(Routes.LoginPage.route)
                    }) {
                        Text(text = "Login")
                    }
                }
            }
        }
    }
}