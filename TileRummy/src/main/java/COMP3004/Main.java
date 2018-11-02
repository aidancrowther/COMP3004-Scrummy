package COMP3004;

import COMP3004.controllers.Controller;
import COMP3004.artificial_intelligence.*;
import COMP3004.models.*;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        /*
        Controller controller = new Controller();
        char viewType = 't';//"g";
        controller.setInteractionType(viewType); // TODO: make interactive
        if(viewType == 'g'){
            controller.launchGraphicalView(args);
        }
        controller.run(true);
        */

        //Generate tiles to use
        Tile tile1 = new Tile('O', 10);
        Tile tile2 = new Tile('O', 9);
        Tile tile3 = new Tile('O', 8);
        Tile tile4 = new Tile('B', 6);
        Tile tile5 = new Tile('G', 6);
        Tile tile6 = new Tile('O', 7);
        Tile tile7 = new Tile('R', 6);
        Tile tile8 = new Tile('O', 6);

        //Initialize a hand that can't generate a play of 30
        Meld hand = new Meld();
        hand.add(tile4);

        //Initialize AI with the specified hand
        Strategy1 AI1 = new Strategy1();
        AI1.setHand(hand);
        AI1.setScore(30);

        //Build a table that the player can't add to
        Table table = new Table();
        table.add(tile2);
        table.add(tile3);
        table.add(tile6);
        AI1.setTable(table);

        //Assert that the player does not play onto the table
        Table output = AI1.play(AI1.getHand());

        //Update the table so that the player can only add to existing melds
        table = new Table();
        table.add(tile7);
        table.add(tile8);
        table.add(tile5);
        AI1.setTable(table);

        //Derive our expected output
        Meld expected = new Meld();
        expected.add(tile7);
        expected.add(tile8);
        expected.add(tile5);
        expected.add(tile4);

        System.out.println(AI1.getScore());

        System.out.println(AI1.getHand().toString());
        for(Meld m : AI1.getTable().getMelds()) System.out.println(m.toString());

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI1.play(AI1.getHand());
        ArrayList<Meld> melds = output.getMelds();

        for(Meld m : melds) System.out.println(m.toString());

    }
}
