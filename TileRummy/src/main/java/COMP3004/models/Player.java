/* COMP3004 - BlackJack
*  Aidan Crowther, 100980915
*/

package COMP3004.models;

public class Player{
    private String name = "player";
    Meld hand = new Meld();

    public Player(){

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
}