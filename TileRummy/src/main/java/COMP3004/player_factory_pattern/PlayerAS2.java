package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

public class PlayerAS2 extends Player {
    public PlayerAS2(){
        this.hand.add(new Tile('G', 1));
        this.hand.add(new Tile('G', 2));
        this.hand.add(new Tile('B', 3));
        this.hand.add(new Tile('G', 6));
        this.hand.add(new Tile('G', 5));
        this.hand.add(new Tile('B', 6));
        this.hand.add(new Tile('G', 7));
        this.hand.add(new Tile('G', 8));
        this.hand.add(new Tile('B', 9));
        this.hand.add(new Tile('G', 10));
        this.hand.add(new Tile('O', 11));
        this.hand.add(new Tile('O', 12));
        this.hand.add(new Tile('O', 13));
    }
}
