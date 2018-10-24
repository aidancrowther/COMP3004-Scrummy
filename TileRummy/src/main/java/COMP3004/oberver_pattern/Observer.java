package COMP3004.oberver_pattern;
/*
 * Author: Brittny Lapierre
 * This interface will provide the observer definition
 * for the observer pattern. This means that any class
 * which implements the observer interface will
 * automatically receive updates of state from the
 * subject.
 * */

import COMP3004.models.Table;

public interface Observer {
    void update(Table table);
}
