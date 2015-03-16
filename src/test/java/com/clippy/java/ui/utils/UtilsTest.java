package com.clippy.java.ui.utils;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Tests for {@link com.clippy.java.ui.utils.Utils}.
 *
 * @author aladiah.89@gmail.com (Angel Echavarria)
 */
public class UtilsTest extends TestCase {

  @Test
  public void testClipBoardListener() throws Exception {
    Utils utils = new Utils();
    assertNotNull("Clipboard listener initialized", utils.clipBoardListener);
  }
}
