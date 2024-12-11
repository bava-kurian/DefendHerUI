package com.example.defendher_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.defender_ui.R

@Composable
fun MainScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7F6))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()
            Spacer(modifier = Modifier.height(40.dp))
            ButtonsSection(navController)
        }
        SOSButton(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Handle menu */ }) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color(0xFF6A1B9A))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Andria Jacob Toms",
                style = TextStyle(
                    color = Color(0xFF4A148C),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Kochi, Kerala",
                style = TextStyle(color = Color(0xFF6A1B9A), fontSize = 14.sp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8BBD0))
        )
    }
}

@Composable
fun ButtonsSection(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val buttonData = listOf(
            "Travel Tracker" to Screen.TravelTrackerScreen.route,
            "Safe Drive" to "safe_drive_route", // Placeholder for additional routes
            "Safe Rest" to "safe_rest_route"   // Placeholder for additional routes
        )

        buttonData.forEach { (buttonText, route) ->
            Button(
                onClick = { navController.navigate(route) },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFCE93D8)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = buttonText,
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
fun SOSButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Trigger SOS Alert */ },
        modifier = modifier
            .padding(16.dp)
            .size(90.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFD50000)
        )
    ) {
        Text(
            text = "SOS",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
    }
}
