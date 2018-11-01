package COMP3004.oberver_pattern;

import COMP3004.artificial_intelligence.ArtificialIntelligence;
import COMP3004.artificial_intelligence.Strategy3;
import COMP3004.controllers.GameInteractionController;

public interface MultiSubjectInterface {
    public void registerTableObserver(ArtificialIntelligence t);
    public void removeTableObserver(ArtificialIntelligence t);
    public void registerTableObserver(GameInteractionController t);
    public void removeTableObserver(GameInteractionController t);
    public void registerPlayerHandObserver(Strategy3 p);
    public void removePlayerHandObserver(Strategy3 p);
    public void notifyObservers();
}
