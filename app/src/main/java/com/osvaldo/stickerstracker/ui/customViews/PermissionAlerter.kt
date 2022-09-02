package com.osvaldo.stickerstracker.ui.customViews

import android.content.Context
import androidx.appcompat.app.AlertDialog

object PermissionAlerter {

    fun providesAlertDialog(context:Context,positiveButtonListener:() -> Unit): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle("R.string.perm_request_rationale_title")
            .setMessage("R.string.perm_request_rationale")
            .setPositiveButton("R.string.request_perm_again") { _, _ ->
                positiveButtonListener()
            }
            .setNegativeButton("R.string.dismiss", null)
            .create()
    }
}