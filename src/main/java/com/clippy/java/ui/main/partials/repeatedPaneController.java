package com.clippy.java.ui.main.partials;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.reactfx.EventStreams;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.Optional;

public class repeatedPaneController {
  @FXML
  public TitledPaneWithCtrl repeatedPane;
  public ToggleButton favoriteBtn;
  public TextArea textArea;
  public Button removeBtn;
  public Button upBtn;
  public Button downBtn;
  private int cant = 30;//cantidad de texto en el titulo

  public void setData(String data) {
    textArea.setText(data);
    repeatedPane.setText(data.substring(0, data.length() < cant ? data.length() : cant));
  }

  public String getData() {
    return textArea.getText();
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
          }
        });

    //evento del boton de borrado
    Subscription removeSubs = EventStreams.eventsOf(removeBtn, ActionEvent.ACTION).subscribe(evt -> {
      boolean borrar = false;
      if (favoriteBtn.isSelected()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar opción de borrado");
        alert.setHeaderText("¿Está seguro de querer borrar este texto?");
        alert.setContentText("Dicho texto ha sido seleccionado como favorito, por ello se le solicita confirme su borrado");
        Optional<ButtonType> result = alert.showAndWait();
        borrar = (result.get() == ButtonType.OK);
      } else {
        borrar = true;
      }
      if (borrar) {
        ObservableList<TitledPane> panels = ((Accordion) repeatedPane.getParent()).getPanes();
        int pos = panels.indexOf(repeatedPane);
        panels.remove(repeatedPane);
        if (!panels.isEmpty()) {
          panels.get(pos < panels.size() ? pos : panels.size() - 1).setExpanded(true);
        }
      }
    });

    //evento del boton arriba
    Subscription upSubs = EventStreams.eventsOf(upBtn, ActionEvent.ACTION).subscribe(evt -> {
      movePane(-1);
    });

    //evento del boton abajo
    Subscription downSubs = EventStreams.eventsOf(downBtn, ActionEvent.ACTION).subscribe(evt -> {
      movePane(1);
    });

    //eventos de cambio en el texto
    Subscription inputTextAreaSubs = EventStreams.changesOf(textArea.getParagraphs()).reduceSuccessions((oldVal, newVal) -> newVal, Duration.ofMillis(100)).subscribe(evt -> {
      if (evt.next() && evt.wasAdded()) {
        String data = textArea.getText();
        repeatedPane.setText(data.substring(0, data.length() < cant ? data.length() : cant));
      }
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
