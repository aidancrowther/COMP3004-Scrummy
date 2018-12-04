package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

import java.util.ArrayList;

public class PlayerAS3 extends Player {
    public  PlayerAS3(){
        this.hand.add(new Tile('R', 2));
        this.hand.add(new Tile('R', 4));
        this.hand.add(new Tile('R', 5));
        this.hand.add(new Tile('R', 8));
        this.hand.add(new Tile('R', 9));
        this.hand.add(new Tile('R', 9));
        this.hand.add(new Tile('R', 10));

        this.hand.add(new Tile('B', 1));
        this.hand.add(new Tile('B', 10));

        this.hand.add(new Tile('O', 2));
        this.hand.add(new Tile('O', 2));

        this.hand.add(new Tile('G', 5));
        this.hand.add(new Tile('G', 6));
        this.hand.add(new Tile('G', 10));

        this.riggedTiles = new ArrayList<Tile>();
        this.riggedTiles.add(new Tile('B', 13));
        this.riggedTiles.add(new Tile('R', 11));
        this.riggedTiles.add(new Tile('0', 05));
        this.riggedTiles.add(new Tile('G', 7));
    }
}
