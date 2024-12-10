package com.example.defendher_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to DefendHer App",
            style = TextStyle(color = Color.Black, fontSize = 22.sp),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = { navController.navigate(Screen.TravelTrackerScreen.route) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB388FF))
        ) {
            Text(text = "Go to Travel Tracker", color = Color.White)
        }

        Button(
            onClick = { /* Placeholder for other navigation logic */ },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB388FF))
        ) {
            Text(text = "Safe History", color = Color.White)
        }
    }
}
