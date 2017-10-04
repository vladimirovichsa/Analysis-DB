package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import java.util.ArrayList;

/**
 * @author Alexey Storozhev
 */
public class CreateTable {

    private String tableName;
    private String tableNameOld;
    private ArrayList<Attribute> listAttribute;
    private ArrayList<Attribute> listOldAttribute;
//    private boolean autoIncrement;
//    private String name;
//    private boolean isPrimaryKey;
//    private int isNullable;
//    private String columnTypeName;

    public CreateTable() {
    }

    public String getTableNameOld() {
        return tableNameOld;
    }

    public void setTableNameOld(String tableNameOld) {
        this.tableNameOld = tableNameOld;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<Attribute> getListAttribute() {
        return listAttribute;
    }

    public ArrayList<Attribute> getListOldAttribute() {
        return listOldAttribute;
    }

    public void setListOldAttribute(ArrayList<Attribute> listOldAttribute) {
        this.listOldAttribute = listOldAttribute;
    }

    public void setListAttribute(ArrayList<Attribute> listAttribute) {
        this.listAttribute = listAttribute;
    }
//    public String getTableName() {
//        return tableName;
//    }
//
//    public void setTableName(String tableName) {
//        this.tableName = tableName;
//    }
//
//    public boolean isAutoIncrement() {
//        return autoIncrement;
//    }
//
//    public void setAutoIncrement(boolean autoIncrement) {
//        this.autoIncrement = autoIncrement;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean isPrimaryKey() {
//        return isPrimaryKey;
//    }
//
//    public void setPrimaryKey(boolean primaryKey) {
//        isPrimaryKey = primaryKey;
//    }
//
//    public int getIsNullable() {
//        return isNullable;
//    }
//
//    public void setIsNullable(int isNullable) {
//        this.isNullable = isNullable;
//    }
//
//    public String getColumnTypeName() {
//        return columnTypeName;
//    }
//
//    public void setColumnTypeName(String columnTypeName) {
//        this.columnTypeName = columnTypeName;
//    }
}
