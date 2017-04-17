package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types;

/**
 *
 * @author Alexey Storozhev
 */
public class Oracle extends DbType {
  private static Oracle instance = null;

  public synchronized static Oracle getInstance() {
    if (instance == null) {
      synchronized (Oracle.class) {
        instance = new Oracle();
      }
    }
    return instance;
  }
}
