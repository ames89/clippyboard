package com.clippy.java.model;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by angel on 28/02/15.
 */

public class ClipBoardListener extends Thread implements ClipboardOwner {
  Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();


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
      //Logger.getLogger(ClipBoardListener.class.getName()).log(Level.SEVERE, null, ex);
    }
    TakeOwnership(contents);
  }

  void TakeOwnership(Transferable t) {
    sysClip.setContents(t, this);
  }

  public void process_clipboard(Transferable trans, Clipboard c) { //your implementation

    try {
      if (trans != null && trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String tempText = (String) trans.getTransferData(DataFlavor.stringFlavor);
        System.out.println(tempText);
      }
    } catch (Exception e) {
      Logger.getGlobal().log(Level.WARNING, e.getMessage());
    }
  }
}
