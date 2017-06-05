package pl.ghx.opencv.camera

import org.opencv.videoio.{VideoCapture, Videoio}
import org.slf4j.LoggerFactory

class CameraService {
  val logger = LoggerFactory.getLogger(getClass)
  val NUMBER_OF_RETRIES = 3

  lazy val getFirstCamera: Camera = {
    val cameraCounter = 0
    val capture = new VideoCapture()
    capture.open(cameraCounter)
    var retry = 0
    while ( {
      !capture.isOpened && {
        retry += 1;
        retry - 1
      } < NUMBER_OF_RETRIES
    }) {
      Thread.sleep(100)
      capture.open(cameraCounter)
    }
    if (capture.isOpened) {
      var (wx, hx) = (0, 0)
      for {
        (w, h) <- List((640, 480), (800, 600), (1280, 720))
      } yield {
        val wb = capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, w)
        val hb = capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, h)
        logger.debug(s"Testing resolution $w x $h")
        if (wb && wb) {
          wx = w
          hx = h
        }
      }
      val descriptor = CameraInfo(cameraCounter, wx, hx, "")
      logger.info(s"Found camera at $cameraCounter: $descriptor")
      capture.release()
      new Camera(descriptor)
    } else {
      throw new RuntimeException("No camera")
    }
  }
}

object CameraService {
  val instance = new CameraService
}
