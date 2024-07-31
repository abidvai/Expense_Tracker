package com.example.expensetracker.Presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.AuthStatus
import com.example.expensetracker.ViewModel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {
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
                val (topBar, photoFame) = createRefs()
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
                        .constrainAs(photoFame) {
                            top.linkTo(topBar.top)
                            start.linkTo(topBar.start)
                            end.linkTo(topBar.end)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .shadow(16.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(16.dp)

                    ) {
                        val authState = authViewModel.user.observeAsState()

                        LaunchedEffect(authState.value) {
                            when(authState.value){
                                is AuthStatus.Error -> Unit
                                AuthStatus.Loading -> Unit
                                AuthStatus.Unauthenticated -> {
                                    navController.navigate(Routes.LoginPage.route)
                                }
                                null -> Unit
                                AuthStatus.Authenticated -> Unit
                            }
                        }

                        Text(text = "Profile Section will be Implement soon")
                        OutlinedButton(onClick = {
                            authViewModel.signOut()
                        }) {
                            Text(text = "LogOut")
                        }
                }
            }
        }
    }
}}