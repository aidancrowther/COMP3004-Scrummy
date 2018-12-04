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

public class PlayerFactoryTest {
    @Test
    public void testCreatePlayerAIOne() {
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

        assertTrue(tileListsEqual(table.getMelds().get(1).getTiles(), playedMeld1));
        assertTrue(tileListsEqual(table.getMelds().get(2).getTiles(), playedMeld2));
        assertTrue(tileListsEqual(table.getMelds().get(3).getTiles(), playedMeld3));
    }

    /*
    *

    //PLAYER 2
    p = factory.FactoryMethod(1);


    //PLAYER 3

    assertTrue(p instanceof PlayerSecondAI);
    p = factory.FactoryMethod(2);
    assertTrue(p instanceof PlayerThirdAI);
    p = factory.FactoryMethod(3);
    assertTrue(p instanceof PlayerFourthAI);
    * */

    @Test
    public void testCreatePlayerAITwo() {
        Table table = new Table();
        PlayerFactory factory = new PlayerFactory();

        //PLAYER TWO
        Player p = factory.FactoryMethod(2);
        ArrayList<Tile> test = new ArrayList<Tile>();
        test.add(new Tile('B', 1));
        test.add(new Tile('B', 2));

        test.add(new Tile('G', 3));

        test.add(new Tile('B', 6));
        test.add(new Tile('B', 5));

        test.add(new Tile('G', 6));

        test.add(new Tile('B', 7));
        test.add(new Tile('B', 8));

        test.add(new Tile('G', 9));
        test.add(new Tile('G', 10));

        test.add(new Tile('R', 11));
        test.add(new Tile('R', 12));
        test.add(new Tile('R', 13));
        assertTrue(tileListsEqual(test, p.getHand().getTiles()));

        ArrayList<Tile> testTwo = new ArrayList<Tile>();
        testTwo.add(new Tile('G', 4));
        testTwo.add(new Tile('G', 5));
        testTwo.add(new Tile('B', 3));
        testTwo.add(new Tile('B', 6));
        testTwo.add(new Tile('B', 9));
        testTwo.add(new Tile('B', 10));
        assertTrue(tileListsEqual(testTwo, p.getRiggedTiles()));
    }


    @Test
    public void testCreatePlayerAIThree() {
        //Setup same as two, add a player four
        Table table = new Table();
        PlayerFactory factory = new PlayerFactory();

        //PLAYER ONE
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

}
