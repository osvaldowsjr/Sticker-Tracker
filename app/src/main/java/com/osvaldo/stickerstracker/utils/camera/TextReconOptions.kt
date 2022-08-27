package com.osvaldo.stickerstracker.utils.camera

import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import java.util.concurrent.Executor

class TextReconOptions : TextRecognizerOptionsInterface {
    override fun getLoggingEventId(): Int {
        return if (this.isThickClient) 24317 else 24306
    }

    override fun getLoggingLanguageOption(): Int {
        return 1
    }

    override fun getCreatorClass(): String {
        return if (!this.isThickClient)
            "com.google.android.gms.vision.text.mlkit.TextRecognizerCreator"
        else
            "com.google.mlkit.vision.text.bundled.latin.BundledLatinTextRecognizerCreator"
    }

    override fun getLoggingLibraryName(): String {
        return if (!this.isThickClient)
            "play-services-mlkit-text-recognition"
        else
            "text-recognition"
    }

    override fun getModuleId(): String {
        return if (!this.isThickClient)
            "com.google.android.gms.vision.ocr"
        else "com.google.mlkit.dynamite.text.latin"
    }

    override fun getExecutor(): Executor? {
        return null
    }

    override fun getIsThickClient(): Boolean {
        return false
    }
}