package com.clippy.java.model;

import java.io.*;
import java.util.Properties;

/**
 * Class that contains static values for configurable Options in the Program
 */
public class Opciones {
  public static boolean trimSpaces = true;
  public static boolean disableCBListener = false;
  public static int jumpsSplitLine = 4;

  public static void loadParams() {
    Properties prop = new Properties();
    InputStream input = null;
    try {
      input = new FileInputStream("clippy.properties");
      // load a properties file
      prop.load(input);
      trimSpaces = Boolean.parseBoolean(prop.getProperty("trimSpaces", "true"));
      disableCBListener = Boolean.parseBoolean(prop.getProperty("disableCBListener", "false"));
      jumpsSplitLine = Integer.parseInt(prop.getProperty("jumpsSplitLine", "4"));
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void saveParamChanges() {
    try {
      Properties props = new Properties();
      //opciones
      props.setProperty("trimSpaces", "" + trimSpaces);
      props.setProperty("disableCBListener", "" + disableCBListener);
      props.setProperty("jumpsSplitLine", "" + jumpsSplitLine);
      //fin opciones
      OutputStream out = new FileOutputStream(new File("clippy.properties"));
      props.store(out, "");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
