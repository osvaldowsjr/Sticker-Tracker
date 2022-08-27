package com.osvaldo.stickerstracker.ui.cameraScan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.osvaldo.stickerstracker.R
import kotlinx.android.synthetic.main.camera_fragment.*

class CameraFragment : CameraFunctions() {
    companion object {
        fun newInstance() = CameraFragment()
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var container: ConstraintLayout
    private lateinit var viewFinder: PreviewView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopCamera()
    }

    override fun onStart() {
        super.onStart()
        setupObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container = view as ConstraintLayout
        viewFinder = container.findViewById(R.id.viewfinder)

        button_stop.setOnClickListener {
            stopCamera()
        }

        button_restart.setOnClickListener {
            restartCamera(viewFinder)
        }

        buttonAdd.setOnClickListener {
            cameraViewModel.addSticker(srcText.text.toString())
        }

        if (allPermissionsGranted()) {
            viewFinder.post {
                displayId = viewFinder.display.displayId
                setUpCamera(viewFinder)
            }
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        overlay.apply {
            setupOverlay()
        }
    }

    private fun setupObserver(){
        cameraViewModel.sourceText.observe(viewLifecycleOwner) { srcText.setText(it) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post {
                    displayId = viewFinder.display.displayId
                    setUpCamera(viewFinder)
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }
}