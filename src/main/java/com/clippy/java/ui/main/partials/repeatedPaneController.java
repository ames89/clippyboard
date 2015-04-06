package com.clippy.java.ui.main.partials;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.reactfx.EventStreams;
import org.reactfx.Subscription;

public class repeatedPaneController {
  @FXML
  public TitledPane repeatedPane;
  public ToggleButton favoriteBtn;
  public TextArea textArea;
  public Button removeBtn;
  public Button upBtn;
  public Button downBtn;

  public void setData(String data) {
    textArea.setText(data);
    repeatedPane.setText(data.substring(0, data.length() < 20 ? data.length() : 20));
  }

  @FXML
  private void initialize() {

    //evento del boton de favorito
    Subscription favoriteSubs = EventStreams.eventsOf(favoriteBtn, ActionEvent.ACTION)
        .subscribe(evt -> {
          //TODO favorito, le da prioridad sobre los demas
        });

    //evento del boton de borrado
    Subscription removeSubs = EventStreams.eventsOf(removeBtn, ActionEvent.ACTION).subscribe(evt -> {
      ((Accordion) repeatedPane.getParent()).getPanes().remove(repeatedPane);
    });

    //evento del boton arriba
    Subscription upSubs = EventStreams.eventsOf(upBtn, ActionEvent.ACTION).subscribe(evt -> {
      ObservableList<TitledPane> listadoDePaneles = ((Accordion) repeatedPane.getParent()).getPanes();
      //consultamos la posicion del elemento
      int pos = listadoDePaneles.indexOf(repeatedPane);
      //extraemos el elemento
      listadoDePaneles.remove(repeatedPane);
      //disminuimos la cantidad de uno para pasarlo a la posicion anterior
      pos--;
      //colocamos el elemento de nuevo en la nueva posicion
      listadoDePaneles.add(pos < 0 ? 0 : pos, repeatedPane);
      repeatedPane.setCollapsible(false);
    });

    //evento del boton abajo
    Subscription downSubs = EventStreams.eventsOf(downBtn, ActionEvent.ACTION).subscribe(evt -> {
      ObservableList<TitledPane> listadoDePaneles = ((Accordion) repeatedPane.getParent()).getPanes();
      //consultamos la posicion del elemento
      int pos = listadoDePaneles.indexOf(repeatedPane);
      //extraemos el elemento
      listadoDePaneles.remove(repeatedPane);
      //aumentamos la cantidad de uno para pasarlo a la siguiente posicion
      pos++;
      //colocamos el elemento de nuevo en la nueva posicion
      listadoDePaneles.add(pos > listadoDePaneles.size() ? listadoDePaneles.size() : pos, repeatedPane);
      repeatedPane.setCollapsible(false);
    });
  }
}
