package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.TypeEnum;

import java.sql.SQLException;

/**
 *
 * @author Alexey Storozhev
 */
public class DbConnectionFactory {

  public static DbConnection getConnection(TypeEnum type, String user, String pwd, String address) throws SQLException {
    switch (type) {
      case MYSQL:
        return new MYSQLConnection(user, pwd, address);
      case POSTGRES:
        return new PostgresConnection(user, pwd, address);
     /* case MSDB:
        return null;
      case ORACLE:
        return null;
      case SQLITE:
        return new SQLiteConnection(address);  */
      default:
        return null;
    }
  }
}

