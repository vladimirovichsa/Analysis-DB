package ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes;

/**
 *
 * @author Alexey Storozhev
 */
public enum TypeEnum {
  MYSQL("MYSQL"), POSTGRES("PostgreSQL"); //, POSTGRES(""), MSDB("Microsoft"), ORACLE("Oracle"), SQLITE("SQLite3");

  public final String name;

  private TypeEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static TypeEnum getEnumByValue(String value) {
    for (TypeEnum e : values()) {
      if (e.name.endsWith(value)) {
        return e;
      }
    }
    return null;
  }

}
