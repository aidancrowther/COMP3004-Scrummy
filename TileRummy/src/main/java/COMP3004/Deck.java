/* COMP3004 - BlackJack
*  Aidan Crowther, 100980915
*/

package COMP3004;

import java.util.*;

public class Deck{

    private Stack<Tile> deck = new Stack<>();

    //Generate a new deck object containing 52 cards
    public Deck(){
        String suits[] = {"S", "C", "D", "H"};
        String values[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for(String suit : suits){
            for(String value : values){
                deck.push(new Tile(suit, value));
            }
        }
    }

    //Return the top card of the deck, removing it
    public Tile pop(){
        return deck.pop();
    }
    
    //Return true if the deck is empty
    public Boolean isEmpty(){
        return deck.isEmpty();
    }

    //Shuffle the deck
    public void shuffle(){
        Collections.shuffle(deck);
    }

    //Push a card onto the deck
    public void push(Tile c){
        deck.push(c);
    }

}
