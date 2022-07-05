package hoang.nguyenminh.smartexam.util

import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetectorOptionsBase
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

fun buildObjectDetectorOptions(): ObjectDetectorOptionsBase = ObjectDetectorOptions.Builder()
    .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
    .enableClassification()
    .build()

fun newObjectDetector(options: ObjectDetectorOptionsBase) = ObjectDetection.getClient(options)