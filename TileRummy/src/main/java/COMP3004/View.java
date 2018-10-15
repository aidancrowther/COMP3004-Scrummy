package COMP3004;

public class View extends TableObserver implements PlayerInterface {
    public Meld hand;

    public View(){
        this.hand = new Meld();
        this.hand .add(new Tile('R', 1));
        this.hand .add(new Tile('G', 1));
        this.hand .add(new Tile('B', 1));
        this.hand .add(new Tile('O', 1));
    }

    public boolean playing = true;

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile) {
        if (inMeld.getTiles().contains(tile)) {
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }

    public Table play() {
        return null;
    }

    public Table play(String message) {
        return null;
    }

    public Meld getHand() { return this.hand; }

    public void setHand(Meld hand) {
        this.hand = hand;
    }
}
