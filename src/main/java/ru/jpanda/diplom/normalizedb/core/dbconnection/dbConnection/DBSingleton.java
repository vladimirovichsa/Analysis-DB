package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alexey on 13.04.2017.
 */
public class DBSingleton {
    private static Connection connection;

    private DBSingleton(){
    }

    public static Connection getInstance() throws SQLException {
        if(connection == null){
            connection = DbConnection.dataSource.getConnection();
        }
        return connection;
    }

    public static void deadConnection() throws SQLException {
        if(connection != null){
            connection = null;
        }
    }
}
