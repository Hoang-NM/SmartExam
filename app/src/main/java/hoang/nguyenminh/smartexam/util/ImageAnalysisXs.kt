package hoang.nguyenminh.smartexam.util

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.defaults.PredefinedCategory

private class ImageAnalysisXs : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val options = buildObjectDetectorOptions()
            val objectDetector = newObjectDetector(options)
            objectDetector.process(image).addOnSuccessListener { detectedObjects ->
                for (detectedObject in detectedObjects) {
                    val boundingBox = detectedObject.boundingBox
                    val trackingId = detectedObject.trackingId
                    for (label in detectedObject.labels) {
                        val text = label.text
                        if (PredefinedCategory.FOOD == text) {

                        }
                        val index = label.index
                        if (PredefinedCategory.FOOD_INDEX == index) {

                        }
                        val confidence = label.confidence
                    }
                }
            }.addOnFailureListener { e ->

            }
        }
    }
}