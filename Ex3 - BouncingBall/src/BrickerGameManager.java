package src;

import src.brick_strategies.BrickStrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.*;

/**
 * This class creates a new Bricker Game contanining Bricks Ball and a Paddle.
 * the player moves a PADDLE from side-to-side to hit a BALL.
 * The game’s objective is to eliminate all of the BRICKS at the top of the screen by hitting them with the BALL.
 * But, if the ball hits the bottom ENCLOSURE several times (look for the hearts - left bottom of the screen)
 * the player loses and the game ends!
 * @author tamuz
 */
public class BrickerGameManager extends  GameManager{

    //================ private constants ================

    private static final int BORDER_WIDTH = 20;
    private static final int BALL_RADIUS = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private static final int BRICK_HEIGHT = 15;
    private static final int NUM_OF_BRICKS_ROWS = 5;
    private static final int NUM_OF_BRICKS_IN_ROW = 8;
    private static final int SPACE_BETWEEN_BRICKS = 5;
    private static final int NUM_OF_LIVES = 4;
    private static final int HEART_SIZE = 15;
    private static final int TEXT_SIZE = 15;
    private static final int TEXT_X_DISTANCE = 75;
    private static final int LIVES_Y_AXIS = 30;
    private static final int DEFAULT_NUM_OF_BRICKS = 0;
    private static final String DARK_BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BALL_PATH = "assets/ball.png";
    private static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_PATH = "assets/paddle.png";
    private static final String BRICK_PATH = "assets/brick.png";
    private static final String HEARTH_PATH = "assets/heart.png";

    //================ fields ================

    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private danogl.util.Counter counter;
    private danogl.util.Counter livesCounter ;
    private Paddle paddle;

    //================ constructor ================

    /**
     * Constructor.
     * @param windowTitle windows title of the game
     * @param windowDimensions pixel dimensions for game window height x width
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
    }

    //================ public methods ================

    /**
     * initialize the game window.
     * specifically initialize objects in the game window - ball, paddle, walls, life counters, bricks.
     * This version of the game has 5 rows, 8 columns of bricks.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener an InputListener instance for reading user input.
     * @param windowController controls visual rendering of the game window and object renderables.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.counter = new danogl.util.Counter(DEFAULT_NUM_OF_BRICKS);
        this.livesCounter = new danogl.util.Counter(NUM_OF_LIVES);

        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(),
                this,
                imageReader,
                soundReader,
                inputListener,
                this.windowController,
                windowDimensions);

        /* create gameObjects */
        createBackGround(imageReader, windowController);
        createBorders(windowDimensions);
        createBall(imageReader, windowDimensions, soundReader);
        createPaddle(imageReader, windowDimensions, inputListener);
        createBricks(imageReader, windowDimensions, brickStrategyFactory);
        createGraphicLifeCounter(imageReader);
        createNumericLifeCounter();

    }


    /**
     * Code in this function is run every frame update.
     * @param deltaTime time between updates.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd(deltaTime);

    }

    public void repositionBall(danogl.GameObject ball){
        ball.setCenter(windowDimensions.mult(0.5f));
    }

    //================ private methods ================

    /**
     * creates the background of the game.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param windowController controls visual rendering of the game window and object renderables.
     */
    private void createBackGround(ImageReader imageReader, WindowController windowController) {
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage(DARK_BACKGROUND_PATH, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     *  creates the ball in the middle of the screen.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param windowDimensions dimensions of the windom.
     * @param soundReader a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @return new ball
     */
    private void createBall(ImageReader imageReader, Vector2 windowDimensions, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(BALL_PATH, true);
        Sound collisionSounds = soundReader.readSound(BALL_SOUND_PATH);
        Ball newBall = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS,BALL_RADIUS), ballImage, collisionSounds);
        repositionBall(newBall);
        gameObjects().addGameObject(newBall);
        this.ball = newBall;
    }

    /**
     *  creates a new paddle at the bottom of the screen.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param windowDimensions dimensions of the windom.
     * @param inputListener an InputListener instance for reading user input.
     */
    private void createPaddle(ImageReader imageReader, Vector2 windowDimensions,
                              UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PADDLE_PATH, true);
        Paddle newPaddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener, windowDimensions, MIN_DISTANCE_FROM_SCREEN_EDGE);
        newPaddle.setCenter(new Vector2(windowDimensions.x()/2 , windowDimensions.y() - 30));
        gameObjects().addGameObject(newPaddle);
        this.paddle =  newPaddle;
    }

    /**
     * creates left, up, right borders on the screen.
     * this way the ball cant get out of those borders.
     * @param windowDimensions dimensions of the windom.
     */
    private void createBorders(Vector2 windowDimensions) {
        Vector2[] borderAnchor = {Vector2.ZERO, new Vector2( windowDimensions.x() - BORDER_WIDTH, 0)
                ,Vector2.ZERO};
        Vector2[] heightWidth = { new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new Vector2(BORDER_WIDTH, windowDimensions.y())
                ,new Vector2(windowDimensions.x(), BORDER_WIDTH)};
        for (int i = 0; i < borderAnchor.length; i++) {

            GameObject newBorder = new Border(
                    //anchored at top-left corner of the screen
                    borderAnchor[i],

                    //height of border is the height of the screen
                    heightWidth[i],

                    //this game object is invisible; it doesn’t have a Renderable

                    null);
            gameObjects().addGameObject(newBorder);
        }
    }

    /**
     * creates all the bricks on screen.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param windowDimensions dimensions of the windom.
     * @param brickStrategyFactory
     */
    private void createBricks(ImageReader imageReader, Vector2 windowDimensions, BrickStrategyFactory brickStrategyFactory){
        int availableWindowXAxis = (int)windowDimensions.x() - 2 * (BORDER_WIDTH + SPACE_BETWEEN_BRICKS) - ((NUM_OF_BRICKS_IN_ROW + 1) * SPACE_BETWEEN_BRICKS);
        int brickWidth = availableWindowXAxis / NUM_OF_BRICKS_IN_ROW;
        int startOfFirstBrick = BORDER_WIDTH + SPACE_BETWEEN_BRICKS;
        Vector2 nextBrickLocation = new Vector2(startOfFirstBrick+ SPACE_BETWEEN_BRICKS,startOfFirstBrick);
        Vector2 brickDimension = new Vector2(brickWidth, BRICK_HEIGHT);
        Renderable brickImage = imageReader.readImage(BRICK_PATH, false);
        for (int row = 0; row < NUM_OF_BRICKS_ROWS; row++) {
            for (int col = 0; col < NUM_OF_BRICKS_IN_ROW; col++) {
                createBrick(brickImage, nextBrickLocation, brickDimension, brickStrategyFactory);
                nextBrickLocation = nextBrickLocation.add(new Vector2( brickWidth + SPACE_BETWEEN_BRICKS, 0));
            }
            nextBrickLocation = new Vector2(startOfFirstBrick + SPACE_BETWEEN_BRICKS, startOfFirstBrick + BRICK_HEIGHT*(row+1) + SPACE_BETWEEN_BRICKS * (row +1));
        }
    }

    /**
     * creates a specific brick on given coordinates on screen.
     * @param brickImage the image of the brick to create
     * @param nextBrickLocation coordinates to place the brick on screen.
     * @param brickDimension dimensions of the brick to creates.
     * @param brickStrategyFactory
     */
    private void createBrick(Renderable brickImage, Vector2 nextBrickLocation,
                             Vector2 brickDimension, BrickStrategyFactory brickStrategyFactory) {
        //
        GameObject brick = new Brick(nextBrickLocation,
                brickDimension,
                brickImage , brickStrategyFactory.getStrategy(), this.counter);

        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
        this.counter.increment();
    }

    /**
     * checks player lost or won, if so it will offer the player the chance to play again.
     * @param deltaTime time between updates. For internal use by game engine. You do not need to call this method
     *                 yourself.
     */
    private void checkForGameEnd(float deltaTime) {
        String prompt = "";
        prompt = checkIfObjectOutOfBound(prompt);
        prompt = checkIfPlayerHasWon(prompt);
        if(!prompt.isEmpty()){
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * checks if the obj hit the bottom border,
     * if its the ball - updates livesCounter and centers the ball for next round.
     * @param prompt message to prompt
     * @return updated message
     */
    private String checkIfObjectOutOfBound(String prompt) {
        for (GameObject gameObj : this.gameObjects()) {
            float objHeight = gameObj.getCenter().y();
            if (objHeight > windowDimensions.y()) { //obj out off screen
                if( gameObj instanceof Ball && !(gameObj instanceof Puck)){
                    this.paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
                    this.livesCounter.decrement();
                    if (this.livesCounter.value() == 0) {
                        prompt = "You lose,";
                    } else {
                        this.ball.setCenter(windowDimensions.mult(0.5f));
                    }
                    continue;
                }
                gameObjects().removeGameObject(gameObj);
            }
        }
        return prompt;
    }

    /**
     * checks if player has won, meaning no bricks are left on screen.
     * @param prompt message to prompt
     * @return updated message
     */
    private String checkIfPlayerHasWon(String prompt) {
        if(this.counter.value() == 0){
            prompt = "You win,";
        }
        return prompt;
    }

    /**
     * Creates NumericLifeCounter object for displaying liveCounter on screen.
     */
    private void createNumericLifeCounter() {
        Vector2 textDimension = new Vector2(TEXT_SIZE, TEXT_SIZE);
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(this.livesCounter,
                new Vector2(windowDimensions.x() - BORDER_WIDTH - TEXT_X_DISTANCE,
                        windowDimensions.y() - LIVES_Y_AXIS), textDimension,
                gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /**
     * Creates a GraphicLifeCounter for displaying liveCounter on screen in the shape of hearts.
     * each heart symbolizes each life.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     */
    private void createGraphicLifeCounter(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage(HEARTH_PATH, true);
        Vector2 heartDimension = new Vector2(HEART_SIZE, HEART_SIZE);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(new Vector2(BORDER_WIDTH + SPACE_BETWEEN_BRICKS, windowDimensions.y() - LIVES_Y_AXIS), heartDimension, this.livesCounter,
                heartImage, gameObjects(), NUM_OF_LIVES);
        gameObjects().addGameObject(graphicLifeCounter,  Layer.BACKGROUND);
    }

    //================ MAIN ================
    public static void main(String[] args) {
        new BrickerGameManager("Bricker Game", new Vector2(700, 500)).run();
    }
}
