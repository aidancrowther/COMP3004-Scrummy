package COMP3004.artificial_intelligence;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;

public class Strategy3 extends ArtificialIntelligence implements PlayerHandObserverInterface {
    protected int[] playerHandSizes;

    public Strategy3(){ }

    @Override
    public Table play() {
        return play(this.player.getHand());
    }

    public void update(int value, int index){
        if(index >= 0 && index < playerHandSizes.length){
            this.playerHandSizes[index] = value;
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



    public Table play(Meld hand) {
        boolean playWithHand = false;
        for (int i=0; i<5; i++) {
            int check = this.getPlayerHandSize(i);
            if ((this.getHand().size() - check) > 0) {
                playWithHand = true;
            }
        }

        if (playWithHand) {
            Strategy1 player = new Strategy1();
            player.setScore(this.getScore());
            player.setHand(this.getHand());
            player.setTable(this.getTable());
            return player.play();
        }
        else {
            Strategy2 player = new Strategy2();
            player.setScore(this.getScore());
            player.setHand(this.getHand());
            player.setTable(this.getTable());
            return player.play();
        }

    }






}
