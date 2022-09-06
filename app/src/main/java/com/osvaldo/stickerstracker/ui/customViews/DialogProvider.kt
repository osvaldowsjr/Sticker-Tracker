package com.osvaldo.stickerstracker.ui.customViews

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.osvaldo.stickerstracker.R

object DialogProvider {

    fun providesAlertDialog(
        context: Context,
        positiveButtonListener: () -> Unit,
        negativeListener: () -> Unit
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(R.string.perm_request_rationale_title)
            .setMessage(R.string.perm_request_rationale)
            .setPositiveButton(R.string.request_perm_again) { _, _ ->
                positiveButtonListener()
            }
            .setNegativeButton(R.string.dismiss) { _, _ ->
                negativeListener()
            }
            .create()
    }

    fun providesExplanationDialog(
        context: Context,
        negativeListener: () -> Unit
    ): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.explanation)
            .setNeutralButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                negativeListener()
            }
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
            }
    }

    fun providesConnectionDialog(
        context: Context, positiveButtonListener: () -> Unit,
        negativeListener: () -> Unit,
        authDigits : String
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle("Aceitar a conexão")
            .setMessage("Confirme o código nos dois dispositivos: $authDigits")
            .setPositiveButton(
                "Aceitar"
            ) { _: DialogInterface?, _: Int ->
                positiveButtonListener()
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { _: DialogInterface?, _: Int ->
                negativeListener()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .create()
    }
}