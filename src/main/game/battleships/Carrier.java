package game.battleships;

/**
 * Created by jgong on 1/6/16.
 */
public class Carrier extends Ship {

    public String getShipName() {
        return "Carrier";
    }

    public Carrier () {
        _lifepoints = 5;
        _shipLength = 5;
        _uniqueIdentifier = 1;
    }
}
