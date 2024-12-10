package com.example.defendher_ui

import android.annotation.SuppressLint
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun TravelTrackerScreen(fusedLocationClient: FusedLocationProviderClient) {
    val context = LocalContext.current

    // State Variables
    var selectedTravelMode by remember { mutableStateOf("Select Transport Mode") }
    var travelDetails by remember { mutableStateOf("") }
    var companionName by remember { mutableStateOf("") }
    var selectedDestination by remember { mutableStateOf<LatLng?>(null) }
    val mapState = rememberCameraPositionState()

    // Fetch user's current location for initial map view
    LaunchedEffect(Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                mapState.position = CameraPosition.Builder()
                    .target(LatLng(location.latitude, location.longitude))
                    .zoom(15f)
                    .build()
            } else {
                Toast.makeText(context, "Unable to fetch location", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Error accessing location", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0AFFF))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text(
            text = "Travel Mode Selector",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown for selecting transport mode
        TransportModeDropdown(
            selectedMode = selectedTravelMode,
            onModeSelected = { mode ->
                selectedTravelMode = mode
                travelDetails = "" // Reset details when mode changes
                companionName = "" // Reset companion when walking is not selected
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Conditional UI Input for travel details
        when (selectedTravelMode) {
            "Car", "Auto", "Bike", "Walk" -> {
                OutlinedTextField(
                    value = travelDetails,
                    onValueChange = { travelDetails = it },
                    label = { Text("Enter Travel Details") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

        // Companion input only for "Walk" mode
        if (selectedTravelMode == "Walk") {
            OutlinedTextField(
                value = companionName,
                onValueChange = { companionName = it },
                label = { Text("Companion Name (Optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Map Section
        Text(
            text = "Tap to set destination",
            style = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier.padding(8.dp)
        )

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            cameraPositionState = mapState,
            onMapClick = { latLng ->
                selectedDestination = latLng
                Toast.makeText(context, "Destination set at ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                if (selectedDestination == null) {
                    Toast.makeText(context, "Please set a destination on the map", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Submitted!", Toast.LENGTH_SHORT).show()
                    // Handle submission logic here
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA))
        ) {
            Text(text = "Submit", color = Color.White)
        }
    }
}

@Composable
fun TransportModeDropdown(selectedMode: String, onModeSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val travelModes = listOf("Cab", "Auto", "Car", "Bike", "Walk")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedMode,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Transport Mode") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            travelModes.forEach { mode ->
                DropdownMenuItem(onClick = {
                    onModeSelected(mode)
                    expanded = false
                }) {
                    Text(mode)
                }
            }
        }
    }
}
