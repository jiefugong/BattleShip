package game; /**
 * Created by jgong on 1/5/16.
 */

import java.util.*;
import game.battleships.*;

public enum Difficulty {

    EASY(10) {
        public ArrayList<Ship> shipsToPlace() {
            ArrayList<Ship> _shipsToPlace = new ArrayList<>();
            _shipsToPlace.add(new Carrier());
            return _shipsToPlace;
        }
    }, INTERMEDIATE(15) {
        public ArrayList<Ship> shipsToPlace() {
            ArrayList<Ship> _shipsToPlace = new ArrayList<>();
            _shipsToPlace.add(new Carrier());
            _shipsToPlace.add(new Battleship());
            _shipsToPlace.add(new Submarine());
            return _shipsToPlace;
        }
    }, HARD(20) {
        public ArrayList<Ship> shipsToPlace() {
            ArrayList<Ship> _shipsToPlace = new ArrayList<>();
            _shipsToPlace.add(new Carrier());
            _shipsToPlace.add(new Battleship());
            _shipsToPlace.add(new Submarine());
            _shipsToPlace.add(new Cruiser());
            _shipsToPlace.add(new Destroyer());
            return _shipsToPlace;
        }
    };

    private final int _boardSize;
    public abstract ArrayList<Ship> shipsToPlace();

    Difficulty (int boardSize) {
        this._boardSize = boardSize;
    }

    public int getBoardSize() {
        return _boardSize;
    }
}
