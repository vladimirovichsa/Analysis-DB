package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alexey Storozhev
 */
public class PostgresConnection extends DbConnection {

  private String userName = null;
  private String password = null;
  private String driver = null;
  private String DbUrl = null;

  public PostgresConnection(String user, String pwd, String url) throws SQLException {
    super();
    userName = user;
    password = pwd;
    driver = "org.postgresql.Driver";
    DbUrl = "jdbc:postgresql://" + url;
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
      e.printStackTrace();
      throw e;
    }  finally {

    }
  }

}
