package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes;

import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Database;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types.DbType;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types.MySql;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types.ProstgreSQL;

import static ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.TypeEnum.MYSQL;


/**
 *
 * @author Alexey Storozhev
 */
public class DbTypeFactory {
  private Database database;

  public DbTypeFactory(Database db) {
    super();
    this.database = db;
  }

  public DbType getType() {
    switch (database.getType()) {
      case MYSQL:
        return MySql.getInstance();
      case POSTGRES:
        return ProstgreSQL.getInstance();
      /*case MSDB:
        return MicrosoftDatabase.getInstance();
      case ORACLE:
        return Oracle.getInstance();
      case SQLITE:
        return SQLite3.getInstance();  */
      default:
        throw new IllegalArgumentException();
    }

  }


}
