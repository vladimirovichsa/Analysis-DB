package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types;

/**
 *
 * @author Alexey Storozhev
 */
public class MicrosoftDatabase extends DbType {
  private static MicrosoftDatabase instance = null;

  public synchronized static MicrosoftDatabase getInstance() {
    if (instance == null) {
      synchronized (MicrosoftDatabase.class) {
        instance = new MicrosoftDatabase();
      }
    }
    return instance;
  }
}
