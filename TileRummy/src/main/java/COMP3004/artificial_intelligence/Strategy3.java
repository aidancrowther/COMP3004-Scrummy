package COMP3004.artificial_intelligence;
import COMP3004.models.Meld;
import COMP3004.models.Player;
import COMP3004.models.Table;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;

public class Strategy3 extends ArtificialIntelligence implements PlayerHandObserverInterface {
    protected int[] playerHandSizes;

    public Strategy3(){ }

    @Override
    public Table play() {
        return this.table; //play(this.player.getHand());
    }

    public void update(int value, int index){
        if(index >= 0 && index < playerHandSizes.length){
            this.playerHandSizes[index] = value;
        }
    }

    public void setPlayerHandSizes(Player[] players) {
        this.playerHandSizes = new int[players.length-1];
        int index = 0;
        for(int i = 0; i < players.length; i++) {
            if(i != 3){ //Don't track self - strat 3
                this.playerHandSizes[index] = players[i].getHand().size();
                index++;
            }
        }
    }

    public void setPlayerHandsArray(int[] playerHandSizes) {
        this.playerHandSizes = playerHandSizes;
    }

    public int getPlayerHandSize(int index) {
        return this.playerHandSizes[index];
    }
    public void setPlayerHandSize(int value, int index) {
        this.playerHandSizes[index] = value;
    }
    
    public int getPlayerHandSizesLength() {
        return playerHandSizes.length;
    }


    
    public Table play(Meld hand) {
        Table output = new Table();
        this.player.setHand(hand);

        //check with the observer
        boolean playWithHand = false;
        for (int i=0; i<this.playerHandSizes.length; i++) {
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

        return output;
    }






}
