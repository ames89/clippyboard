package com.clippy.java.model;

import com.clippy.java.ui.main.partials.TitledPaneWithCtrl;
import javafx.collections.ObservableList;
import javafx.scene.control.TitledPane;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 * Created by RHO on 08/04/2015.
 */
public class WordManipulator {
  private WordprocessingMLPackage wordMLPackage;
  private String nameFile;

  WordManipulator() {
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
  WordManipulator(String name) {
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
    for (TitledPane tp : l) {
      TitledPaneWithCtrl tpCtrl = (TitledPaneWithCtrl) tp;

    }
  }
}
