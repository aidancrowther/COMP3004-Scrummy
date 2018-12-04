package COMP3004.player_factory_pattern;

import COMP3004.models.Player;

public abstract class AbstractPlayerFactory {
    public abstract Player FactoryMethod(int type);
}
