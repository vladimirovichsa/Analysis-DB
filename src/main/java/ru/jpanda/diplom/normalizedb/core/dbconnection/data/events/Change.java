package ru.jpanda.diplom.normalizedb.core.dbconnection.data.events;

import java.util.EventObject;

/**
 *
 * @author Alexey Storozhev
 */
public class Change extends EventObject {

  private static final long serialVersionUID = -8846307959662456247L;
  private final Time time;

  public Change(Object source, Time time) {
    super(source);
    this.time = time;
  }

  public Time getTime() {
    return time;
  }

}
