package COMP3004.artificial_intelligence;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;

public class Strategy3 extends ArtificialIntelligence implements PlayerHandObserverInterface {
    protected int[] playerHandSizes = new int[4];

    public Strategy3(){ }

    @Override
    public Table play() {
        return play(hand);
    }

    public void update(int value, int index){
        if(index >= 0 && index < playerHandSizes.length){
            this.playerHandSizes[index] = value;
        }
    }

    public int getPlayerHandSize(int index) {
        return this.playerHandSizes[index];
    }
    public void setPlayerHandSize(int value, int index) {
        this.playerHandSizes[index] = value;
    }



    public Table play(Meld hand) {
        /*
        
        
        
        */
        return null;
    }






}
