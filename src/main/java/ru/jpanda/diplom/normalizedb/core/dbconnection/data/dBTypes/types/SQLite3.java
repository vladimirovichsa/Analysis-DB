package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types;

/**
 *
 * @author Alexey Storozhev
 */
public class SQLite3 extends DbType {
  private static SQLite3 instance = null;

  public synchronized static SQLite3 getInstance() {
    if (instance == null) {
      synchronized (SQLite3.class) {
        instance = new SQLite3();
      }
    }
    return instance;
  }
}
