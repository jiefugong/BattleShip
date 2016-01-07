package game;

import java.util.*;
import java.lang.*;
import game.battleships.*;

public class GameBoard {

    public GameBoard(Difficulty difficulty) {
        _difficulty = difficulty;
        _gameOver = false;
        _numberOfGuesses = 0;
        _shipsToPlace = difficulty.shipsToPlace();
        _boardSize = difficulty.getBoardSize();
        _fleetSize = _shipsToPlace.size();
        _randomGenerator = new Random();
    }

    public void initializeBoardToDifficulty() {
        _board = new int[_boardSize][_boardSize];
    }

    public void assignAllShipLocations() {
        for (Ship ship : _shipsToPlace) {
            boolean placedShipSuccessfully = false;
            int lengthOfShip = ship.getShipLength();
            int shipIdentifier = ship.getUniqueIdentifier();

            while (!placedShipSuccessfully) {
                Point initialShipCoordinate =
                        new Point(_randomGenerator.nextInt(_boardSize), _randomGenerator.nextInt(_boardSize));
                String direction = DIRECTIONS[_randomGenerator.nextInt(NUMBER_OF_DIRECTIONS)];
                placedShipSuccessfully =
                        placeShipOnBoard(lengthOfShip, initialShipCoordinate, direction, shipIdentifier);
            }
        }
    }

    public void assignAllShipLocationsManually() {
        for (Ship ship : _shipsToPlace) {
            manuallyPlaceShipsOnBoard(ship);
        }
    }

    public void manuallyPlaceShipsOnBoard(Ship ship) {
        System.out.printf("Please place your %s. Enter two integers separated by space.\n", ship.getShipName());
        Point initialCoordinate = new Point(INPUT.nextInt(), INPUT.nextInt());
        System.out.printf("Please select your second coordinate %d units away.\n", ship.getShipLength());
        Point endCoordinate = new Point(INPUT.nextInt(), INPUT.nextInt());

        if (canPlaceShip(initialCoordinate, endCoordinate, ship)) {
            String direction = determineDirectionFromCoordinates(initialCoordinate, endCoordinate);
            placeShipOnBoard(ship.getShipLength(), initialCoordinate, direction, ship.getUniqueIdentifier());
        } else {
            System.out.println("You have selected invalid coordinates. Please try again!");
            manuallyPlaceShipsOnBoard(ship);
        }
    }

    public String determineDirectionFromCoordinates(Point initialCoordinate, Point endCoordinate) {
        if (initialCoordinate.getXCoordinate() == endCoordinate.getXCoordinate()) {
            return initialCoordinate.getYCoordinate() > endCoordinate.getYCoordinate() ? "West" : "East";
        } else {
            return initialCoordinate.getXCoordinate() > endCoordinate.getXCoordinate() ? "North" : "South";
        }
    }

    public boolean placeShipOnBoard(int lengthOfShip, Point initialShipCoordinate, String direction, int shipIdentifier) {
        int initialXCoordinate = initialShipCoordinate.getXCoordinate();
        int initialYCoordinate = initialShipCoordinate.getYCoordinate();

        if (canPlaceShip(lengthOfShip, initialShipCoordinate, direction)) {
            for (int i = 0; i < lengthOfShip; i += 1) {
                switch (direction) {
                    case "North":
                        _board[initialXCoordinate][initialYCoordinate + i] = shipIdentifier;
                        break;
                    case "South":
                        _board[initialXCoordinate][initialYCoordinate - i] = shipIdentifier;
                        break;
                    case "West":
                        _board[initialXCoordinate - i][initialYCoordinate] = shipIdentifier;
                        break;
                    case "East":
                        _board[initialXCoordinate + i][initialYCoordinate] = shipIdentifier;
                        break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean canPlaceShip(Point initialShipCoordinate, Point endShipCoordinate, Ship ship) {
        try {
            if (initialShipCoordinate.getXCoordinate() != endShipCoordinate.getXCoordinate()
                    && initialShipCoordinate.getYCoordinate() != endShipCoordinate.getYCoordinate()) {
                return false; // Ships cannot be placed in a diagonal angle
            } else if (getShipIdentifierAtLocation(initialShipCoordinate) != EMPTY) {
                return false;
            } else if ((int) distanceBetweenTwoPoints(initialShipCoordinate,
                    endShipCoordinate) != ship.getShipLength()) {
                return false;
            } else if (!canPlaceShip(ship.getShipLength(), initialShipCoordinate,
                    determineDirectionFromCoordinates(initialShipCoordinate, endShipCoordinate))) {
                return false;
            } else {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public double distanceBetweenTwoPoints(Point initialCoordinate, Point endCoordinate) {
        return Math.sqrt(Math.pow(initialCoordinate.getXCoordinate() - endCoordinate.getXCoordinate(), 2)
            + Math.pow(initialCoordinate.getYCoordinate() - endCoordinate.getYCoordinate(), 2));
    }

    public boolean canPlaceShip(int lengthOfShip, Point initialShipCoordinate, String direction) {
        int initialXCoordinate = initialShipCoordinate.getXCoordinate();
        int initialYCoordinate = initialShipCoordinate.getYCoordinate();

        if (getShipIdentifierAtLocation(initialShipCoordinate) != EMPTY)
            return false;

        for (int i = 0; i < lengthOfShip; i += 1) {
            try {
                switch (direction) {
                    case "North":
                        if (_board[initialXCoordinate][initialYCoordinate + i] != EMPTY)
                            return false;
                        break;
                    case "South":
                        if (_board[initialXCoordinate][initialYCoordinate - i] != EMPTY)
                            return false;
                        break;
                    case "West":
                        if (_board[initialXCoordinate - i][initialYCoordinate] != EMPTY)
                            return false;
                        break;
                    case "East":
                        if (_board[initialXCoordinate + i][initialYCoordinate] != EMPTY)
                            return false;
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }
        return true;
    }

    public boolean makeGuessOnBoard(Point target) {
        int xcoord = target.getXCoordinate();
        int ycoord = target.getYCoordinate();

        if (_board[xcoord][ycoord] != EMPTY) {
            decrementShipHealth(_board[xcoord][ycoord]);
            _board[xcoord][ycoord] = EMPTY;
            _numberOfGuesses += 1;
            return true;
        } else {
            return false;
        }
    }

    public void decrementShipHealth(int identifierOfHitShip) {
        //TODO: I don't really like how the logic for this works at the moment
        for (Ship shipPiece : _shipsToPlace) {
            if (shipPiece.getUniqueIdentifier() == identifierOfHitShip) {
                shipPiece.lowerHealth();
                System.out.println(shipPiece.getLifepoints());
                if (!shipPiece.isAfloat()) {
                    _fleetSize -= 1;
                }
            }
        }
    }

    public boolean isGameOver() { return _fleetSize == 0; }


    public void resetGameBoard() {
        _numberOfGuesses = 0;
        _fleetSize = _shipsToPlace.size();
        _gameOver = false;
        initializeBoardToDifficulty();
    }

    public void resetGameBoard(Difficulty difficulty) {
        _numberOfGuesses = 0;
        _boardSize = difficulty.getBoardSize();
        _shipsToPlace = difficulty.shipsToPlace();
        _fleetSize = _shipsToPlace.size();
        _gameOver = false;
        _difficulty = difficulty;
        initializeBoardToDifficulty();
    }

    public void printOutBoard() {
        System.out.print("  ");

        for (int rowNumber = 0; rowNumber < _boardSize; rowNumber += 1)
            System.out.printf("%d ", rowNumber);

        System.out.println();

        for (int xcoord = 0; xcoord < _board.length; xcoord += 1) {
            System.out.printf("%d ", xcoord);
            for (int ycoord = 0; ycoord < _board.length; ycoord += 1) {
                System.out.printf("%d ", _board[xcoord][ycoord]);
            }
            System.out.println();
        }
    }

    public void setRandomGenerator(Random generator) {
        _randomGenerator = generator;
    }

    public int getNumberOfGuesses() { return _numberOfGuesses; }

    public int getShipIdentifierAtLocation(Point coordinate) { return _board[coordinate.getXCoordinate()][coordinate.getYCoordinate()]; }

    public int getFleetSize() { return _fleetSize; }

    public int[][] getGameBoard() { return _board; }

    public ArrayList<Ship> getShipsToPlace() { return _shipsToPlace; }

    private int _numberOfGuesses;
    private int _boardSize;
    private int _fleetSize;
    private int[][] _board;
    private boolean _gameOver;
    private ArrayList<Ship> _shipsToPlace;
    private Random _randomGenerator;
    private Difficulty _difficulty;

    private final int EMPTY = 0;
    private final int RESERVED = 1;
    private final int NUMBER_OF_DIRECTIONS = 4;
    private final Scanner INPUT = new Scanner(System.in);
    private final String[] DIRECTIONS = new String[]{"North", "South", "East", "West"};
}
