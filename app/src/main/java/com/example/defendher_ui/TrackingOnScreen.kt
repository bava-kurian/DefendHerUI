package com.example.defendher_ui

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
fun TrackingOnScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You are being tracked",
            style = TextStyle(fontSize = 24.sp, color = Color.Black),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "ETA: 15 minutes",
            style = TextStyle(fontSize = 20.sp, color = Color.Gray),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Here, you can add your map or any other content.
        Text(text = "Map will be displayed here.", style = TextStyle(fontSize = 16.sp))

        Spacer(modifier = Modifier.height(32.dp)) // Space before the button

        // Exit Tracking Button
        Button(
            onClick = {
                navController.navigate(Screen.MainScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA)) // Using the same color scheme
        ) {
            Text(
                text = "EXIT TRACKING",
                color = Color.White,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}
