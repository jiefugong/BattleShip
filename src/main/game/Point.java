package game;

/**
 * Created by jgong on 1/5/16.
 */
public class Point {

    private int _xcoord;
    private int _ycoord;

    public Point (int xcoord, int ycoord) {
        _xcoord = xcoord;
        _ycoord = ycoord;
    }

    public void setXCoordinate (int xcoord) {
        _xcoord = xcoord;
    }

    public void setYCoordinate (int ycoord) {
        _ycoord = ycoord;
    }

    public int getXCoordinate () {
        return _xcoord;
    }

    public int getYCoordinate () {
        return _ycoord;
    }
}
