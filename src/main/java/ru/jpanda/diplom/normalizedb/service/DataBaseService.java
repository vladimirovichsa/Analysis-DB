package ru.jpanda.diplom.normalizedb.service;

import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection.DbConnection;
import ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection.DbConnectionFactory;
import ru.jpanda.diplom.normalizedb.domain.ConnectionDB;

import java.sql.SQLException;

/**
 * Created by Alexey on 09.04.2017.
 */
@Service
public class DataBaseService {
    DbConnection dbConnection;


    public DbConnection connectionDataBase(ConnectionDB connectionDB) throws SQLException {
        dbConnection = DbConnectionFactory.getConnection(
                connectionDB.getTable_type_id().getType(),
                connectionDB.getUser_name(),
                connectionDB.getPassword(),
                connectionDB.getUrl());
        return dbConnection;
    }

    public DbConnection getConnection(){
        return dbConnection;
    }
}
