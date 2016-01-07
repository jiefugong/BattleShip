package game;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Game {

    Game (boolean verbose) {
        _input = new Scanner(System.in);
        _out = new PrintWriter(new OutputStreamWriter(System.out), true);
        _verbose = verbose;
        _manuallyGenerateShipLocations = false;
        _gameOver = false;
    }

    void play() {
        _out.println("Welcome to game.Battleship v1.0!");
        printOutVisibleBoard();

        while (!_gameOver) {
            Point currentGuess = promptForCoordinate();
            if (_gameBoard.makeGuessOnBoard(currentGuess)) {
                _boardVisibleToPlayer[currentGuess.getXCoordinate()][currentGuess.getYCoordinate()] = HIT;
                _out.println("Hit!");
            } else {
                _boardVisibleToPlayer[currentGuess.getXCoordinate()][currentGuess.getYCoordinate()] = MISS;
                _out.println("Miss!");
            }
            _out.flush();

            if (_verbose)
                printOutVisibleBoard();

            _gameOver = _gameBoard.isGameOver();
        }

        _out.printf("You won! You took %d tries to sink all the battleships on %s!",
                _gameBoard.getNumberOfGuesses(), _difficulty);

        _out.close();
    }

    void printOutVisibleBoard() {
        //TODO: Fix this
        System.out.print("  ");

        for (int rowNumber = 0; rowNumber < _boardSize; rowNumber += 1)
            System.out.printf("%d ", rowNumber);

        System.out.println();

        for (int xcoord = 0; xcoord < _boardVisibleToPlayer.length; xcoord += 1) {
            System.out.printf("%s ", xcoord);
            for (int ycoord = 0; ycoord < _boardVisibleToPlayer.length; ycoord += 1) {
                System.out.printf("%s ", _boardVisibleToPlayer[xcoord][ycoord]);
            }
            System.out.println();
        }
    }

    void setUpGame (Difficulty difficulty) {
        _difficulty = difficulty;
        _boardSize = difficulty.getBoardSize();
        _gameBoard = new GameBoard(difficulty);
        _gameBoard.initializeBoardToDifficulty();
        _gameBoard.assignAllShipLocations();

        _boardVisibleToPlayer = new String[_boardSize][_boardSize];
        for (int i = 0; i < _boardSize; i += 1) {
            for (int j = 0; j < _boardSize; j += 1) {
                _boardVisibleToPlayer[i][j] = HIDDEN;
            }
        }
    }

    boolean checkVisibleBoardForGuesses(Point coordinate) {
        return _boardVisibleToPlayer[coordinate.getXCoordinate()][coordinate.getYCoordinate()] != "-";
    }

    Point promptForCoordinate() {
        _out.println("Please enter two integers separated by a space to guess a spot on the battlefield.");
        Point coordinate = new Point(_input.nextInt(), _input.nextInt());
        if (checkVisibleBoardForGuesses(coordinate)) {
            _out.println("You have already guessed that location! Please guess again!");
            _out.flush();
            return promptForCoordinate();
        }
        return coordinate;
    }

    private String[][] _boardVisibleToPlayer;
    private int _boardSize;
    private boolean _manuallyGenerateShipLocations;
    private boolean _verbose;
    private boolean _gameOver;
    private Difficulty _difficulty;
    private GameBoard _gameBoard;

    private final Scanner _input;
    private final PrintWriter _out;
    private final String HIT = "X";
    private final String MISS = "O";
    private final String HIDDEN = "-";

}
