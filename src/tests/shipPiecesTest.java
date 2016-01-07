/**
 * Created by jgong on 1/6/16.
 */

import game.*;
import game.battleships.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class shipPiecesTest {

    @Test
    public void verifyAllShipAttributes() {
        Carrier carrier = new Carrier();
        assertEquals(5, carrier.getLifepoints());
        assertEquals(5, carrier.getShipLength());

        Battleship battleship = new Battleship();
        assertEquals(4, battleship.getLifepoints());
        assertEquals(4, battleship.getShipLength());

        Submarine submarine = new Submarine();
        assertEquals(3, submarine.getLifepoints());
        assertEquals(3, submarine.getShipLength());

        Destroyer destroyer = new Destroyer();
        assertEquals(3, destroyer.getLifepoints());
        assertEquals(3, destroyer.getShipLength());

        Cruiser cruiser = new Cruiser();
        assertEquals(2, cruiser.getLifepoints());
        assertEquals(2, cruiser.getShipLength());
    }

    @Test
    public void hitShipHealthTest() {
        Carrier carrier = new Carrier();
        assertEquals(5, carrier.getLifepoints());
        carrier.lowerHealth();
        assertEquals(4, carrier.getLifepoints());
        assertEquals(true, carrier.isAfloat());

        carrier.setLifepoints(1);
        assertEquals(1, carrier.getLifepoints());
        carrier.lowerHealth();
        assertEquals(0, carrier.getLifepoints());
        assertEquals(false, carrier.isAfloat());

    }
}
