package com.osvaldo.stickerstracker.utils.camera

import android.graphics.Rect
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QRCodeAnalyzer(
    lifecycle: Lifecycle,
    private val result: MutableLiveData<String>,
) : ImageAnalysis.Analyzer {

    private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()

    init {
        lifecycle.addObserver(barcodeScanner)
    }

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        val inputImage =
            InputImage.fromMediaImage(image.image!!, image.imageInfo.rotationDegrees)

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    barcode.rawValue?.let { it1 -> Log.d("TESTE", it1) }
                    result.postValue(barcode.rawValue)
                }
            }
            .addOnFailureListener {
            }.addOnCompleteListener {
                image.close()
            }
    } }