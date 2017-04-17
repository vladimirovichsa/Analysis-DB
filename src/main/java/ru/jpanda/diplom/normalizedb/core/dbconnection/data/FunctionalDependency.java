package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Alexey Storozhev
 */

public class FunctionalDependency implements Serializable {

  private static final long serialVersionUID = 166324774376697454L;
  private ArrayList<Attribute> sourceAttributes;
  private ArrayList<Attribute> targetAttributes;

  public FunctionalDependency() {
    super();
    sourceAttributes = new ArrayList<>();
    targetAttributes = new ArrayList<>();
  }

  public FunctionalDependency(ArrayList<Attribute> sourceAttributes, ArrayList<Attribute> targetAttributes) {
    this();
    this.sourceAttributes = sourceAttributes;
    this.targetAttributes = targetAttributes;
  }

  public ArrayList<Attribute> getSourceAttributes() {
    return sourceAttributes;
  }

  public void setSourceAttributes(ArrayList<Attribute> sourceAttributes) {
    this.sourceAttributes = sourceAttributes;
  }

  public void setTargetAttributes(ArrayList<Attribute> targetAttributes) {
    this.targetAttributes = targetAttributes;
  }

  public ArrayList<Attribute> getTargetAttributes() {
    return targetAttributes;
  }

  @Override
  public String toString() {
    return "[" + getStringOfAttributes(sourceAttributes) + "] -> [" + getStringOfAttributes(targetAttributes) + "]";
  }

  private String getStringOfAttributes(ArrayList<Attribute> attributes) {
    return Utilities.getStringFromArrayList(attributes);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FunctionalDependency) {
      FunctionalDependency fd = (FunctionalDependency) obj;
      if (this.sourceAttributes.equals(fd.getSourceAttributes())) {
        if (this.targetAttributes.equals(fd.getTargetAttributes())) {
          return true;
        }
      }
    }
    return false;
  }
}
