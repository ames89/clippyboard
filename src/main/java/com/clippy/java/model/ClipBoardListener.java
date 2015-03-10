package com.clippy.java.model;

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

  public ClipBoardListener(Clipboard cb) {
    this.sysClip = cb;
  }

  @Override
  public void run() {
    Transferable trans = sysClip.getContents(this);
    TakeOwnership(trans);
  }

  public void lostOwnership(Clipboard c, Transferable t) {
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

  void TakeOwnership(Transferable t) {
    sysClip.setContents(t, this);
  }

  public void process_clipboard(Transferable trans, Clipboard c) {
    try {
      if (trans != null && trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String tempText = (String) trans.getTransferData(DataFlavor.stringFlavor);
        System.out.println(tempText);
      }
    } catch (Exception e) {
      Logger.getGlobal().log(Level.WARNING, e.getMessage());
    }
    //TODO crear un evento donde se observen los cambios del clibpoard
  }

  public String getContentString() {
    try {
      return (String) sysClip.getData(DataFlavor.stringFlavor);
    } catch (UnsupportedFlavorException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
