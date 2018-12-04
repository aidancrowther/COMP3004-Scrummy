package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

import java.util.ArrayList;

public class PlayerDS3 extends Player {
    public PlayerDS3(){
        this.hand.add(new Tile('R', 1));
        this.hand.add(new Tile('R', 3));
        this.hand.add(new Tile('R', 13));

        this.hand.add(new Tile('B', 7));
        this.hand.add(new Tile('B', 8));

        this.hand.add(new Tile('O', 3));
        this.hand.add(new Tile('O', 4));
        this.hand.add(new Tile('O', 7));
        this.hand.add(new Tile('O', 10));
        this.hand.add(new Tile('O', 11));

        this.hand.add(new Tile('G', 7));
        this.hand.add(new Tile('G', 8));

        this.riggedTiles = new ArrayList<Tile>();
        this.riggedTiles.add(new Tile('B', 5));
        this.riggedTiles.add(new Tile('G', 4));
        this.riggedTiles.add(new Tile('R', 10));
        this.riggedTiles.add(new Tile('G', 3));
    }
}
