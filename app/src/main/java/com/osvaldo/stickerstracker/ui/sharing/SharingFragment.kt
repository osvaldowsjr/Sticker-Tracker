package com.osvaldo.stickerstracker.ui.sharing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.ShareFragmentBinding
import com.osvaldo.stickerstracker.utils.CameraFunctions
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SharingFragment : CameraFunctions() {

    val binding: ShareFragmentBinding by viewBinding(ShareFragmentBinding::bind)
    private val sharingViewModel: SharingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.share_fragment, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopCamera()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (allPermissionsGranted()) {
            binding.viewfinder.post {
                displayId = binding.viewfinder.display.displayId
            }
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        if (allPermissionsGranted()) {
            binding.viewfinder.post {
                displayId = binding.viewfinder.display.displayId
                setupCamera()
            }
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun setupCamera() {
        startQRCode(
            binding.viewfinder, sharingViewModel.qrCodeResult
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                binding.viewfinder.post {
                    displayId = binding.viewfinder.display.displayId
                    setupCamera()
                }
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}


