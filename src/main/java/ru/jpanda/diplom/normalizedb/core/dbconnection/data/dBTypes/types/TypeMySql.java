package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types;

/**
 * @author Alexey Storozhev
 */
public enum TypeMySql implements ColumnType{

    TINYINT("TINYINT"),
    SMALLINT("SMALLINT"),
    MEDIUMINT("MEDIUMINT"),
    INT("INT"),
    INTEGER("INTEGER"),
    BIGINT("BIGINT"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    PRECISION("PRECISION"),
    REAL("REAL"),
    DECIMAL("DECIMAL"),
    NUMERIC("NUMERIC"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    TIMESTAMP("TIMESTAMP"),
    TIME("TIME"),
    YEAR("YEAR"),
    CHAR("CHAR"),
    VARCHAR("VARCHAR"),
    TINYBLOB("TINYBLOB"),
    TINYTEXT("TINYTEXT"),
    BLOB("BLOB"),
    TEXT("TEXT"),
    MEDIUMBLOB("MEDIUMBLOB"),
    MEDIUMTEXT("MEDIUMTEXT"),
    LONGBLOB("LONGBLOB"),
    LONGTEXT("LONGTEXT"),
    ENUM("ENUM"),
    SET("SET");

    public String activity;

    private TypeMySql(String tinyint) {
        this.activity = tinyint;
    }
}
