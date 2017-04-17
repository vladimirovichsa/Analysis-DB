package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types;

/**
 *
 * @author Alexey Storozhev
 */
public class ProstgreSQL extends DbType {
  private static ProstgreSQL instance = null;

  public synchronized static ProstgreSQL getInstance() {
    if (instance == null) {
      synchronized (ProstgreSQL.class) {
        instance = new ProstgreSQL();
      }
    }
    return instance;
  }
}
