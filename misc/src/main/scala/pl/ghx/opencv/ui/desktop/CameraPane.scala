package pl.ghx.opencv.ui.desktop

import javafx.application.Platform
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{BorderPane, HBox}

import pl.ghx.opencv.camera.CameraService
import pl.ghx.opencv.services.MatUtil


class CameraPane extends BorderPane {
  val camera = CameraService.instance.getFirstCamera
  camera .start()
  val matUtil = new MatUtil
  setPrefSize(camera.info.imageWidth, camera.info.imageHeight)
  val camerasPane = new HBox(10)
  camerasPane.setPrefSize(camera.info.imageWidth, camera.info.imageHeight)


  val imageView = new ImageView()
  val imageProperty = new SimpleObjectProperty[Image]
  imageView.imageProperty.bind(imageProperty)
  imageView.managedProperty.bind(imageView.visibleProperty)
  camerasPane.getChildren.add(imageView)

  Platform.runLater(r)

  def r: Runnable = new Runnable {
    def run(): Unit = {
      val jpgBinaryDAta = matUtil.toJpgArray(camera.get)
      imageProperty.set(matUtil.toBufferedImage(jpgBinaryDAta))
      Thread.sleep(1000/60)

      Platform.runLater(r)
    }
  }

  setCenter(camerasPane)
}
