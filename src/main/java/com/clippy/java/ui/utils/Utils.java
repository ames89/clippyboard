package com.clippy.java.ui.utils;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Created by angel on 28/02/15.
 */
public class Utils {

  /**
   * Function to create buttons with images
   *
   * @param text   text inside the button
   * @param urlImg relative path of the image
   * @return the new button with an image on the left side
   */
  public static Button buttonCreateImg(String text, String urlImg) {
    Button btn = new Button();
    btn.setText(text);
    btn.setGraphic(new ImageView(urlImg));
    return btn;
  }

}
