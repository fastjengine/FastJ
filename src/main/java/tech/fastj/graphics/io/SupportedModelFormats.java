package tech.fastj.graphics.io;

import java.util.Arrays;

public class SupportedModelFormats {
  public static final String Psdf = "psdf";

  public static final String valuesString = Arrays.toString(values());

  private SupportedModelFormats() {
    throw new java.lang.IllegalStateException();
  }

  public static String[] values() {
    return new String[] {Psdf};
  }
}
