package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

import java.util.ArrayList;

public class PlayerBS1 extends Player {
    public PlayerBS1(){
        this.hand.add(new Tile('G', 1));
        this.hand.add(new Tile('B', 2));
        this.hand.add(new Tile('G', 3));
        this.hand.add(new Tile('B', 6));
        this.hand.add(new Tile('G', 5));
        this.hand.add(new Tile('B', 6));
        this.hand.add(new Tile('G', 7));
        this.hand.add(new Tile('B', 8));
        this.hand.add(new Tile('G', 9));
        this.hand.add(new Tile('B', 10));
        this.hand.add(new Tile('G', 11));
        this.hand.add(new Tile('B', 12));
        this.hand.add(new Tile('G', 13));

        this.riggedTiles = new ArrayList<Tile>();
        this.riggedTiles.add(new Tile('R', 1));
        this.riggedTiles.add(new Tile('R', 3));
        this.riggedTiles.add(new Tile('R', 10));
        this.riggedTiles.add(new Tile('R', 12));
    }
}
