package com.clippy.java.ui.utils;

import com.clippy.java.model.ClipBoardListener;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Created by angel on 28/02/15.
 */
public class Utils {

  /**
   * Variable that listen to the events of changing in the clipboard
   * */
  public static ClipBoardListener clipBoardListener = new ClipBoardListener();

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
