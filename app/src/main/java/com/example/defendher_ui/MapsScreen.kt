package com.example.defendher_ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

val currentLocation = mutableStateOf(LatLng(10.953551, 75.946148))

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MapViewMain(paddingValues: PaddingValues, nav: NavController) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation.value, 11f)
    }

    if (currentLocation.value.latitude == 10.953551 && currentLocation.value.longitude == 75.946148) {
        val accuracy = Priority.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(LocalContext.current)
        if (ActivityCompat.checkSelfPermission(
                LocalContext.current,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                LocalContext.current,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            RequestLocationPermission(
                onPermissionGranted = {},
                onPermissionDenied = {},
                onPermissionsRevoked = {}
            )
            return
        }
        client.getCurrentLocation(accuracy, CancellationTokenSource().token)
            .addOnSuccessListener { loc ->
                loc?.let {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(it.latitude, it.longitude),
                        11f
                    )
                    currentLocation.value = LatLng(it.latitude, it.longitude)
                    //sseClient.AlertViewModel.getRoadData(it.latitude, it.longitude)
                }
            }
            .addOnFailureListener {
                currentLocation.value = LatLng(10.953551, 75.946148)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(
//                top = paddingValues.calculateTopPadding() - 77.dp,
//                bottom = paddingValues.calculateBottomPadding(),
//            )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            //InfoPopupBoxWithZIndex(nav)
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMyLocationClick = {
                    currentLocation.value = LatLng(it.latitude, it.longitude)
                },
                properties = MapProperties(
                    isTrafficEnabled = true,
                    isBuildingEnabled = true,
                    isIndoorEnabled = true,
                    isMyLocationEnabled = isPermissionGranted(
                        LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    mapType = MapType.NORMAL,
                ),
                uiSettings = MapUiSettings(
                    compassEnabled = true,
                    scrollGesturesEnabled = true,
                    scrollGesturesEnabledDuringRotateOrZoom = true,
                    rotationGesturesEnabled = true,
                    mapToolbarEnabled = false,
                    zoomControlsEnabled = true,
                    zoomGesturesEnabled = true,
                ),
//                mapColorScheme = ComposeMapColorScheme.LIGHT,
//                mergeDescendants = true
            ) {
//                for (road in roadData.roadBlocked) {
//                    Polyline(
//                        points = road.line,
//                        color = Color.Red,
//                        width = 20f,
//                        jointType = JointType.BEVEL
//                    )

//                    Marker(
//                        state = MarkerState(position = road.line[0]),
//                        title = road.streetName,
//                        snippet = "Speed: ${road.speed} km/h",
////                        icon = getCustomBitmapDescriptor(
////                            LocalContext.current,
////                            R.drawable.arrow_cool_down_24dp_e8eaed_fill0_wght400_grad0_opsz24,
////                            Color.Red,
////                            170,
////                            170,
////                        ),
////                        anchor = Offset(0.5f, 1f),
//                    )
                //}
//                for (road in roadData.roadClosed) {
//                    Marker(
//                        state = MarkerState(position = LatLng(road.lat, road.lon)),
//                        title = "Road Closed",
//                        snippet = "Road Closed",
//                        icon = getCustomBitmapDescriptor(
//                            LocalContext.current,
//                            R.drawable.remove_road_24dp_e8eaed_fill0_wght400_grad0_opsz24,
//                            Color.Transparent,
//                            170,
//                            170,
//                        ),
//                        anchor = Offset(0.5f, 1f),
//                    )
//                }
            }
        }
    }
}


fun isPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    )

    LaunchedEffect(key1 = permissionState) {
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size
        val permissionsToRequest = permissionState.permissions.filter {
            !it.hasPermission
        }
        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()
        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}