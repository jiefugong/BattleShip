package game.battleships;

/**
 * Created by jgong on 1/6/16.
 */
public abstract class Ship {

    Ship () {
        _lifepoints = -1;
        _shipLength = -1;
        _uniqueIdentifier = -1;
        _isAfloat = true;
    }

    public int getLifepoints() {
        return _lifepoints;
    }

    public int getShipLength() { return _shipLength; }

    public int getUniqueIdentifier() { return _uniqueIdentifier; }

    public void lowerHealth() {
        _lifepoints -= 1;
        if (_lifepoints == 0)
            _isAfloat = false;
    }

    public void setLifepoints(int lifepoints) {
        assert lifepoints > 0;
        _lifepoints = lifepoints;
    }

    public boolean isAfloat() { return _isAfloat; }

    public abstract String getShipName();

    int _lifepoints;
    int _shipLength;
    int _uniqueIdentifier;
    boolean _isAfloat;

}
