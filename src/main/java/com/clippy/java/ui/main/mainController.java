package com.clippy.java.ui.main;

import com.clippy.java.ui.main.partials.repeatedPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainController {

  private ArrayList<TitledPane> elementList;

  @FXML
  public Accordion parentOfRepeats;

  public void favoriteThis(ActionEvent event) {
  }

  public void buttonToolBar(ActionEvent event) {
    //TODO por aca el agregar el nuevo titledpane
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("partials/repeatedPane.fxml"));
    fxmlLoader.setController(new repeatedPaneController());
    try {
      elementList.add(fxmlLoader.load());
    } catch (IOException e) {
      Logger.getGlobal().log(Level.WARNING, "no se cargo correctamente el repeatedPane.fxml");
      e.printStackTrace();
    }
    ObservableList observableList = FXCollections.observableArrayList();
    observableList.addAll(elementList);
    parentOfRepeats.getPanes().addAll(observableList);
  }
}
