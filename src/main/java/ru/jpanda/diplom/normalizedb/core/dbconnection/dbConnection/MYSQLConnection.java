package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;

/**
 *
 * @author Alexey Storozhev
 */
public class MYSQLConnection extends DbConnection {

  private String userName = null;
  private String password = null;
  private String driver = null;
  private String DbUrl = null;

  public MYSQLConnection(String user, String pwd, String url) throws SQLException {
    super();
    userName = user;
    password = pwd;
    driver = "com.mysql.jdbc.Driver";
    DbUrl = "jdbc:mysql://" + url;
    DBSingleton.deadConnection();
    dataSource = new BasicDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(DbUrl);
    dataSource.setUsername(user);
    dataSource.setPassword(password);

    try {
      getRelationsFromDb();
      getDataFromDb();
      getForeignKeysFromDb();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw e;
    } finally {

    }
  }


}
