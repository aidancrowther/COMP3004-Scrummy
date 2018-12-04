package COMP3004.player_factory_pattern;
import COMP3004.models.Player;
import COMP3004.models.Tile;

import java.util.ArrayList;

public class PlayerCS3 extends Player {
    public PlayerCS3(){
        this.hand.add(new Tile('R', 11)); //TODO: CHANGE TO JOKER
        this.hand.add(new Tile('R', 12));

        this.hand.add(new Tile('B', 1));
        this.hand.add(new Tile('B', 2));
        this.hand.add(new Tile('B', 6));
        this.hand.add(new Tile('B', 8));

        this.hand.add(new Tile('O', 3));
        this.hand.add(new Tile('O', 10));
        this.hand.add(new Tile('O', 11));
        this.hand.add(new Tile('O', 12));

        this.hand.add(new Tile('G', 1));
        this.hand.add(new Tile('G', 5));
        this.hand.add(new Tile('G', 6));
        this.hand.add(new Tile('G', 13));

        this.riggedTiles = new ArrayList<Tile>();
        this.riggedTiles.add(new Tile('O', 1));
        this.riggedTiles.add(new Tile('B', 10));
        this.riggedTiles.add(new Tile('O', 13));
        this.riggedTiles.add(new Tile('B', 12));
    }
}
