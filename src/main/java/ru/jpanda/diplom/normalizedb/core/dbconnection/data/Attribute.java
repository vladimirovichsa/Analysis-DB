package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 *
 * @author Alexey Storozhev
 */

public class Attribute implements Serializable {

  private static final long serialVersionUID = 8605668187166117214L;
  private int arrayIndex;
  private boolean autoIncrement;
  private String name;
  private boolean isPrimaryKey;
  private boolean isForeignKey;
  private String constraints;
  private int isNullable;
  private int columnType;
  private int dataTypeSize;

  private boolean normal = false;
  private boolean notice = false;
  private boolean warning = false;
  private boolean critical = false;

  public Attribute() {
    super();
    name = "newAttribute";
    isPrimaryKey = false;
    isForeignKey = false;
    constraints = "";
  }

  public Attribute(String name) {
    this();
    setName(name);
  }

  public Attribute(String name, boolean isPrimaryKey, boolean isForeignKey) {
    this(name);
    this.isPrimaryKey = isPrimaryKey;
    this.isForeignKey = isForeignKey;
  }

  public String getName() {
    return name;
  }

  public String getNameWithKeyNotation() {
    String returnString = name;
    if (isPrimaryKey) {
      returnString = returnString + "<pk>";
    }
    if (isForeignKey) {
      returnString = returnString + "<fk>";
    }
    if (!constraints.isEmpty()) {
      returnString = returnString + " [" + constraints + "]";
    }
    return returnString;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNameWithoutFiring(String name) {
    if (!name.equals(this.name)) {
      this.name = name;
    }
  }

  public int getArrayIndex() {
    return arrayIndex;
  }

  public void setArrayIndex(int arrayIndex) {
    this.arrayIndex = arrayIndex;
  }

  // PrimaryKey
  public void setIsPrimaryKey(boolean isPrimaryKey) {
    this.isPrimaryKey = isPrimaryKey;
  }

  public boolean getIsPrimaryKey() {
    return isPrimaryKey;
  }

  public void setIsForeignKey(boolean isForeignKey) {
    this.isForeignKey = isForeignKey;
  }

  public void setIsForeignKeyWithoutFiring(boolean isForeignKey) {
    this.isForeignKey = isForeignKey;
  }

  public boolean getIsForeignKey() {
    return isForeignKey;
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public int getIsNullable() {
    return isNullable;
  }

  public void setIsNullable(int isNullable) {
    this.isNullable = isNullable;
  }

  public int getColumnType() {
    return columnType;
  }

  public void setColumnType(int columnType) {
    this.columnType = columnType;
  }

  public int getDataTypeSize() {
    return dataTypeSize;
  }

  public void setDataTypeSize(int dataTypeSize) {
    this.dataTypeSize = dataTypeSize;
  }

  public boolean isNormal() {
    return normal;
  }

  public void setNormal(boolean normal) {
    this.normal = normal;
  }

  public boolean isNotice() {
    return notice;
  }

  public void setNotice(boolean notice) {
    this.notice = notice;
  }

  public boolean isWarning() {
    return warning;
  }

  public void setWarning(boolean warning) {
    this.warning = warning;
  }

  public boolean isCritical() {
    return critical;
  }

  public void setCritical(boolean critical) {
    this.critical = critical;
  }

  @JsonIgnore
  @Override
  public String toString() {
    return getNameWithKeyNotation();
  }

  @Override
  public boolean equals(Object otherObject) {
    if (otherObject instanceof Attribute) {
      Attribute otherAttribute = (Attribute) otherObject;
      // Name
      if (!name.equals(otherAttribute.getName())) {
        return false;
      }
      // PK
      if (isPrimaryKey != otherAttribute.getIsPrimaryKey()) {
        return false;
      }
      // FK
      if (isForeignKey != otherAttribute.getIsForeignKey()) {
        return false;
      }
    }
    else {
      return false;
    }

    return true;
  }

  public String getConstraints() {
    return constraints;
  }

  public void setConstraints(String constraints) {
    this.constraints = constraints;
  }
}
