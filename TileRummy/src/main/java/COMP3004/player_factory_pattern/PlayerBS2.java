package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

public class PlayerBS2 extends Player {
    public PlayerBS2(){
        this.hand.add(new Tile('R', 1));
        this.hand.add(new Tile('R', 2));
        this.hand.add(new Tile('R', 3));
        this.hand.add(new Tile('R', 6));
        this.hand.add(new Tile('R', 5));
        this.hand.add(new Tile('R', 6));
        this.hand.add(new Tile('R', 7));
        this.hand.add(new Tile('R', 8));
        this.hand.add(new Tile('R', 9));
        this.hand.add(new Tile('R', 10));
        this.hand.add(new Tile('R', 11));
        this.hand.add(new Tile('R', 12));
        this.hand.add(new Tile('R', 13));
    }
}
