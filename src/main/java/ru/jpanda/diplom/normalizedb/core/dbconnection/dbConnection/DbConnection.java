package ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.sql.Select;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.*;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.TypeEnum;
import ru.jpanda.diplom.normalizedb.core.dbconnection.logic.ResponseObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                attr.setArrayIndex(i - 1);
                attr.setColumnType(md.getColumnType(i));
                attr.setColumnTypeName(md.getColumnTypeName(i));
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

    public ResponseObject createTable(String tableName, List<Attribute> attributes) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            String create = "CREATE TABLE if not exists " + tableName +
                    "(";
            int i = 1;
            for (Attribute attribute : attributes) {
                String columnName = attribute.getName();
                String primaryKey = attribute.getIsPrimaryKey() ? " PRIMARY KEY " : "";
                String notNull = attribute.getIsNullable() == 0 ? " NOT NULL " : "";
                String autoInc = attribute.isAutoIncrement() ? " AUTO_INCREMENT " : "";
                String typeName = attribute.getColumnTypeName();
                create += columnName + " " + typeName + primaryKey + notNull + autoInc;
                if (i != attributes.size()) {
                    create += ",";
                }
                i++;
            }
            create += ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            System.out.println(create);

            st.executeUpdate(create);
        } catch (SQLException e) {
            responseObject.setError(e.getMessage());
            return responseObject;
        }
        responseObject.setSuccess("SUCCESS");
        return responseObject;
    }

    List<Integer> getAllRowTheTable(String tableName, String... colunm) {
        List<Integer> listID = new ArrayList<>();
        try {
            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            String columns = "";
            for (int j = 0; j < 0; j++) {
                columns += colunm[j];
                if (j != colunm.length - 1) {
                    columns += ",";
                }
            }
            String create = "SELECT " + columns + " FROM " + tableName;
            System.out.println(create);
            ResultSet resultSet = st.executeQuery(create);
            int i = 0;
            while (resultSet.next()) {
                listID.add(resultSet.getInt(i));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return listID;
    }

    public ResponseObject moveTableData(String tableNameFrom, String tableNameTo, List<Attribute> listAttributes, List<Attribute> listOldAttributes) {
        ResponseObject responseObject = new ResponseObject();
        String addColumn = "ALTER TABLE `test1`.`address` \n" +
                "ADD COLUMN `tabl_id` INT(11) NULL AFTER `house`;";
        String fk = "ALTER TABLE `test1`.`address` \n" +
                "ADD CONSTRAINT `tabl_idi`\n" +
                "  FOREIGN KEY (`tabl_id`)\n" +
                "  REFERENCES `test1`.`table_name_default` (`id`)\n" +
                "  ON DELETE NO ACTION\n" +
                "  ON UPDATE NO ACTION;";
        try {
            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            String field = "";
            String fieldNew = "";
            for (int i = 1; i <= listAttributes.size() - 1; i++) {
                field += listAttributes.get(i).getName();
                if (i != listAttributes.size() - 1) {
                    field += ",";
                }
            }
            for (int i = 0; i <= listOldAttributes.size() - 1; i++) {
                fieldNew += listOldAttributes.get(i).getName();
                if (i != listOldAttributes.size() - 1) {
                    fieldNew += ",";
                }
            }
            String create = "insert into " + tableNameTo + " (" + fieldNew + ") select DISTINCT " + field + " from " + tableNameFrom;
            System.out.println(create);
            st.executeUpdate(create);
        } catch (SQLException e) {
            responseObject.setError(e.getMessage());
            return responseObject;
        } finally {

        }
        responseObject.setSuccess("SUCCESS");
        return responseObject;
    }

    public ResponseObject deleteOldColumn(String tableName, List<Attribute> attributeList) {
        ResponseObject responseObject = new ResponseObject();
        try {

            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            String field = "";
            for (int i = 0; i <= attributeList.size() - 1; i++) {
                field += "DROP COLUMN " + attributeList.get(i).getName();
                if (i != attributeList.size() - 1) {
                    field += ",";
                }
            }
            String create = "ALTER TABLE " + tableName + " " + field;
            System.out.println(create);
            st.executeUpdate(create);

        } catch (SQLException e) {
            responseObject.setError(e.getMessage());
            return responseObject;
        }
        responseObject.setSuccess("SUCCESS");
        return responseObject;
    }

    public ResponseObject addRow(String tableName, List<Attribute> attributeList, List<Integer> row) {
        ResponseObject responseObject = new ResponseObject();
        try {

            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName);
            int i = 1;
            List<Insert> inserts = new ArrayList<>();
            while (resultSet.next()) {
                for (int i1 = 0; i1 <= row.size() - 1; i1++) {
                    if (i-1 == row.get(i1)) {
                        Insert insert = new Insert();
                        insert.setTableName(tableName);
                        int j = 0;
                        for (Attribute attribute : attributeList) {
//                            field += attribute.getName();
                            insert.setFields(attribute);
                            String value = resultSet.getString(attribute.getArrayIndex()+1);
                            if (attribute.isAtomicity()) {
                                String[] split = value.split("\\,|\\;|\\|");
                                Map<Integer, List<String>> map = new HashMap<>();
                                List<String> list = new ArrayList<>();
                                for (int i2 = 0; i2 < split.length; i2++) {
                                    list.add(split[i2]);
                                }
                                map.put(attribute.getArrayIndex(), list);
                                insert.setDivValue(map);
                            }
                            insert.setValue(value);
                            j++;
                        }
                        inserts.add(insert);
                    }
                }
                i++;
            }
            ResponseObject responseObject1 = buildInsert(inserts);
            if(null != responseObject1.getError()){
                responseObject.setError(responseObject1.getError());
            }else {
                responseObject.setSuccess(responseObject1.getSuccess());
            }
        } catch (SQLException e) {
            responseObject.setError(e.getMessage());
            System.out.println(e.getMessage());
            return responseObject;
        }
        responseObject.setSuccess("SUCCESS");
        return responseObject;
    }

    private ResponseObject buildInsert(List<Insert> insertList) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            int i = 0;
            boolean primary = false;
            boolean autoinc = false;
            int max = 0;
            for (Insert insert : insertList) {
                if (insert.getDivValue().size() != 0) {
                    Map<Integer, List<String>> divValue = insert.getDivValue();
                    for (Map.Entry<Integer, List<String>> pair : divValue.entrySet()) {
                        Integer key = pair.getKey();
                        List<String> value = pair.getValue();
                        for (int i1 = 0; i1 < value.size(); i1++) {
                            if (i1 == 0) {
                                String update = "UPDATE " + insert.getTableName() + " SET ";
                                String fieldUpdate = "";
                                String where = "WHERE ";
                                int i2 = 0;
                                for (Attribute attribute : insert.getFields()) {
                                    if (attribute.isAtomicity()) {
                                        fieldUpdate += attribute.getName() + " = '" + value.get(i1) + "' ";
                                    } else {
                                        where += attribute.getName() + " = '" + insert.getValue().get(attribute.getArrayIndex()) + "'";
                                        if(insert.getValue().size()-1 != i2){
                                            where += " AND ";
                                        }
                                    }
                                    i2++;

                                }
                                update += fieldUpdate + " " + where;
                                System.out.println(update);
                                st.executeUpdate(update);
                            } else {
                                String insertQuery = "INSERT INTO " + insert.getTableName();

                                String fields = "(";
                                String values = " VALUE (";
                                int i2 = 0;

                                for (Attribute attribute : insert.getFields()) {
                                    if (attribute.isAutoIncrement()) {

                                    } else {
                                        fields += attribute.getName();
                                        if (attribute.getIsPrimaryKey() && !attribute.getIsForeignKey()) {
                                            if (attribute.getColumnTypeName().equals("INT") || attribute.getColumnTypeName().equals("integer")) {
                                                String select = "select max(" + attribute.getName() + ") from " + insert.getTableName();
                                                ResultSet resultSet = st.executeQuery(select);
                                                while (resultSet.next()) {
                                                    if(max == 0){
                                                        max = Integer.parseInt(resultSet.getString(1))+1;
                                                    }else {
                                                        max++;
                                                    }
                                                    values += "'" + max + "'";
                                                }
                                            } else {
                                                values += "'" + insert.getValue().get(attribute.getArrayIndex()) + "'";
                                            }
                                        } else {
                                            if(attribute.getArrayIndex() == key){
                                                values += "'" + value.get(i1) + "'";
                                            }else{
                                                values += "'" + insert.getValue().get(attribute.getArrayIndex()) + "'";
                                            }
                                        }
                                        if(insert.getFields().size()-1 != i2){
                                            fields += ", ";
                                            values += ", ";
                                        }
                                    }
                                    i2++;
                                }
                                fields += ")";
                                values += ")";
                                insertQuery += fields + values;
                                System.out.println(insertQuery);
                                st.executeUpdate(insertQuery);
                            }

                        }
                    }
                }

            }
            responseObject.setSuccess("SUCCESS");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            responseObject.setError(e.getMessage());
            e.printStackTrace();
        }


        return responseObject;
    }

    private boolean checkAutoIncrement(List<Attribute> attributeList) {
        for (Attribute attribute : attributeList) {
            if (attribute.isAutoIncrement()) {
                return true;
            }
        }
        return false;
    }

    public ResponseObject addColumn(String tableName, String tableNameNew) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            String addColumn = "ADD COLUMN " + tableNameNew + "_id INT(11) ";
            String create = "ALTER TABLE " + tableName + " " + addColumn;
            System.out.println(create);
            st.executeUpdate(create);
        } catch (SQLException e) {
            responseObject.setError(e.getMessage());
            return responseObject;
        }
        responseObject.setSuccess("SUCCESS");
        return responseObject;
    }

    public ResponseObject copyDataTableToInDataTableFrom(String tableNameTo,
                                                         String tableNameFrom,
                                                         List<Attribute> attributeNew, List<Attribute> attributeOld) {
        ResponseObject responseObject = new ResponseObject();
        List<String> lisCopyId = new ArrayList<>();
        try {

            Connection conn = DBSingleton.getInstance();
            Statement st = conn.createStatement();
            String field = "";
            for (int i = 0; i <= attributeNew.size() - 1; i++) {
                field += attributeNew.get(i).getName();
                if (i != attributeNew.size() - 1) {
                    field += ",";
                }
            }
            ResultSet resultSet = st.executeQuery("SELECT " + field + " FROM " + tableNameFrom);

            while (resultSet.next()) {
                String where = "";
                for (int i1 = 0; i1 <= attributeOld.size() - 1; i1++) {
                    where += attributeOld.get(i1).getName() + " = '" + resultSet.getString(i1 + 2) + "'";
                    if (i1 != attributeOld.size() - 1) {
                        where += " AND ";
                    }
                }
                lisCopyId.add("UPDATE " + tableNameTo + " SET " + tableNameFrom + "_id = '" + resultSet.getInt(1) + "' WHERE " + where);

            }

            System.out.println(lisCopyId);
            for (String s : lisCopyId) {
                st.executeUpdate(s);
            }
            String symbols = "qwerty";
            StringBuilder randString = new StringBuilder();
            int count = (int) (Math.random() * 30);
            for (int i = 0; i < count; i++)
                randString.append(symbols.charAt((int) (Math.random() * symbols.length())));

            String ss = "ALTER TABLE `" + tableNameTo + "` " +
                    "CHANGE COLUMN `" + tableNameFrom + "_id` `" + tableNameFrom + "_id` INT(11) NOT NULL , " +
                    "DROP PRIMARY KEY, " +
                    "ADD PRIMARY KEY (`" + attributeNew.get(0).getName() + "`, `" + tableNameFrom + "_id`), " +
                    "ADD INDEX `" + tableNameFrom + "_" + randString.toString() + "_idx` (`" + tableNameFrom + "_id` ASC); ";
            String s1 = "ALTER TABLE `" + tableNameTo + "` " +
                    "ADD CONSTRAINT `" + tableNameFrom + "_id_" + randString.toString() + "` " +
                    "  FOREIGN KEY (`" + tableNameFrom + "_id`) " +
                    "  REFERENCES `" + tableNameFrom + "` (`" + attributeNew.get(0).getName() + "`) " +
                    "  ON DELETE NO ACTION " +
                    "  ON UPDATE NO ACTION;";

            System.out.println(ss);
            System.out.println(s1);
            st.executeUpdate(ss);
            st.executeUpdate(s1);

        } catch (SQLException e) {
            responseObject.setError(e.getMessage());
            return responseObject;
        }
        responseObject.setSuccess("SUCCESS");
        return responseObject;
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
