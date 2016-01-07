/**
 * Created by jgong on 1/6/16.
 */

import game.*;
import game.battleships.*;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class createBoardTest {

    @Test
    public void createEasyBoardSizeTest() {
        GameBoard _game = new GameBoard(Difficulty.EASY);
        _game.initializeBoardToDifficulty();
        assertEquals(_game.getGameBoard().length, 10);
        assertEquals(_game.getGameBoard()[0].length, 10);
    }

    @Test
    public void canPlaceShipsOnBoard() {
        GameBoard _game = new GameBoard(Difficulty.INTERMEDIATE);
        _game.initializeBoardToDifficulty();
        _game.assignAllShipLocations();
        int[][] _gameboard = _game.getGameBoard();

        int totalShipsCombinedSize = 12;
        int actualShipsCombinedSize = 0;

        for (int i = 0; i < _gameboard.length; i += 1) {
            for (int j = 0; j < _gameboard.length; j += 1) {
                if (_gameboard[i][j] != 0)
                    actualShipsCombinedSize += 1;
            }
        }

        assertEquals(totalShipsCombinedSize, actualShipsCombinedSize);
    }

    @Test
    public void correctNumberOfUniqueShips() {
        GameBoard _game = new GameBoard(Difficulty.HARD);
        _game.initializeBoardToDifficulty();
        _game.assignAllShipLocations();
        int[][] _gameBoard = _game.getGameBoard();

        HashSet<Integer> uniqueShips = new HashSet<>();

        for (int i = 0; i < _gameBoard.length; i += 1) {
            for (int j = 0; j < _gameBoard.length; j += 1) {
                if (_gameBoard[i][j] != 0)
                    uniqueShips.add(_gameBoard[i][j]);
            }
        }

        assertEquals(5, uniqueShips.size());
    }

    @Test
    public void seededBoardRemainsSame() {
        Random seededGenerator = new Random(5634);
        GameBoard _game = new GameBoard(Difficulty.INTERMEDIATE);
        _game.setRandomGenerator(seededGenerator);
        _game.initializeBoardToDifficulty();
        _game.assignAllShipLocations();

        Point carrierLocation = new Point(0, 7);
        Point battleshipLocation = new Point(13, 11);
        Point submarineLocation = new Point(11, 7);

        _game.resetGameBoard();
        seededGenerator = new Random(5634);
        _game.setRandomGenerator(seededGenerator);
        _game.initializeBoardToDifficulty();
        _game.assignAllShipLocations();

        assertEquals(5, _game.getShipIdentifierAtLocation(submarineLocation));
        assertEquals(1, _game.getShipIdentifierAtLocation(carrierLocation));
        assertEquals(2, _game.getShipIdentifierAtLocation(battleshipLocation));
    }

    @Test
    public void sunkAllShips() {
        GameBoard _game = new GameBoard(Difficulty.EASY);
        _game.initializeBoardToDifficulty();
        _game.assignAllShipLocations();

        assertEquals(1, _game.getFleetSize());

        ArrayList<Ship> ships = _game.getShipsToPlace();

        Ship shipPiece = ships.get(0);
        shipPiece.setLifepoints(1);
        _game.decrementShipHealth(shipPiece.getUniqueIdentifier());

        assertEquals(true, _game.isGameOver());
    }
}
