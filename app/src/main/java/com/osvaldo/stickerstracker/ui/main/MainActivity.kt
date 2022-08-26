package com.osvaldo.stickerstracker.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.ui.stickerslist.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}