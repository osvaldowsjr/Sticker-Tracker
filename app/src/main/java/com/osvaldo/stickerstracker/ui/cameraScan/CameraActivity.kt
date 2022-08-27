package com.osvaldo.stickerstracker.ui.cameraScan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.osvaldo.stickerstracker.R

class CameraActivity : AppCompatActivity(R.layout.camera_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CameraFragment.newInstance())
                .commitNow()
        }
    }
}