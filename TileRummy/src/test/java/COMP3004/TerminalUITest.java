package COMP3004;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


//Test all AI strategies
public class TerminalUITest {

    @Test
    //Assert that update will change the table
    public void testSetHand() {
        TerminalUI terminal = new TerminalUI();
        //Generate a small list of tiles and melds for the test
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int i=1; i<=6; i++) tiles.add(new Tile('O', i));

        Meld meld1 = new Meld();
        meld1.add(tiles.get(0));
        meld1.add(tiles.get(1));
        meld1.add(tiles.get(2));

        for(int i=1; i<=3; i++) assertTrue(meld1.getTiles().get(i-1).toString().equals("O"+i));

        terminal.setHand(meld1);
        assertTrue(terminal.getHand().equals(meld1));
        meld1 = null;
        assertFalse(terminal.getHand() == null);
    }

    @Test
    //Assert that the selectTile method will move tiles between melds correctly
    public void testSelectTile(){
        TerminalUI terminal = new TerminalUI();
        //Generate a small list of tiles and melds for the test
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int i=1; i<=6; i++) tiles.add(new Tile('O', i));

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
        terminal.selectTile(meld1, meld2, tiles.get(0));
        assertFalse(meld1.getTiles().contains(tiles.get(0)));
        assertTrue(meld2.getTiles().contains(tiles.get(0)));

        //Test moving a tile that is not in meld1 to meld2
        terminal.selectTile(meld1, meld2, tiles.get(0));
        assertFalse(meld1.getTiles().size() < 2);
        assertTrue(meld2.getTiles().size() == 4);
    }

    @Test
    public void testGenerateTileString() {
        TerminalUI terminal = new TerminalUI();
        ArrayList<Tile> allTiles = new ArrayList<>();
        char colours[] = {'R', 'G', 'B', 'O'};
        int values[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

        for(int colour = 0; colour < 8; colour++){
            for(int value : values){
                allTiles.add(new Tile(colours[colour%4], value));
            }
        }

        for(Tile t : allTiles) {
            String tileColour = terminal.ANSI_BLUE;
            if(t.getColour() == 'R'){
                tileColour = terminal.ANSI_RED;
            } else if(t.getColour() == 'G') {
                tileColour = terminal.ANSI_GREEN;

            } else if (t.getColour() == 'O') {
                tileColour = terminal.ANSI_YELLOW; //best i can do
            }
            assertEquals("|" + tileColour + t.getValue() + terminal.ANSI_RESET + "|", terminal.generateTileString(t).replace(" ", ""));
        }
    }


    @Test
    //Assert that the selectTile method will move tiles between melds correctly
    public void testMoveOnInput(){
        TerminalUI terminal = new TerminalUI();
        Table table = new Table();
        //Generate a small list of tiles and melds for the test
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int i=1; i<=9; i++) tiles.add(new Tile('O', i));

        Meld hand = new Meld();
        hand.add(tiles.get(0));
        hand.add(tiles.get(1));
        hand.add(tiles.get(2));
        terminal.setHand(hand);

        Meld meld2= new Meld();
        meld2.add(tiles.get(3));
        meld2.add(tiles.get(4));
        meld2.add(tiles.get(5));
        table.add(meld2);

        Meld meld3= new Meld();
        meld2.add(tiles.get(6));
        meld2.add(tiles.get(7));
        meld2.add(tiles.get(8));
        table.add(meld3);


        //TEST USER SELECT HAND, MELD2
        terminal.selectTile(hand, table.getMelds().get(0), tiles.get(0));
        assertFalse(hand.getTiles().contains(tiles.get(0)));
        assertTrue(table.getMelds().get(0).getTiles().contains(tiles.get(0)));
        //TEST USER SELECT HAND, MELD3
        terminal.selectTile(hand, table.getMelds().get(1), tiles.get(1));
        assertFalse(hand.getTiles().contains(tiles.get(1)));
        assertTrue(table.getMelds().get(1).getTiles().contains(tiles.get(1)));
        //TEST USER SELECT MELD2, MELD3
        terminal.selectTile(table.getMelds().get(0), table.getMelds().get(1), tiles.get(3));
        assertFalse(hand.getTiles().contains(tiles.get(3)));
        assertTrue(table.getMelds().get(1).getTiles().contains(tiles.get(3)));
    }
    /*
    @Test
    public void testPlayEmptyTable() {}

    @Test
    public void testDifferentTableStates() {
        does play() react appropriately to:
            Table it can't play
            Table it can play melds and add to melds
            Table it can only play melds
            Table it can only add to preexisting melds
    }

    */

}