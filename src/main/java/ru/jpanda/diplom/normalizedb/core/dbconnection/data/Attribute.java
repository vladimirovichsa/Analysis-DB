package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author Alexey Storozhev
 */

public class Attribute implements Serializable {

  private static final long serialVersionUID = 8605668187166117214L;
  private boolean autoIncrement;
  private String name;
  private boolean isPrimaryKey;
  private boolean isForeignKey;
  private String constraints;
  private int isNullable;
  private int columnType;
  private int dataTypeSize;

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
