package com.clippy.java.model;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Angel on 07/04/2015.
 */
public class Opciones {
  public static boolean trimSpaces = false;
  public static boolean disableCBListener = false;

  public void loadParams() {
    Properties props = new Properties();
    InputStream is = null;
    // First try loading from the current directory
    try {
      File f = new File("clippy.xml");
      is = new FileInputStream(f);
    } catch (Exception e) {
      is = null;
    }
    try {
      if (is == null) {
        // Try loading from classpath
        is = getClass().getResourceAsStream("clippy.xml");
      }
      // Try loading properties from the file (if found)
      props.loadFromXML(is);
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.toString());
    }
    trimSpaces = Boolean.getBoolean(props.getProperty("trimSpaces", "true"));
    disableCBListener = Boolean.getBoolean(props.getProperty("disableCBListener", "false"));
  }

  public void saveParamChangesAsXML() {
    try {
      Properties props = new Properties();
      props.setProperty("trimSpaces", "" + trimSpaces);
      props.setProperty("disableCBListener", "" + disableCBListener);
      File f = new File("clippy.xml");
      OutputStream out = new FileOutputStream(f);
      props.storeToXML(out, "", "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
