package com.osvaldo.stickerstracker.ui.cameraScan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.osvaldo.stickerstracker.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class CameraActivity : AppCompatActivity(R.layout.camera_activity) {

    private val cameraViewModel: CameraViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CameraFragment.newInstance())
                .commitNow()
        }
    }
}