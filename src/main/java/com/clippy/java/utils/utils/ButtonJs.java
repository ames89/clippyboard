package com.clippy.java.utils.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.reactfx.EventStreams;
import org.reactfx.Subscription;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Angel on 13/04/2015.
 */
public class ButtonJs extends Button {

  private class LocalList<E> extends ArrayList<E> {
    @Override
    public String toString() {
      StringBuffer cad = new StringBuffer();
      this.forEach(elem -> {
        cad.append(elem + "\n");
      });
      return cad.toString();
    }
  }

  /**
   * It allows to link the content of the file specified once called this method
   */
  public void assingFile(String filePath) {

    //action when the button is pressed
    Subscription btnClicksub = EventStreams.eventsOf(this, ActionEvent.ACTION).subscribe(evt -> {
      try {
        LocalList<String> lines = new LocalList<String>();
        lines.addAll(Files.readAllLines(FileSystems.getDefault().getPath(filePath)));
        Utils.clipBoardListener.setContentCB(lines.toString());
      } catch (IOException e) {
        Logger.getGlobal().log(Level.WARNING, e.getMessage());
      }
    });
  }

  public ButtonJs() {
    super();
  }
}
