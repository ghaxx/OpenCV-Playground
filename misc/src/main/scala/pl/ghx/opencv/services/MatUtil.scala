package pl.ghx.opencv.services

import java.io.ByteArrayInputStream
import javafx.scene.image.Image

import org.opencv.core.{Mat, MatOfByte}
import org.opencv.imgcodecs.Imgcodecs

class MatUtil {
  def toJpgArray(m: Mat): Array[Byte] = {
    val memory = new MatOfByte
    try {
      Imgcodecs.imencode(".bmp", m, memory)
      memory.toArray
    } catch {
      case t: Throwable => throw t
//        Imgcodecs.imencode(".bmp", Mat.zeros(m.rows(), m.cols(), m.`type`()), memory)
//        memory.toArray
    }  finally memory.release()
  }

  def toBufferedImage(m: Array[Byte]) = new Image(new ByteArrayInputStream(m))
}
