package game.battleships;

/**
 * Created by jgong on 1/6/16.
 */
public class Destroyer extends Ship {

    public String getShipName() {
        return "Destroyer";
    }

    public Destroyer () {
        _lifepoints = 3;
        _shipLength = 3;
        _uniqueIdentifier = 4;
    }
}
