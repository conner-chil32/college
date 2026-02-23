package fgc;

import tage.*;

public class runGame extends VariableFrameRateGame {

    private static Engine engine;

    public runGame() { super(); }

    public static void main(String[] args)
    {	runGame game = new runGame();
        engine = new Engine(game);
        engine.initializeSystem();
        game.buildGame();
        game.startGame();
    }

    @Override
    public void loadShapes() {

    }

    @Override
    public void loadTextures() {

    }

    @Override
    public void buildObjects() {

    }

    @Override
    public void initializeLights() {

    }

    @Override
    public void initializeGame() {

    }

    @Override
    public void update() {

    }

    @Override
    public void shutdown() {
        super.shutdown();
    }
}
