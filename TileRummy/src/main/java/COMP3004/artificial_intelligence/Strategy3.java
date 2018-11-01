package COMP3004.artificial_intelligence;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;

public class Strategy3 extends ArtificialIntelligence implements PlayerHandObserverInterface {
    public int[] playerHandSizes = new int[4];

    public Strategy3(){ }

    public Table play(Meld m) {
        return null;
    }

    public void update(int value, int index){
        if(index >= 0 && index < playerHandSizes.length){
            System.out.println("Player #:");
            System.out.println(index);
            System.out.println("Hand size was:");
            System.out.println(this.playerHandSizes[index]);
            this.playerHandSizes[index] = value;
            System.out.println("And is now:");
            System.out.println(this.playerHandSizes[index]);
        }
    }

    public int getPlayerHandSize(int index) {
        return this.playerHandSizes[index];
    }
    public void setPlayerHandSize(int value, int index) {
        this.playerHandSizes[index] = value;
    }
}
