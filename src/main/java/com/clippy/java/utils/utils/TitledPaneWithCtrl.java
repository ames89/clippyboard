package com.clippy.java.utils.utils;

import com.clippy.java.ui.main.partials.repeatedPaneController;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;

/**
 * Created by angel on 07/04/2015.
 */
public class TitledPaneWithCtrl extends TitledPane {
  private repeatedPaneController controller;

  public TitledPaneWithCtrl() {
    super();
    controller = null;
  }

  public TitledPaneWithCtrl(repeatedPaneController controller) {
    super();
    this.controller = controller;
  }

  public TitledPaneWithCtrl(String title, Node content) {
    super(title, content);
  }

  public repeatedPaneController getController() {
    return controller;
  }

  public void setController(repeatedPaneController controller) {
    this.controller = controller;
  }
}
