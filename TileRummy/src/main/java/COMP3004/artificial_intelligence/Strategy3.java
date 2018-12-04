package COMP3004.artificial_intelligence;
import COMP3004.models.Meld;
import COMP3004.models.Player;
import COMP3004.models.Table;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;

import java.util.ArrayList;

public class Strategy3 extends ArtificialIntelligence implements PlayerHandObserverInterface {
    protected ArrayList<Integer> playerHandSizes = new ArrayList<Integer>();

    public Strategy3(){ }

    @Override
    public Table play() {
        return this.table; //play(this.player.getHand());
    }

    public void update(int value, int index){
        if(index >= 0 && index < playerHandSizes.size()){
            this.playerHandSizes.set(index, value);
        }
    }

    public void setPlayerHandSizes(ArrayList<Player> players) {
        int index = 0;
        for(int i = 0; i < players.size(); i++) {
            if(this.player != players.get(i)){ //Don't track self
                this.playerHandSizes.add(players.get(i).getHand().size());
                index++;
            }
        }
    }

    public void setPlayerHandsArray(ArrayList<Integer> playerHandSizes) {
        this.playerHandSizes = playerHandSizes;
    }

    public int getPlayerHandSize(int index) {
        return this.playerHandSizes.get(index);
    }
    public void setPlayerHandSize(int value, int index) {
        this.playerHandSizes.set(index,value);
    }
    
    public int getPlayerHandSizesLength() {
        return playerHandSizes.size();
    }


    
    public Table play(Meld hand) {
        Table output = new Table();
        this.player.setHand(hand);

        //check with the observer
        boolean playWithHand = false;
        for (int i=0; i<this.playerHandSizes.size(); i++) {
            int check = this.getPlayerHandSize(i);
            if ((this.getHand().size() - check) > 0) {
                playWithHand = true;
                break;
            }
        }

        if (this.getScore() < 30) {
            playWithHand = true;
        }

        //if someone has 3 cards less than p3, play all possible melds
        if (playWithHand) {
            Strategy1 strat = new Strategy1();
            strat.getPlayer().setName(this.getPlayer().getName());
            strat.setTerminalView(this.terminalView);
            strat.setScore(this.getScore());
            strat.setHand(hand);
            strat.setTable(this.getTable());
            output = strat.play(hand);
            if(this.getScore() < 30) {
                this.setScore(strat.getScore());
            }
        }
        //otherwise, only interact with table
        else {
            Strategy2 strat = new Strategy2();
            strat.getPlayer().setName(this.getPlayer().getName());
            strat.setTerminalView(this.terminalView);
            strat.setScore(this.getScore());
            strat.setHand(hand);
            strat.setTable(this.getTable());
            output = strat.play(hand);
        }
        if(this.graphicalView != null){
            this.graphicalView.draw(output);
        }


        return output;
    }






}
