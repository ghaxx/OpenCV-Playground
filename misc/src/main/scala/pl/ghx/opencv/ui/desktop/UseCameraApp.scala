package pl.ghx.opencv.ui.desktop

import java.nio.file.Paths
import javafx.application.Application
import javafx.event.{Event, EventHandler}
import javafx.scene.Scene
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.stage.{Stage, WindowEvent}

import org.slf4j.LoggerFactory
import pl.ghx.opencv.camera.CameraService

class UseCameraApp extends Application {
  System.load(Paths.get("dll/opencv_java320.dll").toAbsolutePath.toString)
//  System.load(Paths.get(getClass.getClassLoader.getResource("opencv_java320.dll").toURI).toString)
//  System.loadLibrary("opencv_java320.dll")

  var primaryStage: Stage = _
  val logger = LoggerFactory.getLogger(getClass)

  override def start(stage: Stage) = {
    logger.debug("Starting app")
    primaryStage = stage
    primaryStage.setTitle("Webcam Monitoring")
    primaryStage.setResizable(false)
    // TODO: Create own window decorations
    //        primaryStage.initStyle(StageStyle.UNDECORATED);
    //        Scene scene = new Scene(new HBox(new CameraImageView()));
    val scene = new Scene(new CameraPane)
    logger.debug("Setting main scene")
    primaryStage.setScene(scene)
    logger.debug("Resizing main scene")
    primaryStage.sizeToScene()
    primaryStage.show()
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent] {
      def handle(event: WindowEvent) = stop()
    })
    // Setup close actions
    stage.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent: KeyEvent) => {
      def foo(keyEvent: KeyEvent) = if (keyEvent.getCode eq KeyCode.ESCAPE) stop()
      foo(keyEvent)
    })
  }
  override def stop() = {
    logger.debug("Stopping")
    CameraService.instance.getFirstCamera.stop()
    System.exit(0)
  }
}

object UseCameraApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[UseCameraApp], args: _*)
  }
}