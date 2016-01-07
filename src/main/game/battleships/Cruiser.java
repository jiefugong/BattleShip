package game.battleships;

/**
 * Created by jgong on 1/6/16.
 */
public class Cruiser extends Ship {

    public String getShipName() {
        return "Cruiser";
    }

    public Cruiser () {
        _lifepoints = 2;
        _shipLength = 2;
        _uniqueIdentifier = 3;
    }
}
