package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types;

/**
 *
 * @author Alexey Storozhev
 */
public class MySql extends DbType {
  private static MySql instance = null;

  public synchronized static MySql getInstance() {
    if (instance == null) {
      synchronized (MySql.class) {
        instance = new MySql();
      }
    }
    return instance;
  }
}
