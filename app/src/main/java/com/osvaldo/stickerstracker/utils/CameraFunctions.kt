package com.osvaldo.stickerstracker.utils

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.util.DisplayMetrics
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.camera.core.*
import androidx.camera.core.Camera
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.osvaldo.stickerstracker.utils.camera.QRCodeAnalyzer
import com.osvaldo.stickerstracker.utils.camera.ScopedExecutor
import com.osvaldo.stickerstracker.utils.camera.TextAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min

abstract class CameraFunctions : Fragment() {

    companion object {
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var scopedExecutor: ScopedExecutor
    var displayId: Int = -1
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null
    private var imageAnalyzer: ImageAnalysis? = null

    fun stopCamera() {
        cameraExecutor.shutdown()
        scopedExecutor.shutdown()
    }

    fun startQRCode(
        previewView: PreviewView,
        mutableLiveData: MutableLiveData<String>,
    ) {
        cameraExecutor = Executors.newSingleThreadExecutor()
        scopedExecutor = ScopedExecutor(cameraExecutor)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            useCaseQrCode(
                previewView,
                mutableLiveData,
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun useCaseQrCode(
        previewView: PreviewView,
        mutableLiveData: MutableLiveData<String>,
    ) {
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = previewView.display.rotation

        val preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(previewView.display.rotation)
            .build()

        imageAnalyzer!!.setAnalyzer(
            cameraExecutor,
            QRCodeAnalyzer(
                lifecycle,
                mutableLiveData,
            )
        )

        startCamera(cameraProvider, preview, previewView)
    }

    fun startAddingSticker(
        previewView: PreviewView,
        mutableLiveData: MutableLiveData<String>,
        DESIRED_WIDTH_CROP_PERCENT: Int,
        DESIRED_HEIGHT_CROP_PERCENT: Int
    ) {
        cameraExecutor = Executors.newSingleThreadExecutor()
        scopedExecutor = ScopedExecutor(cameraExecutor)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            useCaseStickerAdding(
                previewView,
                mutableLiveData,
                DESIRED_WIDTH_CROP_PERCENT,
                DESIRED_HEIGHT_CROP_PERCENT
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun useCaseStickerAdding(
        previewView: PreviewView,
        mutableLiveData: MutableLiveData<String>,
        DESIRED_WIDTH_CROP_PERCENT: Int,
        DESIRED_HEIGHT_CROP_PERCENT: Int
    ) {
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = previewView.display.rotation

        val preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor, TextAnalyzer(
                        lifecycle,
                        mutableLiveData,
                        Pair(DESIRED_WIDTH_CROP_PERCENT, DESIRED_HEIGHT_CROP_PERCENT)
                    )
                )
            }

        startCamera(cameraProvider, preview, previewView)
    }

    private fun startCamera(
        cameraProvider: ProcessCameraProvider,
        preview: Preview,
        previewView: PreviewView
    ) {
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(
            this, cameraSelector, preview, imageAnalyzer
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = ln(max(width, height).toDouble() / min(width, height))
        if (abs(previewRatio - ln(RATIO_4_3_VALUE))
            <= abs(previewRatio - ln(RATIO_16_9_VALUE))
        ) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    fun SurfaceView.setupOverlay(overlayText: String, width: Int, height: Int) {
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                holder?.let {
                    drawOverlay(it, overlayText, width, height)
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
            }

        })
    }

    private fun drawOverlay(
        holder: SurfaceHolder,
        overlayText: String,
        width: Int,
        height: Int
    ) {
        val canvas = holder.lockCanvas()
        val bgPaint = Paint().apply {
            alpha = 140
        }
        canvas.drawPaint(bgPaint)
        val rectPaint = Paint()
        rectPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        rectPaint.style = Paint.Style.FILL
        rectPaint.color = Color.WHITE
        val outlinePaint = Paint()
        outlinePaint.style = Paint.Style.STROKE
        outlinePaint.color = Color.WHITE
        outlinePaint.strokeWidth = 4f
        val surfaceWidth = holder.surfaceFrame.width()
        val surfaceHeight = holder.surfaceFrame.height()

        val cornerRadius = 25f
        val rectTop = surfaceHeight * width / 2 / 100f
        val rectLeft = surfaceWidth * width / 2 / 100f
        val rectRight = surfaceWidth * (1 - width / 2 / 100f)
        val rectBottom = surfaceHeight * (1 - height / 2 / 100f)
        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRoundRect(
            rect, cornerRadius, cornerRadius, rectPaint
        )
        canvas.drawRoundRect(
            rect, cornerRadius, cornerRadius, outlinePaint
        )
        val textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 50F

        val textBounds = Rect()
        textPaint.getTextBounds(overlayText, 0, overlayText.length, textBounds)
        val textX = (surfaceWidth - textBounds.width()) / 2f
        val textY = rectBottom + textBounds.height() + 15f // put text below rect and 15f padding
        canvas.drawText(overlayText, textX, textY, textPaint)
        holder.unlockCanvasAndPost(canvas)
    }

    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }
}