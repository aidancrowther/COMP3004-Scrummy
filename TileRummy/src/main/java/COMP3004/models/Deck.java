/* COMP3004 - BlackJack
*  Aidan Crowther, 100980915
*/

package COMP3004.models;

import java.lang.reflect.Array;
import java.util.*;

public class Deck{

    private Stack<Tile> deck = new Stack<>();

    //Generate a new deck object containing 104 tiles
    public Deck(){ populateDeck(); }
    public Deck(boolean empty) { if (!empty) populateDeck(); }
    private void populateDeck() {
        char colours[] = {'R', 'G', 'B', 'O'};
        int values[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

        for(int colour = 0; colour < 8; colour++){
            for(int value : values){
                deck.push(new Tile(colours[colour%4], value));
            }
        }

        System.out.println("deck length: " + this.deck.size());
        deck.push(new Joker());
        deck.push(new Joker());
    }

    //Return the top card of the deck, removing it
    public Tile pop(){
        if(!deck.empty()){
            return deck.pop();
        }
        return null;
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

    public Stack<Tile> clone() {
        Stack<Tile> copy = new Stack<Tile>();
        //while()
        System.out.println(deck.size());
        ArrayList<Tile> popped = new ArrayList<>();
        Tile t = deck.pop();
        while(!deck.empty()){
            popped.add(t);
            t = deck.pop();
        }

        for(Tile t2: popped){
          this.deck.push(t2);
          copy.push(t2);
        }

        System.out.println(copy.size());
        return copy;
    }

}
