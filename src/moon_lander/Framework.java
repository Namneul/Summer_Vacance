package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Framework that controls the game (Game.java) that created it, update it and draw it on the screen.
 *
 * @author www.gametutorial.net
 */

public class Framework extends Canvas {

    /**
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;

    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;

    /**
     * FPS - Frames per second
     * How many times per second the game should update?
     */
    private final int GAME_FPS = 16;
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;

    /**
     * Possible states of the game
     */
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED, CUSTOMIZE_MENU,LOADING_BEFORE_START}
    /**
     * Current state of the game
     */
    public static GameState gameState;

    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;

    // The actual game
    private Game game;


    /**
     * Image for menu.
     */
    private BufferedImage moonLanderMenuImg;
    private BufferedImage customBackImg;
    private BufferedImage rocketImg, rocket_pinkImg, rocket_blueImg, rocket_yellowImg;

    public Framework ()
    {
        super();

        gameState = GameState.VISUALIZING;

        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }

    static int ctm = 0;


    /**
     * Set variables and objects.
     * This method is intended to set the variables and objects for this class, variables and objects for the actual game can be set in Game.java.
     */
    private void Initialize()
    {

    }

    /**
     * Load files - images, sounds, ...
     * This method is intended to load files for this class, files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {
        try
        {
            URL rocketImgUrl = this.getClass().getResource("/resources/images/rocket.png");
            rocketImg = ImageIO.read(rocketImgUrl);

            URL rocket_pinkImgUrl = this.getClass().getResource("/resources/images/rocket_pink.png");
            rocket_pinkImg = ImageIO.read(rocket_pinkImgUrl);

            URL rocket_blueImgUrl = this.getClass().getResource("/resources/images/rocket_blue.png");
            rocket_blueImg = ImageIO.read(rocket_blueImgUrl);

            URL rocket_yellowImgUrl = this.getClass().getResource("/resources/images/rocket_yellow.png");
            rocket_yellowImg = ImageIO.read(rocket_yellowImgUrl);

            URL customBackImgUrl = this.getClass().getResource("/resources/images/custom_back.jpg");
            customBackImg = ImageIO.read(customBackImgUrl);

            URL moonLanderMenuImgUrl = this.getClass().getResource("/resources/images/menu.jpg");
            moonLanderMenuImg = ImageIO.read(moonLanderMenuImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();

        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;

        while(true)
        {
            beginTime = System.nanoTime();

            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;

                    game.UpdateGame(gameTime, mousePosition());

                    lastTime = System.nanoTime();
                    break;
                case GAMEOVER:
                    //...
                    break;
                case MAIN_MENU:
                    //...
                    break;
                case OPTIONS:
                    //...
                    break;
                case GAME_CONTENT_LOADING:
                    //...
                    break;
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();

                    gameState = GameState.MAIN_MENU;
                    // When all things that are called above finished, we change game status to main menu.
                    break;
                case VISUALIZING:
                    // On Ubuntu OS (when I tested on my old computer) this.getWidth() method doesn't return the correct value immediately (eg. for frame that should be 800px width, returns 0 than 790 and at last 798px).
                    // So we wait one second for the window/frame to be set to its correct size. Just in case we
                    // also insert 'this.getWidth() > 1' condition in case when the window/frame size wasn't set in time,
                    // so that we although get approximately size.
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();

                        // When we get size of frame we change status.
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                    break;
            }

            // Repaint the screen.
            repaint();

            // Here we calculate the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10)
                timeLeft = 10; //set a minimum
            try {
                //Provides the necessary delay and also yields control so that other thread can do work.
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }


    /**
     * Draw the game to the screen. It is called through repaint() method in GameLoop() method.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState) {
            case PLAYING:
                game.Draw(g2d, mousePosition());
                break;
            case GAMEOVER:
                game.DrawGameOver(g2d, mousePosition(), gameTime);
                break;
            case MAIN_MENU:
                g2d.drawImage(moonLanderMenuImg, 0, 0, frameWidth, frameHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("Welcome to Moon Lander!.", frameWidth / 2 - 85, frameHeight / 2);
                g2d.drawString("Press any key to choose your rocket.", frameWidth / 2 - 115, frameHeight / 2 + 30);
                g2d.drawString("WWW.GAMETUTORIAL.NET", 7, frameHeight - 5);
                break;
            case OPTIONS:
                //...
                break;
            case GAME_CONTENT_LOADING:
                g2d.setColor(Color.white);
                g2d.drawString("GAME is LOADING", frameWidth / 2 - 50, frameHeight / 2);
                break;
            case LOADING_BEFORE_START:
                g2d.setColor(Color.white);
                g2d.drawString("Are you READY?", frameWidth / 2 - 50, frameHeight / 2);
                g2d.drawString("Press any key to START!", frameWidth / 2 - 10, frameHeight / 2);

                break;
            case CUSTOMIZE_MENU:
                g2d.drawImage(customBackImg, 0, 0, frameWidth, frameHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("Choose your Rocket to land!", frameWidth / 2 - 85, frameHeight / 2 - 155);
                g2d.drawString("Press Enter to choose.", frameWidth / 2 - 70, frameHeight / 2 - 130);
                while (true) {
                    if (Canvas.keyboardKeyState(KeyEvent.VK_LEFT)) {
                        if (ctm == 0) {
                            ctm = 3;
                        } else {
                            ctm -= 1;
                        }

                    } else if (Canvas.keyboardKeyState(KeyEvent.VK_RIGHT)) {
                        if (ctm == 3) {
                            ctm = 0;
                        } else {
                            ctm += 1;
                        }
                    } else if (Canvas.keyboardKeyState(KeyEvent.VK_ENTER)) {
                        switch (ctm){

                        }
                        gameState = GameState.LOADING_BEFORE_START;
                        break;
                    }
                    switch (ctm) {
                        case 0:
                            g2d.drawImage(rocketImg, frameWidth / 2 - 50, frameHeight / 2 -70, rocketImg.getWidth()+20, rocketImg.getHeight()+20, null);
                            break;
                        case 1:
                            g2d.drawImage(rocket_yellowImg, frameWidth / 2- 50, frameHeight / 2-70, rocket_yellowImg.getWidth()+20, rocket_yellowImg.getHeight()+20, null);
                            break;
                        case 2:
                            g2d.drawImage(rocket_pinkImg, frameWidth / 2- 50, frameHeight / 2-70, rocket_pinkImg.getWidth()+20, rocket_pinkImg.getHeight()+20, null);
                            break;
                        case 3:
                            g2d.drawImage(rocket_blueImg, frameWidth / 2- 50, frameHeight / 2-70, rocket_blueImg.getWidth()+20, rocket_blueImg.getHeight()+20, null);
                            break;
                    }
                    break;
                }
        }
    }
    /**
     * Starts new game.
     */
    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        game = new Game();
    }

    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        game.RestartGame();

        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }

    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     *
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();

            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }

    /**
     * This method is called when keyboard key is released.
     *
     * @param e KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        switch (gameState)
        {
            case MAIN_MENU:
                gameState = GameState.CUSTOMIZE_MENU;
                break;
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame();
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    gameState = GameState.CUSTOMIZE_MENU;
                }
                break;
            case LOADING_BEFORE_START:
                newGame();

        }
    }

    /**
     * This method is called when mouse button is clicked.
     *
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {

    }
}
