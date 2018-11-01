package COMP3004.oberver_pattern;

import COMP3004.models.Player;
import COMP3004.models.Table;

import java.util.ArrayList;

public abstract class MultiSubject implements MultiSubjectInterface{
    protected Table table = new Table();
    protected Player[] players = new Player[4];

    protected ArrayList<TableObserverInterface> tableObservers = new ArrayList<>();
    protected ArrayList<PlayerHandObserverInterface> playerHandObservers = new ArrayList<>();

    public void registerTableObserver(TableObserverInterface t){
        this.tableObservers.add(t);
    }

    public void removeTableObserver(TableObserverInterface t){
        this.tableObservers.remove(t);
    }

    public void registerPlayerHandObserver(PlayerHandObserverInterface p){
        this.playerHandObservers.add(p);
    }

    public void removePlayerHandObserver(PlayerHandObserverInterface p){
        this.playerHandObservers.remove(p);
    }

    public void notifyObservers(){
        for(TableObserverInterface observer : this.tableObservers) {
            observer.update(this.table);
        }

        int index = 0;
        for(PlayerHandObserverInterface observer : this.playerHandObservers) {
            observer.update(this.players[index].getHand().size(), index);
            index++;
        }
    }
}
