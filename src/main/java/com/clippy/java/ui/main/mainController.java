package com.clippy.java.ui.main;

import com.clippy.java.model.Opciones;
import com.clippy.java.model.FileExportCreator;
import com.clippy.java.ui.main.partials.repeatedPaneController;
import com.clippy.java.utils.utils.ButtonJs;
import com.clippy.java.utils.utils.TitledPaneWithCtrl;
import com.clippy.java.utils.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.reactfx.EventStreams;
import org.reactfx.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainController {

  @FXML
  public Accordion parentOfRepeats;
  public Button exportWordBtn;
  public ToggleButton ClipbboardEnabled;
  public Button cleanAll;
  public ToolBar copyButtonList;

  /**
   * Metodo que se ejecuta para inicializar la vista principal
   */
  @FXML
  private void initialize() {
    ClipbboardEnabled.setSelected(!Opciones.disableCBListener);
    ClipbboardEnabled.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(!Opciones.disableCBListener ? "clipenabled.png" : "clipdisabled.png"))));

    /**
     * Boton para activar/desactivar el clipboard
     * */
    Subscription clipEnablerSubs = EventStreams.eventsOf(ClipbboardEnabled, ActionEvent.ACTION)
        .subscribe(evt -> {
          if (ClipbboardEnabled.isSelected()) {
            Opciones.disableCBListener = false;
            ClipbboardEnabled.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("clipenabled.png"))));
          } else {
            Opciones.disableCBListener = true;
            ClipbboardEnabled.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("clipdisabled.png"))));
          }
        });

    /**
     * la accion del boton clearAll que permite borrar todos los elementos en la lista actual
     */
    Subscription cleanAllSubs = EventStreams.eventsOf(cleanAll, ActionEvent.ACTION)
        .subscribe(evt -> {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Confirmar opción de borrado");
          alert.setHeaderText("¿Está seguro de querer borrar el listado de elementos?");
          alert.setContentText("No se podrá deshacer la acción");
          //acciones en formato lambda
          alert
              .showAndWait()//muestra el dialogo y espera respuesta
              .filter(resul -> resul == ButtonType.OK)//filtra que el valor sea OK
              .ifPresent(resp -> parentOfRepeats.getPanes().clear());//realiza la accion
        });

    /**
     * la accion del boton exportWordBtn que permite exportar a word todos los elementos
     * en la lista actual
     */
    Subscription buttonToolBarSubs = EventStreams.eventsOf(exportWordBtn, ActionEvent.ACTION)
        .subscribe(evt -> {
          FileExportCreator wm = new FileExportCreator();
          wm.addItems(parentOfRepeats.getPanes());
        });

    /**
     * Subscripcion al listener del clipboard
     */
    Utils.clipBoardListener.clipboardStream.subscribe(cad -> {
      if (!cad.isEmpty()) {
        for (String porcion : cad.split("(\\n){" + Opciones.jumpsSplitLine + ",}")) {
          if (!porcion.isEmpty())
            parentOfRepeats.getPanes().add(createTitledPane(porcion));
        }
      }
    });

    try {//the scope of the javascript buttons
      //we are instantiating the list that will contains the javascript files
      ArrayList<File> javascripts = new ArrayList<>();
      //here we are joining them on the "javascript" list
      javascripts.addAll(Arrays.asList((new File("./down/")).listFiles((pathname) -> {
        //al files that ends in js
        return pathname.getName().endsWith("js");
      })));
      //now for each file we are going to add them
      javascripts.forEach(file -> {
        ButtonJs btn = new ButtonJs();
        btn.assingFile(file.getPath());
        btn.setText(file.getName());
        copyButtonList.getItems().add(btn);
      });
    } catch (Exception e) {
      Logger.getGlobal().log(Level.INFO, "No existen archivos o carpeta ./down o los archivos no son .js");
    }

    /**
     * @deprecated en funcion de la apertura de un titledpane, al abrirlo pasa a ser el que se llamara en el clipboard
     * evento de cambio en el listado de objetos, se evalua el primero, si cambia, se sustituye el contenido del
     * portapapeles por el nuevo objeto en la posicion 0
     */
    /*parentOfRepeats
        .getPanes()
        .addListener((ListChangeListener<TitledPane>) c -> {
          while (c.next()) {
            if (c.getFrom() == 0 && !parentOfRepeats.getPanes().isEmpty()) {
              Utils.clipBoardListener.setContentCB(
                  ((TitledPaneWithCtrl) c.getList().get(0))
                      .getController().getData());
            }
          }
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
