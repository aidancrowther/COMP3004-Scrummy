package COMP3004.player_factory_pattern;

import COMP3004.models.Player;

public class PlayerFactory extends AbstractPlayerFactory {
    @Override
    public Player FactoryMethod(int type) {
        switch (type) {
            case 1: return new PlayerFirstAI();
            case 2: return new PlayerSecondAI();
            case 3: return new PlayerThirdAI();
            case 4: return new PlayerFourthAI();
            default: return null;
        }
    }
}
