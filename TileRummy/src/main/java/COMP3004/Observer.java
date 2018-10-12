package COMP3004;
/*
 * Author: Brittny Lapierre
 * This interface will provide the observer definition
 * for the observer pattern. This means that any class
 * which implements the observer interface will
 * automatically receive updates of state from the
 * subject.
 * */

public interface Observer {
    void update();
}
