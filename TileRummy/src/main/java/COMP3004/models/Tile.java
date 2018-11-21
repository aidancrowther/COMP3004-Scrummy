/* COMP3004 - BlackJack
*  Aidan Crowther, 100980915
*  David N. Zilio, 100997259
*/

package COMP3004.models;

public class Tile{
    
    private char colour;
    private int value;

    //Gnerate a new card using the specified suit and value
    public Tile(char colour, int value){
        this.colour = colour;
        this.value = value;
    }

    //Return the cards suit
    public char getColour(){
        return colour;
    }

    //Return the cards value
    public int getValue(){
        return value;
    }

    public void setColour(char c) {
        this.colour = c;
    }

    public void setValue(int v) {
        this.value = v;
    }

    //Check if the tile is equal to the tile passed in
    public Boolean equals(Tile t){
        Boolean result = true;
        result &= this.getColour() == t.getColour();
        result &= this.getValue() == t.getValue();
        return result;
    }

    public boolean isJoker() {
        if (this instanceof COMP3004.models.Joker) {
            return true;
        }
        else {
            return false;
        }
    }

    //Print the cards suit and value in a human readable format
    @Override
    public String toString(){
        return ""+colour+value;
    }

}