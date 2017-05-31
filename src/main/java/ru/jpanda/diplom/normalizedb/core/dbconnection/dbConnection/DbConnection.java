package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

import org.apache.commons.dbcp.BasicDataSource;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.*;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types.DbType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Storozhev
 */
public abstract class DbConnection {

    protected static BasicDataSource dataSource;
    private DatabaseMetaData dbm = null;
    private ArrayList<String> relationNames = null;
    private ResultSet tables = null;
    private Database database = null;

    public DbConnection() {
        super();
        relationNames = new ArrayList<>();
        database = new Database();
    }

    public Database getDatabase() {
        return database;
    }

    void getRelationsFromDb() throws SQLException {
        Connection conn = null;
        //Get Relation Names
        try {
            conn = DBSingleton.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbm = conn.getMetaData();
        String[] types = {"TABLE"};
        tables = dbm.getTables(null, null, "%", types);
        while (tables.next()) {
            String table = tables.getString("TABLE_NAME");
            relationNames.add(table);
        }
    }

    void getDataFromDb() throws SQLException {
        Connection conn = DBSingleton.getInstance();
        for (String relation : relationNames) {
            RelationSchema tmpRelation = new RelationSchema(relation);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + relation);
            ResultSetMetaData md = rs.getMetaData();
            int col = md.getColumnCount();
            for (int i = 1; i <= col; i++) {
                String col_name = md.getColumnName(i);
                String dataType = md.getColumnTypeName(i);
                int dataTypeSize = md.getPrecision(i);
                int nullable = md.isNullable(i);
                boolean autoIncrement = md.isAutoIncrement(i);
                Attribute attr = new Attribute(col_name);
                attr.setArrayIndex(i-1);
                attr.setColumnType(md.getColumnType(i));
                String constraints = "";
                if (nullable == 0) {
                    constraints = constraints + " NOT NULL";
                }
                if (autoIncrement) {
                    constraints = constraints + " AUTO_INCREMENT";
                }
                attr.setConstraints(dataType + ((this instanceof SQLiteConnection) ? "" : "(" + dataTypeSize + ")") +
                        constraints);
                tmpRelation.addAttribute(attr);
            }
            ResultSet pks = dbm.getPrimaryKeys(null, null, relation);
            while (pks.next()) {
                String columnName = pks.getString("COLUMN_NAME");
                tmpRelation.getAttributeByName(columnName).setIsPrimaryKey(true);
            }
            database.addRelationSchema(tmpRelation);
        }
    }

    void getForeignKeysFromDb() throws SQLException {
        Connection conn = DBSingleton.getInstance();
        for (String relation : relationNames) {
            ResultSet fks = dbm.getExportedKeys(conn.getCatalog(), null, relation);

            while (fks.next()) {

                String fkTableName = fks.getString("FKTABLE_NAME");
                String fkColumnName = fks.getString("FKCOLUMN_NAME");
                String pkTableName = fks.getString("PKTABLE_NAME");
                String pkColumnName = fks.getString("PKCOLUMN_NAME");


                ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint();
                foreignKeyConstraint.setSourceRelationName(fkTableName);
                foreignKeyConstraint.setSourceAttributeName(fkColumnName);
                foreignKeyConstraint.setTargetRelationName(pkTableName);
                foreignKeyConstraint.setTargetAttributeName(pkColumnName);
                database.getForeignKeys().add(foreignKeyConstraint);
                database.getRelationSchemaByName(fkTableName).getAttributeByName(fkColumnName).setIsForeignKey(true);
            }
        }
    }


    public void getDataByTableNameFromAnalysis(String relationName) throws SQLException {
        ResultSet rs = execute("SELECT * FROM " + relationName);
        int fetchSize = rs.getFetchSize();
        ResultSetMetaData md = rs.getMetaData();
        List<List<String>> data = new ArrayList<>();
        int col = md.getColumnCount();
        int i = 1;
        List<String> columnData = null;
        while (rs.next()) {
            if (columnData == null)
                columnData = new ArrayList<>();

            boolean last = rs.isLast();
            columnData.add(rs.getString(i));
            if (last && data.size() != col) {
                rs.beforeFirst();
                data.add(columnData);
                if ((i + 1) < col)
                    i++;
                columnData = null;
            }
        }
        rs.getStatement().close();
        database.getRelationSchemaByName(relationName).setDataFromAnalysis(data);
    }

    public void getDataByTableNameFromDb(String relationName) throws SQLException {
        ResultSet rs = execute("SELECT * FROM " + relationName);
        ResultSetMetaData md = rs.getMetaData();
        List<List<String>> data = new ArrayList<>();
        int col = md.getColumnCount();
        while (rs.next()) {
            List<String> tableData = new ArrayList<>();
            for (int i = 1; i <= col; i++) {
                tableData.add(rs.getString(i));
            }
            data.add(tableData);
        }
        rs.getStatement().close();
        database.getRelationSchemaByName(relationName).setData(data);
    }


    private ResultSet execute(String query) throws SQLException {
        Connection conn = DBSingleton.getInstance();
        DatabaseMetaData dbmd = conn.getMetaData();
        Statement stmt = null;
        int JDBCVersion = dbmd.getJDBCMajorVersion();
        boolean srs = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        if (srs) {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } else {
            stmt = conn.createStatement();
        }
        System.out.println(query);
        return stmt.executeQuery(query);
    }
}
