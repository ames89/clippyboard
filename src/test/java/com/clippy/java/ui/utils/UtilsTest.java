package com.clippy.java.ui.utils;

import com.clippy.java.utils.utils.Utils;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Tests for {@link Utils}.
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
