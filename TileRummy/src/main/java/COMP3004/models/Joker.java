package COMP3004.models;

import COMP3004.models.Tile;

public class Joker extends Tile {

    public Joker() {
        super('J', 0);
        this.colour = 'J';
        this.value = 0;
    }

    //Print the cards suit and value in a human readable format
    @Override
    public String toString(){
        return ""+this.colour+this.value;
    }


}