package COMP3004.oberver_pattern;
/*
* Author: Brittny Lapierre
* This interface will provide the subject functionality
* within the observer pattern. The subject has a list
* of observers, which it will notify when changes of
* state occur.
* */

public interface Subject {
    void registerObserver(TableObserver t);
    void removeObserver(TableObserver t);
    void notifyObservers();
}
