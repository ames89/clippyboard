package com.clippy.java;

import com.clippy.java.model.Opciones;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    //Registro de errores en un archivo
    Logger.getGlobal().addHandler(new Handler() {
      @Override public void publish(LogRecord record) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream("Error.log"), "utf-8"))) {
          writer.write(record.getMessage());
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override public void flush() {
      }

      @Override public void close() throws SecurityException {
      }
    });
    //cargar las opciones del archivo
    Opciones.loadParams();

    //crear la vista principal
    Parent root = FXMLLoader.load(getClass().getResource("ui/main/mainView.fxml"));
    //setUserAgentStylesheet(STYLESHEET_CASPIAN);
    primaryStage.setTitle("Clippyboard");
    root.getStylesheets().add(getClass().getResource("ui/main/tooltip.css").toExternalForm());
    primaryStage.setScene(new Scene(root, 350, 600));

    //metodo para almacenar los cambios al cerrar la aplicacion
    primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent event) {
        Opciones.saveParamChanges();
      }
    });

    //mostrar ventana
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
