package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import java.util.ArrayList;

/**
 *
 * @author Alexey Storozhev
 */
public class Utilities {

  private static Utilities instance = new Utilities();

  private Utilities() {
    super();
  }

  public static synchronized Utilities getInstance() {
    return instance;
  }

  public static Integer tryParseInt(String str) {
    Integer n = null;

    try {
      return new Integer(str);

    } catch (NumberFormatException nfe) {
      return n;
    }
  }

  public static String getStringFromArrayList(ArrayList<?> arr) {
    String result = "";

    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i) instanceof Attribute) {
        Attribute attr = (Attribute) arr.get(i);
        if (i == 0) {
          result = result + attr.getName();
        } else {
          result = result + "," + attr.getName();
        }
      } else {
        if (i == 0) {
          result = result + arr.get(i).toString();
        } else {
          result = result + "," + arr.get(i).toString();
        }
      }
    }

    return result;
  }
}
