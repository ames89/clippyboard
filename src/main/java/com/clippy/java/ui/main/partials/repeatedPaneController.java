package com.clippy.java.ui.main.partials;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;

public class repeatedPaneController {
  @FXML
  public TitledPane repeatedPane;

  @FXML
  public TextArea textArea;

  @FXML
  public ToggleButton favorite;

  public void favoriteThis(ActionEvent event) {
    System.out.println(favorite.isSelected());
  }

  public void setData(String data){
    textArea.setText(data);
    repeatedPane.setText(data.substring(0,19));
  }

  @FXML
  private void initialize(){

  }
}
