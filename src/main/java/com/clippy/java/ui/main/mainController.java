package com.clippy.java.ui.main;

import com.clippy.java.ui.main.partials.repeatedPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;
import org.reactfx.Subscription;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainController {

  private ObservableList<TitledPane> observableList = FXCollections.observableArrayList();

  @FXML
  public Accordion parentOfRepeats;

  @FXML
  public Button buttonToolBar;

  public void favoriteThis(ActionEvent event) {
  }

  /*public mainController() {
  }*/

  @FXML
  private void initialize() {
    EventStream<ActionEvent> btnToolbar = EventStreams.eventsOf(buttonToolBar, ActionEvent.ACTION);
    Subscription btnSub = btnToolbar.subscribe(event -> {
      observableList.add(createTitledPane());
      parentOfRepeats.getPanes().setAll(observableList);
    });
  }

  public TitledPane createTitledPane() {
    FXMLLoader repeatedPane = new FXMLLoader(getClass().getResource("partials/repeatedPane.fxml"));
    TitledPane newTitledPane = null;
    try {
      newTitledPane = (TitledPane) repeatedPane.load();
    } catch (IOException e) {
      Logger.getGlobal().log(Level.SEVERE, "no se cargo correctamente el repeatedPane.fxml");
      e.printStackTrace();
    }
    repeatedPaneController ctrl = (repeatedPaneController) repeatedPane.getController();
    return newTitledPane;
  }
}
