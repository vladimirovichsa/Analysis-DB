package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import java.util.ArrayList;

/**
 *
 * @author Alexey Storozhev
 */
public class Key {
  private ArrayList<Attribute> attributes;

  public Key() {
    super();
    attributes = new ArrayList<>();
  }

  public Key(ArrayList<Attribute> attributes) {
    super();
    this.attributes = attributes;
  }

  public ArrayList<Attribute> getAttributes() {
    return attributes;
  }

  @Override
  public String toString() {
    return "(" + Utilities.getStringFromArrayList(attributes) + ")";
  }
}
