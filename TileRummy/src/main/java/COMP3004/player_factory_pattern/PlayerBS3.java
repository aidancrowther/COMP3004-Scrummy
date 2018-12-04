package COMP3004.player_factory_pattern;

import COMP3004.models.Player;
import COMP3004.models.Tile;

import java.util.ArrayList;

public class PlayerBS3 extends Player {
    public PlayerBS3(){
        this.hand.add(new Tile('R', 6));
        this.hand.add(new Tile('R', 7));

        this.hand.add(new Tile('B', 3));
        this.hand.add(new Tile('B', 4));
        this.hand.add(new Tile('B', 5));
        this.hand.add(new Tile('B', 9));
        this.hand.add(new Tile('B', 11));

        this.hand.add(new Tile('O', 4));
        this.hand.add(new Tile('O', 5));
        this.hand.add(new Tile('O', 8));
        this.hand.add(new Tile('O', 13));


        this.hand.add(new Tile('G', 1));
        this.hand.add(new Tile('G', 2));
        this.hand.add(new Tile('G', 8));
        this.hand.add(new Tile('G', 11));

        this.riggedTiles = new ArrayList<Tile>();
        this.riggedTiles.add(new Tile('O', 12));
        this.riggedTiles.add(new Tile('R', 4));
        this.riggedTiles.add(new Tile('R', 6));
        this.riggedTiles.add(new Tile('O', 11)); //TODO: JOKER
        this.riggedTiles.add(new Tile('G', 3));
    }
}
