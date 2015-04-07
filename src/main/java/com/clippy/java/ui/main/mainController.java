package com.clippy.java.ui.main;

import com.clippy.java.ui.main.partials.TitledPaneWithCtrl;
import com.clippy.java.ui.main.partials.repeatedPaneController;
import com.clippy.java.utils.utils.Utils;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainController {

  @FXML
  public Accordion parentOfRepeats;
  public Button buttonToolBar;

  public void favoriteThis(ActionEvent event) {
  }

  @FXML
  private void initialize() {
    Utils.clipBoardListener.clipboardStream.subscribe(cad -> {
      if (cad.length() != 0) {
        parentOfRepeats.getPanes().add(createTitledPane(cad));
      }
    });

    parentOfRepeats
        .getPanes()
        .addListener(new ListChangeListener<TitledPaneWithCtrl>() {
          @Override public void onChanged(Change<? extends TitledPaneWithCtrl> c) {
            if(c.wasPermutated()) {
              Utils.clipBoardListener.setContentCB(c.getList().get(0).getController().getData());
            }
          }
        });
    /* //acciones del boton "Hola"
    EventStream<ActionEvent> btnToolbar = EventStreams.eventsOf(buttonToolBar, ActionEvent.ACTION);
    Subscription btnSub = btnToolbar.subscribe(ev ->{
      observableList.add(createTitledPane("hola mundo"));//lista de elementos, se le agrega un TitledPane
      parentOfRepeats.getPanes().setAll(observableList);//
    });*/
  }

  public TitledPaneWithCtrl createTitledPane(String cad) {
    //se instancia el nuevo panel
    FXMLLoader repeatedPane = new FXMLLoader(getClass().getResource("partials/repeatedPane.fxml"));
    TitledPaneWithCtrl newTitledPane = null;
    try {
      //se llama a cargar el template
      newTitledPane = (TitledPaneWithCtrl) repeatedPane.load();
    } catch (IOException e) {
      Logger.getGlobal().log(Level.SEVERE, "no se cargo correctamente el repeatedPane.fxml");
      e.printStackTrace();
    }
    //colocar el texto del portapapeles en el nuevo panel
    newTitledPane.setController((repeatedPaneController) repeatedPane.getController());
    newTitledPane.getController().setData(cad);
    return newTitledPane;
  }
}
