package com.clippy.java.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class mainController {

  private ArrayList<TitledPane> elementList;

  @FXML
  public Accordion parentOfRepeats;

  public void favoriteThis(ActionEvent event) {
  }

  public void buttonToolBar(ActionEvent event) {
    //TODO por aca el agregar el nuevo titledpane
    //parentOfRepeats.getPanes().add()
  }
}
