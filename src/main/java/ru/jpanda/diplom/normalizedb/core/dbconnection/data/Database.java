package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.TypeEnum;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types.ColumnType;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.types.TypeMySql;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alexey Storozhev
 */

public class Database implements Serializable {

  private ArrayList<RelationSchema> database;
  private String custCompany;
  private String custAdress;
  private String notes;
  private TypeEnum type;
  private ArrayList<Object> searchResult;
  private List<ColumnType> columnTypes;

  private ArrayList<ForeignKeyConstraint> foreignKeys;

  public Database() {
    super();
    database = new ArrayList<>();
    foreignKeys = new ArrayList<>();
    custCompany = "";
    custAdress = "";
    notes = "";
    type = TypeEnum.MYSQL;
    searchResult = new ArrayList<>();
    columnTypes = new ArrayList<>(Arrays.asList(TypeMySql.values()));
  }

  public ArrayList<RelationSchema> getDatabase() {
    return database;
  }


  public boolean addRelationSchema(RelationSchema schema) {
    boolean added;
    added = database.add(schema);
    return added;

  }

  public void renameRelationSchema(RelationSchema relation, String newName) {
    if (database.contains(relation)) {
      updateFkRelationNames(relation.getName(), newName);
      relation.setNameWithoutFiring(newName);
    }
  }

  public void renameAttribute(RelationSchema parentRelation, Attribute attribute, String newName) {
    if (database.contains(parentRelation)) {
      if (parentRelation.getAttributes().contains(attribute)) {
        updateFkAttributeNames(parentRelation.getName(), attribute.getName(), newName);
        parentRelation.renameAttributeWithoutFiring(attribute, newName);
      }
    }
  }

  private Attribute getAttributeFromRelation(String relationName, String attributeName) {
    RelationSchema relation = getRelationSchema(relationName);
    Attribute attribute = null;
    if (relation != null) {
      attribute = relation.getAttributeByName(attributeName);
      if (attribute != null) {
        return attribute;
      }
    }
    return attribute;
  }

  private String getNewRelationName(String oldRelationName, String attributeName, ArrayList<RelationSchema> relations) {
    for (RelationSchema relation : relations) {
      for (Attribute attr : relation.getAttributes()) {
        if (attr.getName().equals(attributeName)) {
          return relation.getName();
        }
      }
    }

    return oldRelationName;
  }

  public RelationSchema getRelationSchema(String text) {
    Integer relationId = Utilities.tryParseInt(text);

    if (relationId != null) {
      return getRelationSchemaByIndex(relationId - 1);
    } else {
      return getRelationSchemaByName(text);
    }
  }

  public RelationSchema getRelationSchemaByIndex(Integer index) {
    if (index >= 0 && index < database.size()) {
      return database.get(index);
    }
    return null;
  }

  public RelationSchema getRelationSchemaByName(String name) {
    RelationSchema resultRelation = null;
    for (RelationSchema relation : this.database) {
      if (relation.getName().equalsIgnoreCase(name)) {
        resultRelation = relation;
        break;
      }
    }
    return resultRelation;
  }

  public ArrayList<String> getAllRelationNames() {
    ArrayList<String> relationNames = new ArrayList<>();
    for (RelationSchema relation : database) {
      relationNames.add(relation.getName());
    }
    return relationNames;
  }

  public ArrayList<ForeignKeyConstraint> getForeignKeys() {
    return foreignKeys;
  }

  public void removeForeignKey(String sourceRelationName, String sourceAttributeName) {
    ArrayList<ForeignKeyConstraint> fksToDelete = new ArrayList<>();

    for (ForeignKeyConstraint fk : foreignKeys) {
      if (fk.getSourceRelationName().equals(sourceRelationName) && fk.getSourceAttributeName().equals
        (sourceAttributeName)) {
        fksToDelete.add(fk);
      }
    }
    foreignKeys.removeAll(fksToDelete);
  }

  public void updateFkRelationNames(String oldRelationName, String newRelationName) {
    for (ForeignKeyConstraint fk : foreignKeys) {
      if (fk.getSourceRelationName().equals(oldRelationName)) {
        fk.setSourceRelationName(newRelationName);
      }
      if (fk.getTargetRelationName().equals(oldRelationName)) {
        fk.setTargetRelationName(newRelationName);
      }
    }
  }

  public void updateFkAttributeNames(String relationName, String attributeName, String newAttributeName) {
    for (ForeignKeyConstraint fk : foreignKeys) {
      if (fk.getSourceRelationName().equals(relationName)) {
        if (fk.getSourceAttributeName().equals(attributeName)) {
          fk.setSourceAttributeName(newAttributeName);
        }
      } else if (fk.getTargetRelationName().equals(relationName)) {
        if (fk.getTargetAttributeName().equals(attributeName)) {
          fk.setTargetAttributeName(newAttributeName);
        }
      }
    }
  }

  public List<ColumnType> getColumnTypes() {
    return columnTypes;
  }

  @Override
  public String toString() {
    return "Database";
  }

  public String getCustCompany() {
    return custCompany;
  }

  public void setCustCompany(String custCompany) {
    this.custCompany = custCompany;
  }

  public String getCustAdress() {
    return custAdress;
  }

  public void setCustAdress(String custAdress) {
    this.custAdress = custAdress;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public ArrayList<Object> search(String searchForString) {
    searchResult.clear();
    for(RelationSchema rel: database) {
      if(rel.getName().toUpperCase().contains(searchForString.toUpperCase())) {
        searchResult.add(rel);
      }
      for(Attribute attr : rel.getAttributes()) {
        if(attr.getName().toUpperCase().contains(searchForString.toUpperCase())) {
          searchResult.add(attr);
        }
      }
    }

    return searchResult;
  }
}

