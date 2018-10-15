package COMP3004;

public class View extends TableObserver implements PlayerInterface {
    public boolean playing = true;
    public View(){
        super();
    }

    public void drawTile() {
    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile) {
        if(inMeld.getTiles().contains(tile)){
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }

    public Table play(){
        return null;
    }

    public void setHand(Meld hand) {
        this.hand = hand;
    }

}
