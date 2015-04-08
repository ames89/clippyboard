package com.clippy.java.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by angel on 28/02/15.
 */

public class ClipBoardListener extends Thread implements ClipboardOwner {
  private Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
  private SimpleStringProperty tempText = new SimpleStringProperty("");
  public EventStream<String> clipboardStream;

  public ClipBoardListener(Clipboard cb) {
    this();
    this.sysClip = cb;
  }

  public ClipBoardListener() {
    if (clipboardStream == null) {
      clipboardStream = EventStreams.nonNullValuesOf(tempText);
    }
    this.run();
  }

  @Override
  public void run() {
    Transferable trans = sysClip.getContents(this);
    TakeOwnership(trans);
  }

  public void lostOwnership(Clipboard c, Transferable t) {
    if (!Opciones.disableCBListener) {
      try {
        ClipBoardListener.sleep(250);  //waiting e.g for loading huge elements like word's etc.
      } catch (Exception e) {
        Logger.getGlobal().log(Level.WARNING, e.getStackTrace().toString());
        System.out.println("Exception: " + e);
      }
      Transferable contents = sysClip.getContents(this);
      try {
        process_clipboard(contents, c);
      } catch (Exception ex) {
        Logger.getLogger(ClipBoardListener.class.getName()).log(Level.SEVERE, null, ex);
      }
      TakeOwnership(contents);
    }
  }

  private void TakeOwnership(Transferable t) {
    sysClip.setContents(t, this);
  }

  private void process_clipboard(Transferable trans, Clipboard c) {
    try {
      if (trans != null && trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String dataClip = (String) trans.getTransferData(DataFlavor.stringFlavor);
        //necesario para transferir los datos de swing a javafx
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            //se pasan los datos de dataclip de awt hacia javafx
            tempText.setValue(Opciones.trimSpaces ? dataClip.trim() : dataClip);
          }
        });
      }
    } catch (Exception e) {
      Logger.getGlobal().log(Level.WARNING, e.getMessage());
    }
  }

  public String getContentString() {
    try {
      return (String) sysClip.getData(DataFlavor.stringFlavor);
    } catch (UnsupportedFlavorException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public synchronized void setContentCB(String txt) {
    sysClip.setContents(new StringSelection(txt), this);
  }
}
