package com.example.expensetracker.Presentation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.expensetracker.NavGraph.Routes.Routes
import com.example.expensetracker.R
import com.example.expensetracker.ui.theme.greenColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var activeButton by rememberSaveable {
        mutableStateOf("")
    }
    BottomAppBar(modifier = Modifier.height(100.dp)) {
        IconButton(
            onClick = {
                activeButton = "home"
                navController.navigate(Routes.Home.route){
                    popUpTo(0)
                }
            },
            modifier = Modifier.weight(2f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.home), contentDescription = null,
                Modifier.size(30.dp),
                tint = if (activeButton == "home") greenColor else Color.Black
            )
        }
        IconButton(onClick = {
            activeButton = "profile"
            navController.navigate(Routes.ProfileScreen.route)
        }, modifier = Modifier.weight(2f)) {
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                Modifier
                    .size(30.dp),
                tint = if (activeButton == "profile") greenColor else Color.Black
            )
        }
    }

}
