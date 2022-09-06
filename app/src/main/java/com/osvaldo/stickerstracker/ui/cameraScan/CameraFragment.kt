package com.osvaldo.stickerstracker.ui.cameraScan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.CameraFragmentBinding
import com.osvaldo.stickerstracker.utils.viewBinding

class CameraFragment : CameraFunctions() {

    private lateinit var container: ConstraintLayout
    private lateinit var viewFinder: PreviewView
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
        requestPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container = view as ConstraintLayout
        viewFinder = container.findViewById(R.id.viewfinder)

        binding.buttonStop.setOnClickListener { stopCamera() }
        binding.toolbar.apply {
            setBackIconOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.buttonRestart.setOnClickListener { restartCamera(viewFinder) }

        binding.buttonAdd.setOnClickListener {
            cameraViewModel.addSticker(binding.srcText.text.toString())
        }

        binding.overlay.apply {
            setupOverlay()
        }
    }

    private fun setupObserver() {
        cameraViewModel.nationName.observe(viewLifecycleOwner) { binding.nationName.text = it }
        cameraViewModel.stickerNumber.observe(viewLifecycleOwner) { binding.stickerId.text = it }
        cameraViewModel.isAdded.observe(viewLifecycleOwner) { binding.isAdded.visibility = it }
        cameraViewModel.sourceText.observe(viewLifecycleOwner) { binding.srcText.setText(it) }
        cameraViewModel.nationEnum.observe(viewLifecycleOwner) {
            binding.imageFlag.setImageResource(it)
        }
        cameraViewModel.isRepeated.observe(viewLifecycleOwner) {
            binding.isRepeated.visibility = it
        }
    }

    override fun isGaranted() {
        viewFinder.post {
            displayId = viewFinder.display.displayId
            setUpCamera(viewFinder)
        }
    }
}