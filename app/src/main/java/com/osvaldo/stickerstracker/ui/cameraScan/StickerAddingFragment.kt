package com.osvaldo.stickerstracker.ui.cameraScan

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.CameraFragmentBinding
import com.osvaldo.stickerstracker.utils.CameraFunctions
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StickerAddingFragment : CameraFunctions() {
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val DESIRED_WIDTH_CROP_PERCENT = 75
        const val DESIRED_HEIGHT_CROP_PERCENT = 80
    }

    private val stickerAddingViewModel: StickerAddingViewModel by viewModel()
    private val binding: CameraFragmentBinding by viewBinding(CameraFragmentBinding::bind)

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

        binding.buttonStop.setOnClickListener { stopCamera() }
        binding.toolbar.apply {
            setLastIconVisibility(View.GONE)
            setFirstIconVisibility(View.GONE)
            setBackIconOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.buttonRestart.setOnClickListener {
            setupCamera()
        }

        binding.buttonAdd.setOnClickListener {
            stickerAddingViewModel.addSticker(binding.srcText.text.toString())
        }

        if (allPermissionsGranted()) {
            binding.viewfinder.post {
                displayId = binding.viewfinder.display.displayId
                setupCamera()
            }
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.overlay.apply {
            val overlayText = "Coloque o número da figurinha na caixa"
            setupOverlay(overlayText,DESIRED_WIDTH_CROP_PERCENT, DESIRED_HEIGHT_CROP_PERCENT)
        }
    }

    private fun setupObserver() {
        stickerAddingViewModel.nationName.observe(viewLifecycleOwner) {
            binding.nationName.text = it
        }
        stickerAddingViewModel.stickerNumber.observe(viewLifecycleOwner) {
            binding.stickerId.text = it
        }
        stickerAddingViewModel.isAdded.observe(viewLifecycleOwner) {
            binding.isAdded.visibility = it
        }
        stickerAddingViewModel.sourceText.observe(viewLifecycleOwner) { binding.srcText.setText(it) }
        stickerAddingViewModel.nationEnum.observe(viewLifecycleOwner) {
            binding.imageFlag.setImageResource(it)
        }
        stickerAddingViewModel.isRepeated.observe(viewLifecycleOwner) {
            binding.isRepeated.visibility = it
        }
    }

    private fun setupCamera() {
        startAddingSticker(
            binding.viewfinder, stickerAddingViewModel.sourceText, DESIRED_WIDTH_CROP_PERCENT,
            DESIRED_HEIGHT_CROP_PERCENT
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