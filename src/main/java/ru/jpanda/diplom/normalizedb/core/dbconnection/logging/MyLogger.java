package ru.jpanda.diplom.normalizedb.core.dbconnection.logging;

import java.io.IOException;
import java.util.logging.*;

/**
 *
 * @author Alexey Storozhev
 */
public class MyLogger {
  static private SimpleFormatter formatterTxt;

  static private Level logLevel = Level.INFO;

  static private FileHandler fileTxt;


  static public void setup() throws IOException {

    Logger logger = Logger.getLogger("");
    logger.setLevel(logLevel);
    fileTxt = new FileHandler("Logging.txt");
//    fileHTML = new FileHandler(Options.getInstance().getLogFile().getCanonicalPath());

    // Create txt Formatter
    formatterTxt = new SimpleFormatter();
    fileTxt.setFormatter(formatterTxt);
    logger.addHandler(fileTxt);

    // Create HTML Formatter
//    formatterHTML = new MyHtmlFormatter();
//    fileHTML.setFormatter(formatterHTML);
//    logger.addHandler(fileHTML);
  }


}
