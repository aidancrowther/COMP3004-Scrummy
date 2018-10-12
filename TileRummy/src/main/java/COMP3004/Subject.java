package COMP3004;
/*
* Author: Brittny Lapierre
* This interface will provide the subject functionality
* within the observer pattern. The subject has a list
* of observers, which it will notify when changes of
* state occur.
* */

public interface Subject {
    void registerObserver();
    void removeObserver();
    void notifyObservers();
}
