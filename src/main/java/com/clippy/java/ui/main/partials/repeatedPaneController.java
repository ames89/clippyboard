package com.clippy.java.ui.main.partials;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.reactfx.EventStreams;
import org.reactfx.Subscription;

public class repeatedPaneController {
  @FXML
  public TitledPaneWithCtrl repeatedPane;
  public ToggleButton favoriteBtn;
  public TextArea textArea;
  public Button removeBtn;
  public Button upBtn;
  public Button downBtn;

  public void setData(String data) {
    textArea.setText(data);
    int cant = 30;
    repeatedPane.setText(data.substring(0, data.length() < cant ? data.length() : cant));
  }

  @FXML
  private void initialize() {

    //evento del boton de favorito
    Subscription favoriteSubs = EventStreams.eventsOf(favoriteBtn, ActionEvent.ACTION)
        .subscribe(evt -> {
          if (favoriteBtn.isSelected()) {
            //se le borran a los demas el favorito
            ObservableList<TitledPane> listadoDePaneles = ((Accordion) repeatedPane.getParent()).getPanes();
            listadoDePaneles.remove(repeatedPane);
            listadoDePaneles.add(0, repeatedPane);
            for (int i = 1; i < listadoDePaneles.size(); i++) {
              ((TitledPaneWithCtrl) listadoDePaneles.get(i)).getController().favoriteBtn.setSelected(false);
            }
            repeatedPane.setExpanded(true);
            //TODO por aca
            //TODO favorito, le da prioridad sobre los demas
          }
        });

    //evento del boton de borrado
    Subscription removeSubs = EventStreams.eventsOf(removeBtn, ActionEvent.ACTION).subscribe(evt -> {
      ((Accordion) repeatedPane.getParent()).getPanes().remove(repeatedPane);
    });

    //evento del boton arriba
    Subscription upSubs = EventStreams.eventsOf(upBtn, ActionEvent.ACTION).subscribe(evt -> {
      movePane(-1);
    });

    //evento del boton abajo
    Subscription downSubs = EventStreams.eventsOf(downBtn, ActionEvent.ACTION).subscribe(evt -> {
      movePane(1);
    });
  }

  /**
   * @param change un valor de 1 si baja en la lista o -1 si sube
   */
  private void movePane(int change) {
    ObservableList<TitledPane> listadoDePaneles = ((Accordion) repeatedPane.getParent()).getPanes();
    //consultamos la posicion del elemento
    int pos = listadoDePaneles.indexOf(repeatedPane);
    //extraemos el elemento
    listadoDePaneles.remove(repeatedPane);
    //aumentamos la cantidad de uno para pasarlo a la siguiente posicion
    pos += change;
    //colocamos el elemento de nuevo en la nueva posicion
    if (pos >= 0 && pos < listadoDePaneles.size()) {
      listadoDePaneles.add(pos, repeatedPane);
    } else if (pos < 0) {
      listadoDePaneles.add(pos, repeatedPane);
    } else {
      listadoDePaneles.add(listadoDePaneles.size(), repeatedPane);
    }
    //colocamos el panel que acabamos de mover como el abierto por defecto
    repeatedPane.setExpanded(true);
  }
}
