package ru.jpanda.diplom.normalizedb.core.dbconnection.data.events;

import java.util.ArrayList;

/**
 *
 * @author Alexey Storozhev
 */
public final class ChangeSupport {
  private ArrayList<ChangeListener> listeners = new ArrayList<>();

  public void addChangeListener(ChangeListener listener) {
    listeners.add(listener);
  }

  public void removeChangeListener(ChangeListener listener) {
    listeners.remove(listener);
  }

  public void fireBeforeChange() {
    distributeEvent(new Change(this, Time.BEFORECHANGE));
  }

  public void fireChange(Time time) {
    distributeEvent(new Change(this, time));
  }

  public void fireAfterChange() {
    distributeEvent(new Change(this, Time.AFTERCHANGE));
  }

  private void distributeEvent(Change changeEvent) {
    for (ChangeListener listener : listeners) {
      listener.Change(changeEvent);
    }
  }
}
