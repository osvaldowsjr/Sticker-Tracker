package com.osvaldo.stickerstracker.utils

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

fun String.removeSpaces(): String {
    return this.replace(" ", "")
}

fun DialogFragment.setFullScreen() {
    dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}