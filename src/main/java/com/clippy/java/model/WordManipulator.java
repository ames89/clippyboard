package com.clippy.java.model;

import com.clippy.java.utils.utils.TitledPaneWithCtrl;
import javafx.collections.ObservableList;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.ParagraphProperties;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Angel on 08/04/2015.
 */
public class WordManipulator {
  private WordprocessingMLPackage wordMLPackage;
  private ArrayList<String> paragraphs = new ArrayList();

  /**
   * Add the text that belongs to an observable list of titledpanes to the paragraph of
   * the word to be created
   *
   * @param l the observablelist of titledpanes
   */
  public void addItems(ObservableList<TitledPane> l) {
    for (TitledPane tp : l) {
      TitledPaneWithCtrl tpCtrl = (TitledPaneWithCtrl) tp;
      for (int i = 0; i < Opciones.jumpsSplitLine; i++)
        paragraphs.add("");
      paragraphs.add(tpCtrl.getController().getData());
    }
    saveFile();
  }

  private void saveFile() {
    //selector de archivos
    FileChooser fc = new FileChooser();

    //formateador del nombre de archivo
    DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");

    //agregar el nombre del archivo
    fc.setInitialFileName(ZonedDateTime.now().format(format));

    //Agregar los tipos de archivo
    FileChooser.ExtensionFilter doc = new FileChooser.ExtensionFilter("Documento .doc", "*.doc");
    FileChooser.ExtensionFilter docx = new FileChooser.ExtensionFilter("Documento .docx", "*.docx");
    FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("Texto plano .txt", "*.txt");

    //Agregar las extensiones disponibles para los distintos procesos
    fc.getExtensionFilters().add(doc);
    fc.getExtensionFilters().add(docx);
    fc.getExtensionFilters().add(txt);

    //the file where the information will be saved
    File toSave = fc.showSaveDialog(null);

    //se creara el tipo de archivo segun la eleccion del usuario
    if (fc.getSelectedExtensionFilter() == doc) {
      saveDoc(toSave);
    } else if (fc.getSelectedExtensionFilter() == docx) {
      saveDocx(toSave);
    } else if (fc.getSelectedExtensionFilter() == txt) {
      saveTxt(toSave);
    }
  }

  /**
   * Method to save the file by parameters in doc format
   *
   * @param toSave The file where the information will be saved
   */
  private void saveDoc(File toSave) {
    try {
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("down/empty.doc"));
      HWPFDocument doc = new HWPFDocument(fs);
      Range range = doc.getRange();
      Paragraph parContainer = range.insertAfter(new ParagraphProperties(), 0);
      for (String para : paragraphs) {
        parContainer.setSpacingAfter(200);
        parContainer.insertAfter(para);
        parContainer = range.insertAfter(new ParagraphProperties(), 0);
      }
      FileOutputStream fos = new FileOutputStream(toSave);
      doc.write(fos);
      fos.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage() + "\n" + e.getStackTrace());
    }
  }

  /**
   * Method to save the file by parameters in docx format
   *
   * @param toSave The file where the information will be saved
   */
  private void saveDocx(File toSave) {
    try {
      //el documento
      WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

      //el cuerpo principal del documento
      MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

      //se agregan las lineas
      for (String paragraph : paragraphs) {
        mdp.addParagraphOfText(paragraph);
      }

      //guardar el word en el dialogo de archivo
      wordMLPackage.save(toSave);
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage() + "\n" + e.getStackTrace());
    }
  }

  private void saveTxt(File toSave) {
    String alltxt = "";
    for (String paragraph : paragraphs) {
      alltxt += paragraph.isEmpty() ? System.lineSeparator() : paragraph;
    }
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(toSave), "utf-8"))) {
      writer.write(alltxt.toString());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
