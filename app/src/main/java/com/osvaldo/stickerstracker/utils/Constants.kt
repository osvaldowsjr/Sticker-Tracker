package com.osvaldo.stickerstracker.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

class Permissions {
    companion object {
        @RequiresApi(Build.VERSION_CODES.S)
        val NEARBY_12UP = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val NEARBY = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val CAMERA = arrayOf(Manifest.permission.CAMERA)
    }
}

class Constants {
    companion object{
        const val SMOOTHING_DURATION = 50L
    }
}