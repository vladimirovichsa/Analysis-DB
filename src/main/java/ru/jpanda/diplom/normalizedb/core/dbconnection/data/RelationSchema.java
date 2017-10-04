package ru.jpanda.diplom.normalizedb.core.dbconnection.data;


import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexey Storozhev
 */

public class RelationSchema implements Serializable {

  private static final long serialVersionUID = -3301439448650204024L;
  private String name;
  private ArrayList<Attribute> attributes;
  private ArrayList<FunctionalDependency> functionalDependencies;
  private List<List<String>> data;
  private static int id = 0;
  private int ownId;
  private List<List<String>> dataFromAnalysis;
  private boolean nf1 = false;
  private boolean nf2 = false;
  private boolean nf3 = false;

  public RelationSchema() {
    super();
    ownId = id++;
    attributes = new ArrayList<>();
    functionalDependencies = new ArrayList<>();
  }

  public RelationSchema(String name) {
    this();
    this.name = name;
  }

  public RelationSchema(String name, ArrayList<Attribute> attributes) {
    this(name);
    this.attributes = attributes;
  }

  public RelationSchema(String name, ArrayList<Attribute> attributes, ArrayList<FunctionalDependency>
    functionalDependencies) {
    this(name, attributes);
    this.functionalDependencies = functionalDependencies;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (!name.equals(this.name)) {
      this.name = name;
    }
  }

  public void setNameWithoutFiring(String name) {
    if (!name.equals(this.name)) {
      this.name = name;
    }
  }

  public boolean isNf1() {
    return nf1;
  }

  public void setNf1(boolean nf1) {
    this.nf1 = nf1;
  }

  public boolean isNf2() {
    return nf2;
  }

  public void setNf2(boolean nf2) {
    this.nf2 = nf2;
  }

  public boolean isNf3() {
    return nf3;
  }

  public void setNf3(boolean nf3) {
    this.nf3 = nf3;
  }

  public void renameAttributeWithoutFiring(Attribute attribute, String newName) {
    if (attributes.contains(attribute)) {
      attribute.setNameWithoutFiring(newName);
    }
  }

  public void setOwnId(int ownId) {
    this.ownId = ownId;
  }

  public int getOwnId() {
    return ownId;
  }

  public ArrayList<Attribute> getAttributes() {
    return attributes;
  }

  public String[] getAttributesNameArray() {
    String[] attrArray = new String[attributes.size()];

    int i = 0;
    for (Attribute attr : attributes) {
      attrArray[i++] = attr.getName();
    }

    return attrArray;
  }

  public Attribute getAttributeByName(String name) {

    for (Attribute attr : attributes) {
      if (attr.getName().equals(name)) {
        return attr;
      }
    }
    return null;
  }

  public ArrayList<FunctionalDependency> getFunctionalDependencies() {
    return functionalDependencies;
  }

  public void setAttributes(ArrayList<Attribute> attributes) {
    this.attributes = attributes;
  }

  public void removeAttribute(Attribute attribute) {
    attributes.remove(attribute);
  }

  public void setFunctionalDependencies(ArrayList<FunctionalDependency> functionalDependencies) {
    this.functionalDependencies = functionalDependencies;
  }

  public boolean addFunctionalDependency(FunctionalDependency dependency) {
    for (FunctionalDependency fd : functionalDependencies) {
      if (fd.equals(dependency)) {
        return false;
      }
    }
    functionalDependencies.add(dependency);
    return true;
  }

  public boolean addAttribute(String name) {
    for (Attribute attribute : attributes) {
      if (attribute.getName().equalsIgnoreCase(name)) {
        return false;
      }
    }
    return addAttribute(new Attribute(name));
  }

  public boolean addAttribute(Attribute attribute) {
    if (!attributes.contains(attribute)) {
      attributes.add(attribute);
      return true;
    }
    return false;
  }

  public void removeFunctionalDependency(FunctionalDependency dependency) {
    functionalDependencies.remove(dependency);
  }

  public void updateFunctionalDependencies() {
    ArrayList<FunctionalDependency> toDelete = new ArrayList<>();

    for (FunctionalDependency fd : functionalDependencies) {
      cleanReferences(fd.getSourceAttributes());
      cleanReferences(fd.getTargetAttributes());

      if (fd.getSourceAttributes().isEmpty() || fd.getTargetAttributes().isEmpty()) {
        toDelete.add(fd);
      }
    }

    functionalDependencies.removeAll(toDelete);
  }

  private void cleanReferences(ArrayList<Attribute> list) {
    ArrayList<Attribute> toDelete = new ArrayList<>();

    for (Attribute item : list) {
      if (!isAttributeExisting(item)) {
        toDelete.add(item);
      }
    }

    for (Attribute attr : toDelete) {
      list.remove(attr);
    }
  }

  private boolean isAttributeExisting(Attribute attribute) {
    for (Attribute attr : attributes) {
      if (attr == attribute) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return name + " (" + Utilities.getStringFromArrayList(attributes) + ")";
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof RelationSchema) {
      RelationSchema otherSchema = (RelationSchema) object;

      if (!name.equals(otherSchema.getName())) {
        return false;
      }

      if (!attributes.equals(otherSchema.getAttributes())) {
        return false;
      }

      if (!functionalDependencies.equals(otherSchema.getFunctionalDependencies())) {
        return false;
      }

    }

    return true;
  }

  public void restoreReferences() {
    for (FunctionalDependency fd : getFunctionalDependencies()) {
      for (int i = 0; i < fd.getSourceAttributes().size(); i++) {
        fd.getSourceAttributes().set(i, getAttributeByName(fd.getSourceAttributes().get(i).getName()));
      }
      for (int i = 0; i < fd.getTargetAttributes().size(); i++) {
        fd.getTargetAttributes().set(i, getAttributeByName(fd.getTargetAttributes().get(i).getName()));
      }
    }
  }

  public void setData(List<List<String>> data) {
    this.data = data;
  }

  public List<List<String>> getData() {
    return data;
  }

  public void setDataFromAnalysis(List<List<String>> data) {
    this.dataFromAnalysis = data;
  }

  public List<List<String>> getDataFromAnalysis() {
    return dataFromAnalysis;
  }
}
