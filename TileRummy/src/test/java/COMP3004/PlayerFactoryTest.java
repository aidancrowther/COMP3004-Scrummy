package COMP3004;

import COMP3004.artificial_intelligence.Strategy1;
import COMP3004.models.Meld;
import COMP3004.models.Player;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.player_factory_pattern.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/*
* Scenarios:
* s1: PlayerAS1 with planned plays + unlucky PlayerBS1 who won't play at all
 * */
public class PlayerFactoryTest {
    @Test
    public void testScenarioOneSetup() {
        Table table = new Table();
        PlayerFactory factory = new PlayerFactory();

        //PLAYER TYPE ONE
        Player p = factory.FactoryMethod(0);
        ArrayList<Tile> test = new ArrayList<Tile>();
        test.add(new Tile('0', 1));
        test.add(new Tile('0', 2));
        test.add(new Tile('0', 3));
        test.add(new Tile('R', 6));
        test.add(new Tile('0', 5));
        test.add(new Tile('0', 6));
        test.add(new Tile('R', 7));
        test.add(new Tile('0', 8));
        test.add(new Tile('0', 9));
        test.add(new Tile('0', 10));
        test.add(new Tile('0', 11));
        test.add(new Tile('0', 12));
        test.add(new Tile('0', 13));
        assertTrue(tileListsEqual(test, p.getHand().getTiles()));

        ArrayList<Tile> testTwo = new ArrayList<Tile>();
        testTwo.add(new Tile('R', 8));
        testTwo.add(new Tile('O', 7));
        assertTrue(tileListsEqual(testTwo, p.getRiggedTiles()));

        //TEST FIRST MOVE
        Strategy1 s = new Strategy1();
        s.setPlayer(p);
        s.setTable(table);
        table = s.play(p.getHand());
        System.out.println(table.toString());
        assertTrue(table.getMelds().size() == 4);


        ArrayList played = new ArrayList();
        played.addAll(table.getMelds().get(1).getTiles());
        played.addAll(table.getMelds().get(2).getTiles());


        ArrayList<Tile> playedMeld1 = new ArrayList<Tile>();
        playedMeld1.add(new Tile('0', 8));
        playedMeld1.add(new Tile('0', 9));
        playedMeld1.add(new Tile('0', 10));

        ArrayList<Tile> playedMeld2 = new ArrayList<Tile>();
        playedMeld2.add(new Tile('0', 1));
        playedMeld2.add(new Tile('0', 2));
        playedMeld2.add(new Tile('0', 3));

        ArrayList<Tile> playedMeld3 = new ArrayList<Tile>();
        playedMeld3.add(new Tile('0', 11));
        playedMeld3.add(new Tile('0', 12));
        playedMeld3.add(new Tile('0', 13));

        assertTrue(tableContainsMeld(table, playedMeld1));
        assertTrue(tableContainsMeld(table, playedMeld2));
        assertTrue(tableContainsMeld(table, playedMeld3));

        Player p2 = factory.FactoryMethod(1);
        ArrayList<Tile> test2 = new ArrayList<Tile>();
        test.add(new Tile('G', 1));
        test.add(new Tile('B', 2));
        test.add(new Tile('G', 3));
        test.add(new Tile('B', 6));
        test.add(new Tile('G', 5));
        test.add(new Tile('B', 6));
        test.add(new Tile('G', 7));
        test.add(new Tile('B', 8));
        test.add(new Tile('G', 9));
        test.add(new Tile('B', 10));
        test.add(new Tile('G', 11));
        test.add(new Tile('B', 12));
        test.add(new Tile('G', 13));
        assertTrue(tileListsEqual(test, p2.getHand().getTiles()));

        ArrayList<Tile> testTwo2 = new ArrayList<Tile>();
        testTwo.add(new Tile('R', 1));
        testTwo.add(new Tile('R', 3));
        testTwo.add(new Tile('R', 10));
        testTwo.add(new Tile('R', 12));
        assertTrue(tileListsEqual(testTwo, p2.getRiggedTiles()));
    }

    /*
     * //test s2 move after s1 plays it's first 30 + it can play all cards
     * s2: PlayerAS2 (s1) [will play on first turn] + PlayerBS2 (s2) [has run of all color]
     *
    * */
    @Test
    public void testScenarioTwoSetup() {
        Table table = new Table();
        PlayerFactory factory = new PlayerFactory();

        //PLAYER TWO
        Player p = factory.FactoryMethod(2);
        ArrayList<Tile> test = new ArrayList<Tile>();
        test.add(new Tile('G', 1));
        test.add(new Tile('G', 2));
        test.add(new Tile('B', 3));
        test.add(new Tile('G', 6));
        test.add(new Tile('G', 5));
        test.add(new Tile('B', 6));
        test.add(new Tile('G', 7));
        test.add(new Tile('G', 8));
        test.add(new Tile('B', 9));
        test.add(new Tile('G', 10));
        test.add(new Tile('O', 11));
        test.add(new Tile('O', 12));
        test.add(new Tile('O', 13));
        assertTrue(tileListsEqual(test, p.getHand().getTiles()));

        //PLAYER TWO - will win on first turn
        Player p2 = factory.FactoryMethod(3);
        ArrayList<Tile> test2 = new ArrayList<Tile>();
        test.add(new Tile('R', 1));
        test.add(new Tile('R', 2));
        test.add(new Tile('R', 3));
        test.add(new Tile('R', 6));
        test.add(new Tile('R', 5));
        test.add(new Tile('R', 6));
        test.add(new Tile('R', 7));
        test.add(new Tile('R', 8));
        test.add(new Tile('R', 9));
        test.add(new Tile('R', 10));
        test.add(new Tile('R', 11));
        test.add(new Tile('R', 12));
        test.add(new Tile('R', 13));
        assertTrue(tileListsEqual(test, p.getHand().getTiles()));
    }

/*
*
 * Brittny's testplan
 * s3: PlayerAS3 (s1) + PlayerBS3 (s1) + PlayerCS3 (s2) + PlayerDS3 (s2)
*
* */
    @Test
    public void testScenarioThreeSetup() {
        //Setup same as two, add a player four
        Table table = new Table();
        PlayerFactory factory = new PlayerFactory();

        //PLAYER ONE
        Player p = factory.FactoryMethod(4);
        ArrayList<Tile> test = new ArrayList<Tile>();
        test.add(new Tile('R', 2));
        test.add(new Tile('R', 4));
        test.add(new Tile('R', 5));
        test.add(new Tile('R', 8));
        test.add(new Tile('R', 9));
        test.add(new Tile('R', 9));
        test.add(new Tile('R', 10));

        test.add(new Tile('B', 1));
        test.add(new Tile('B', 10));

        test.add(new Tile('O', 2));
        test.add(new Tile('O', 2));

        test.add(new Tile('G', 5));
        test.add(new Tile('G', 6));
        test.add(new Tile('G', 10));
        assertTrue(tileListsEqual(test, p.getHand().getTiles()));

        ArrayList<Tile> testTwo = new ArrayList<Tile>();
        //testTwo.add(new Tile('R', 8));
        //testTwo.add(new Tile('O', 7));
        assertTrue(tileListsEqual(testTwo, p.getRiggedTiles()));

        //PLAYER 2
        Player p2 = factory.FactoryMethod(5);
        ArrayList<Tile> test2 = new ArrayList<Tile>();
        test.add(new Tile('R', 6));
        test.add(new Tile('R', 7));

        test.add(new Tile('B', 3));
        test.add(new Tile('B', 4));
        test.add(new Tile('B', 5));
        test.add(new Tile('B', 9));
        test.add(new Tile('B', 11));

        test.add(new Tile('O', 4));
        test.add(new Tile('O', 5));
        test.add(new Tile('O', 8));
        test.add(new Tile('O', 13));

        test.add(new Tile('G', 1));
        test.add(new Tile('G', 2));
        test.add(new Tile('G', 8));
        test.add(new Tile('G', 11));
        assertTrue(tileListsEqual(test2, p2.getHand().getTiles()));

        //PLAYER 3
        Player p3 = factory.FactoryMethod(6);
        ArrayList<Tile> test3 = new ArrayList<Tile>();
        test.add(new Tile('R', 11)); //TODO: CHANGE TO JOKER
        test.add(new Tile('R', 12));

        test.add(new Tile('B', 1));
        test.add(new Tile('B', 2));
        test.add(new Tile('B', 6));
        test.add(new Tile('B', 8));

        test.add(new Tile('O', 3));
        test.add(new Tile('O', 10));
        test.add(new Tile('O', 11));
        test.add(new Tile('O', 12));

        test.add(new Tile('G', 1));
        test.add(new Tile('G', 5));
        test.add(new Tile('G', 6));
        test.add(new Tile('G', 13));
        assertTrue(tileListsEqual(test3, p3.getHand().getTiles()));

        //PLAYER 3
        Player p4 = factory.FactoryMethod(7);
        ArrayList<Tile> test4 = new ArrayList<Tile>();
        test.add(new Tile('R', 1));
        test.add(new Tile('R', 3));
        test.add(new Tile('R', 13));

        test.add(new Tile('B', 7));
        test.add(new Tile('B', 8));

        test.add(new Tile('O', 3));
        test.add(new Tile('O', 4));
        test.add(new Tile('O', 7));
        test.add(new Tile('O', 10));
        test.add(new Tile('O', 11));

        test.add(new Tile('G', 7));
        test.add(new Tile('G', 8));
        assertTrue(tileListsEqual(test4, p4.getHand().getTiles()));
    }

    @Test
    public void testCreatePlayerAIFour() {
        //maybe wont need cause only supposed to support up to ai3
    }

    public boolean tileListsEqual(ArrayList<Tile> one, ArrayList<Tile> two){
        if(one.size() == two.size()){
            for(Tile t : two){
                if(!containsTile(one,t)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean containsTile(ArrayList<Tile> array, Tile t) {
        for(Tile curr : array) {
            if(curr.getValue() == t.getValue()
            && curr.getColour() == t.getColour()){
                return true;
            }
        }
        return false;
    }

    public boolean tableContainsMeld(Table t, ArrayList<Tile> tiles){
        for(Meld m : t.getMelds()){
            if(tileListsEqual(m.getTiles(), tiles)){
                return true;
            }
        }
        return false;
    }

}
