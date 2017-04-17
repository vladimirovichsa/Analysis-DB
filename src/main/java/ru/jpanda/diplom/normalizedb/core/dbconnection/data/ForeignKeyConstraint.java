package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import java.io.Serializable;

/**
 *
 * @author Alexey Storozhev
 */
public final class ForeignKeyConstraint implements Serializable {

  public ForeignKeyConstraint() {
    super();
  }

  public ForeignKeyConstraint(String sourceRelationName, String sourceAttributeName, String targetRelationName,
                              String targetAttributeName) {
    super();
    this.sourceRelationName = sourceRelationName;
    this.sourceAttributeName = sourceAttributeName;
    this.targetRelationName = targetRelationName;
    this.targetAttributeName = targetAttributeName;
  }

  private static final long serialVersionUID = 3011555081376590937L;
  private String sourceRelationName;
  private String targetRelationName;
  private String targetAttributeName;
  private String sourceAttributeName;

  public String getTargetRelationName() {
    return targetRelationName;
  }

  public void setTargetRelationName(String targetRelationName) {
    this.targetRelationName = targetRelationName;
  }

  public String getTargetAttributeName() {
    return targetAttributeName;
  }

  public void setTargetAttributeName(String targetAttributeName) {
    this.targetAttributeName = targetAttributeName;
  }

  public String getSourceRelationName() {
    return sourceRelationName;
  }

  public void setSourceRelationName(String sourceRelationName) {
    this.sourceRelationName = sourceRelationName;
  }

  public String getSourceAttributeName() {
    return sourceAttributeName;
  }

  public void setSourceAttributeName(String sourceAttributeName) {
    this.sourceAttributeName = sourceAttributeName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ForeignKeyConstraint) {
      ForeignKeyConstraint fk = (ForeignKeyConstraint) obj;
      if (fk.getSourceAttributeName().equals(sourceAttributeName)) {
        if (fk.getTargetRelationName().equals(targetRelationName)) {
          if (fk.getTargetAttributeName().equals(targetAttributeName)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return sourceRelationName + "." + sourceAttributeName + "==>" + targetRelationName + "." + targetAttributeName;
  }

}
