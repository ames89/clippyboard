package com.clippy.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("ui/main/mainView.fxml"));
    //setUserAgentStylesheet(STYLESHEET_CASPIAN);
    primaryStage.setTitle("Clippyboard");
    root.getStylesheets().add(getClass().getResource("ui/main/tooltip.css").toExternalForm());
    primaryStage.setScene(new Scene(root, 350, 600));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
