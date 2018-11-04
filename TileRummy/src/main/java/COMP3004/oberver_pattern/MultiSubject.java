package COMP3004.oberver_pattern;

import COMP3004.artificial_intelligence.ArtificialIntelligence;
import COMP3004.artificial_intelligence.Strategy1;
import COMP3004.artificial_intelligence.Strategy3;
import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Player;
import COMP3004.models.Table;

import java.util.ArrayList;

public abstract class MultiSubject implements MultiSubjectInterface{
    protected Table table = new Table();
    protected Player[] players = new Player[5];

    protected ArrayList<GameInteractionController> tableObservers = new ArrayList<>();
    protected ArrayList<Strategy3> playerHandObservers = new ArrayList<>(); //TODO: if you need this more general create a super class

    public void registerTableObserver(ArtificialIntelligence t){
        this.tableObservers.add(t);
    }

    public void removeTableObserver(ArtificialIntelligence t){
        this.tableObservers.remove(t);
    }

    public void registerTableObserver(GameInteractionController t){
        this.tableObservers.add(t);
    }

    public void removeTableObserver(GameInteractionController t){
        this.tableObservers.remove(t);
    }

    public void registerPlayerHandObserver(Strategy3 p){
        this.playerHandObservers.add(p);
    }

    public void removePlayerHandObserver(Strategy3 p){
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
