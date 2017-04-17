package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

/**
 *
 * @author Alexey Storozhev
 */
public class SQLiteConnection extends DbConnection {
  /*
  private String driver = null;
  private String DbUrl = null;

  public SQLiteConnection(String url) throws SQLException {
    super();
    driver = "org.sqlite.JDBC";
    DbUrl = "jdbc:sqlite:" + url;

    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(DbUrl);

      getRelationsFromDb();
      getDataFromDb();
      getForeignKeysFromDb();

    } catch (SQLException e) {
      //e.printStackTrace();
      throw e;
    } catch (ClassNotFoundException e) {
      //e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
      */
}
