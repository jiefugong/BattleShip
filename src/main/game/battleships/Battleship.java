package game.battleships;

/**
 * Created by jgong on 1/6/16.
 */
public class Battleship extends Ship {

    public String getShipName() {
        return "Batlleship";
    }

    public Battleship () {
        _lifepoints = 4;
        _shipLength = 4;
        _uniqueIdentifier = 2;
    }
}
