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
  private String columnTypeName;
  private int dataTypeSize;
  private Atomicity atomicityObject ;
  private boolean isAtomicity ;

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

  public boolean isAtomicity() {
    return isAtomicity;
  }

  public void setAtomicity(boolean atomicity) {
    isAtomicity = atomicity;
  }

  public Atomicity getAtomicityObject() {
    return atomicityObject;
  }

  public void setAtomicityObject(Atomicity atomicityObject) {
    this.atomicityObject = atomicityObject;
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

  public String getColumnTypeName() {
    return columnTypeName;
  }

  public void setColumnTypeName(String columnTypeName) {
    this.columnTypeName = columnTypeName;
  }

  @JsonIgnore
  @Override
  public String toString() {
    return getNameWithKeyNotation();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Attribute attribute = (Attribute) o;

    if (arrayIndex != attribute.arrayIndex) return false;
    if (autoIncrement != attribute.autoIncrement) return false;
    if (isPrimaryKey != attribute.isPrimaryKey) return false;
    if (isForeignKey != attribute.isForeignKey) return false;
    if (isNullable != attribute.isNullable) return false;
    if (columnType != attribute.columnType) return false;
    if (dataTypeSize != attribute.dataTypeSize) return false;
    if (isAtomicity != attribute.isAtomicity) return false;
    if (normal != attribute.normal) return false;
    if (notice != attribute.notice) return false;
    if (warning != attribute.warning) return false;
    if (critical != attribute.critical) return false;
    if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
    if (constraints != null ? !constraints.equals(attribute.constraints) : attribute.constraints != null) return false;
    if (columnTypeName != null ? !columnTypeName.equals(attribute.columnTypeName) : attribute.columnTypeName != null)
      return false;
    return atomicityObject != null ? atomicityObject.equals(attribute.atomicityObject) : attribute.atomicityObject == null;
  }

  @Override
  public int hashCode() {
    int result = arrayIndex;
    result = 31 * result + (autoIncrement ? 1 : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (isPrimaryKey ? 1 : 0);
    result = 31 * result + (isForeignKey ? 1 : 0);
    result = 31 * result + (constraints != null ? constraints.hashCode() : 0);
    result = 31 * result + isNullable;
    result = 31 * result + columnType;
    result = 31 * result + (columnTypeName != null ? columnTypeName.hashCode() : 0);
    result = 31 * result + dataTypeSize;
    result = 31 * result + (atomicityObject != null ? atomicityObject.hashCode() : 0);
    result = 31 * result + (isAtomicity ? 1 : 0);
    result = 31 * result + (normal ? 1 : 0);
    result = 31 * result + (notice ? 1 : 0);
    result = 31 * result + (warning ? 1 : 0);
    result = 31 * result + (critical ? 1 : 0);
    return result;
  }

  public String getConstraints() {
    return constraints;
  }

  public void setConstraints(String constraints) {
    this.constraints = constraints;
  }
}
