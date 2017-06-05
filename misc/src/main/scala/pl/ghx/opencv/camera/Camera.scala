package pl.ghx.opencv.camera

import org.opencv.core.{CvType, Mat}
import org.opencv.videoio.{VideoCapture, Videoio}
import org.slf4j.helpers.BasicMarker
import org.slf4j.{LoggerFactory, Marker, MarkerFactory}

class Camera(val info: CameraInfo) {

  val logger = LoggerFactory.getLogger(getClass)
  val capture = new VideoCapture()
  def start() = {
    capture.open(info.openCVIdentifier)
  capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, info.imageWidth)
  capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, info.imageHeight)
  }
  def stop() = {
    logger.info(MarkerFactory.getMarker("" + info.openCVIdentifier), s"Stopping camera ${info.openCVIdentifier}")
    capture.release()
  }
  def get: Mat = {
    var clone = Mat.zeros(info.imageHeight.toInt, info.imageWidth.toInt, CvType.CV_8UC3)
    capture.read(clone)
    clone
  }
}
