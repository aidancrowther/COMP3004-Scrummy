package COMP3004;

import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;



//Test all AI strategies
public class StrategyTest{

    @Test
    //Assert that update will change the table
    public void testUpdate() {
        Table toAdd = new Table();
        Strategy1 AI1 = new Strategy1();

        //Assert that the table is correctly initialized and passed by reference
        assertTrue(AI1.table == null);
        AI1.update(toAdd);
        assertTrue(AI1.table == toAdd);
    }

    @Test
    //Assert that the selectTile method will move tiles between melds correctly
    public void testSelectTile(){

        Strategy1 AI1 = new Strategy1();
        //Generate a small list of tiles and melds for the test
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int i=1; i<=13; i++) tiles.add(new Tile('O', i));

        Meld meld1 = new Meld();
        meld1.add(tiles.get(0));
        meld1.add(tiles.get(1));
        meld1.add(tiles.get(2));

        Meld meld2= new Meld();
        meld2.add(tiles.get(3));
        meld2.add(tiles.get(4));
        meld2.add(tiles.get(5));

        for(int i=1; i<=3; i++) assertTrue(meld1.getTiles().get(i-1).toString().equals("O"+i));
        for(int i=4; i<=6; i++) assertTrue(meld2.getTiles().get(i-4).toString().equals("O"+i));

        //Test moving a tile that is in meld1 to meld2
        AI1.selectTile(meld1, meld2, tiles.get(0));
        assertFalse(meld1.getTiles().contains(tiles.get(0)));
        assertTrue(meld2.getTiles().contains(tiles.get(0)));

        //Test moving a tile that is not in meld1 to meld2
        AI1.selectTile(meld1, meld2, tiles.get(0));
        assertFalse(meld1.getTiles().size() < 2);
        assertTrue(meld2.getTiles().size() == 4);
    }

}