package COMP3004.oberver_pattern;

public interface MultiSubjectInterface {
    public void registerTableObserver(TableObserverInterface t);
    public void removeTableObserver(TableObserverInterface t);

    public void registerPlayerHandObserver(PlayerHandObserverInterface p);
    public void removePlayerHandObserver(PlayerHandObserverInterface p);

    public void notifyObservers();
}
