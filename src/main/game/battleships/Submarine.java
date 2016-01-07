package game.battleships;

/**
 * Created by jgong on 1/6/16.
 */
public class Submarine extends Ship {

    public String getShipName() {
        return "Submarine";
    }

    public Submarine () {
        _lifepoints = 3;
        _shipLength = 3;
        _uniqueIdentifier = 5;
    }
}
