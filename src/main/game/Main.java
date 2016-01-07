package game;

import java.lang.*;

public class Main {

    public static void main (String[] args) {
        Game game = new Game(true);
        game.setUpGame(Difficulty.EASY);
        game.play();
    }
}
