/* COMP3004 - BlackJack
*  Aidan Crowther, 100980915
*/

package COMP3004.models;

import java.util.ArrayList;

public class Player{
    private String name = "player";
    protected Meld hand = new Meld();
    protected ArrayList<Tile> riggedTiles;

    public Player(){

    }

    public Player(String name){
        this.name = name;
    }

    public void setHand(Meld hand) {
        this.hand = hand;
    }

    public Meld getHand() { return this.hand; }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Tile> getRiggedTiles() {
        return riggedTiles;
    }

    public void setRiggedTiles(ArrayList<Tile> riggedTiles) {
        this.riggedTiles = riggedTiles;
    }
}