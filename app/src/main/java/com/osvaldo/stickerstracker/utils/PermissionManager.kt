package com.osvaldo.stickerstracker.utils

import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.osvaldo.stickerstracker.ui.customViews.DialogProvider

abstract class PermissionManager(private val permissionsNeeded: PermissionsNeeded) : Fragment() {

    abstract fun isGranted()

    enum class PermissionsNeeded {
        NEARBY,
        CAMERA
    }

    private fun requestNearby() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requester.launch(Permissions.NEARBY_12UP)
        } else {
            requester.launch(Permissions.NEARBY)
        }
    }

    private fun requestCamera() {
        requester.launch(Permissions.CAMERA)
    }

    private val requester =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            map.forEach {
                val permission = it.key
                val isGranted = it.value
                when {
                    isGranted -> {
                        isGranted()
                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        permission
                    ) -> {
                        DialogProvider.providesAlertDialog(
                            requireContext(),
                            { requestPermission() },
                            { requireActivity().onBackPressedDispatcher.onBackPressed() })
                            .show()
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Falha", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    fun requestPermission() {
        when (permissionsNeeded) {
            PermissionsNeeded.NEARBY -> {
                requestNearby()
            }
            PermissionsNeeded.CAMERA -> {
                requestCamera()
            }
        }
    }
}