package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

import java.util.ArrayList;

// AI HAND > [O1,O2,O3,R6,O5,O6,R7,O8,O9,O11,O12,013]
//AI DRAW ORDER [R8, O7]
//> WILL PLAY THESE MOVES
//01, 02, 03
//O8,O9,011, 012, 013
//NOTHING > R8
// R6 R7 R8
//NOTHING > O7
//05, 06, 07

public class PlayerAS1 extends Player {
    public PlayerAS1(){
        this.hand.add(new Tile('O', 1));
        this.hand.add(new Tile('O', 2));
        this.hand.add(new Tile('O', 3));
        this.hand.add(new Tile('R', 6));
        this.hand.add(new Tile('O', 5));
        this.hand.add(new Tile('O', 6));
        this.hand.add(new Tile('R', 7));
        this.hand.add(new Tile('O', 8));
        this.hand.add(new Tile('O', 9));
        this.hand.add(new Tile('O', 10));
        this.hand.add(new Tile('O', 11));
        this.hand.add(new Tile('O', 12));
        this.hand.add(new Tile('O', 13));

        this.riggedTiles = new ArrayList<Tile>();
        this.riggedTiles.add(new Tile('R', 8));
        this.riggedTiles.add(new Tile('O', 7));
    }
}
