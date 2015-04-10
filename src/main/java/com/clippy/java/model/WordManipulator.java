package com.clippy.java.model;

import com.clippy.java.utils.utils.TitledPaneWithCtrl;
import javafx.collections.ObservableList;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Angel on 08/04/2015.
 */
public class WordManipulator {
  private WordprocessingMLPackage wordMLPackage;
  private String nameFile;

  public WordManipulator() {
    try {
      // Create the package to be worked on
      wordMLPackage = WordprocessingMLPackage.createPackage();
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    }
  }

  /**
   * the constructor method that enable to hold the name of the file to be created
   *
   * @param name the name of the new file
   */
  public WordManipulator(String name) {
    this();
    this.nameFile = name;
  }

  /**
   * Add the text that belongs to an observable list of titledpanes to the paragraph of
   * the word to be created
   *
   * @param l the observablelist of titledpanes
   */
  public void addItems(ObservableList<TitledPane> l) {
    //el cuerpo principal del documento
    MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
    for (TitledPane tp : l) {
      TitledPaneWithCtrl tpCtrl = (TitledPaneWithCtrl) tp;
      for (int i = 0; i < 3; i++)
        mdp.addParagraphOfText("");
      mdp.addParagraphOfText(tpCtrl.getController().getData());
    }
    saveFile();
  }

  public void saveFile() {
    FileChooser fc = new FileChooser();//selector de archivos
    //formateador del nombre de archivo
    DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");
    //agregar el nombre del archivo
    fc.setInitialFileName(ZonedDateTime.now().format(format));
    //agregar el tipo de archivo
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documento Word", "*.docx"));
    try {
      //guardar el word en el dialogo de archivo
      wordMLPackage.save(fc.showSaveDialog(null));
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage() + "\n" + e.getStackTrace());
    }
  }
}
