package COMP3004;

public class View extends TableObserver implements PlayerInterface {
    private Player player;

    public View() {
        player = new Player();
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setHand(Meld hand) {
        this.player.setHand(hand);
    }
}
