package moon_lander;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The space rocket with which player will have to land.
 * 
 * @author www.gametutorial.net
 */

public class PlayerRocket {
    
    /**
     * We use this to generate a random number for starting x coordinate of the rocket.
     */
    private Random random;
 
    /**
     * X coordinate of the rocket.
     */
    public int x;
    /**
     * Y coordinate of the rocket.
     */
    public int y;

    public int mousex, mousey;

    /**
     * Is rocket landed?
     */
    public boolean landed;
    
    /**
     * Has rocket crashed?
     */
    public boolean crashed;
        
    /**
     * Accelerating speed of the rocket.
     */
    private int speedAccelerating;
    /**
     * Stopping/Falling speed of the rocket. Falling speed because, the gravity pulls the rocket down to the moon.
     */
    private int speedStopping;
    
    /**
     * Maximum speed that rocket can have without having a crash when landing.
     */
    public int topLandingSpeed;
    
    /**
     * How fast and to which direction rocket is moving on x coordinate?
     */
    private int speedX;
    /**
     * How fast and to which direction rocket is moving on y coordinate?
     */
    public int speedY;
            
    /**
     * Image of the rocket in air.
     */
    private BufferedImage rocketImg;
    /**
     * Image of the rocket when landed.
     */
    private BufferedImage rocketLandedImg;
    /**
     * Image of the rocket when crashed.
     */
    private BufferedImage rocketCrashedImg;
    /**
     * Image of the rocket fire.
     */
    private BufferedImage rocketFireImg;
    
    /**
     * Width of rocket.
     */
    public int rocketImgWidth;
    /**
     * Height of rocket.
     */
    public int rocketImgHeight;
    
    
    public PlayerRocket()
    {
        Initialize();
        LoadContent();
        
        // Now that we have rocketImgWidth we set starting x coordinate.
        x = random.nextInt(Framework.frameWidth - rocketImgWidth);
    }
    
    
    private void Initialize()
    {
        random = new Random();
        
        ResetPlayer();
        
        speedAccelerating = 2;
        speedStopping = 1;
        
        topLandingSpeed = 5;
    }
    
    private void LoadContent()
    {
        try
        {
            switch (Framework.ctm){
                case 0:
                    URL rocketImgUrl = this.getClass().getResource("resources/images/rocket.png");
                    rocketImg = ImageIO.read(rocketImgUrl);
                    rocketImgWidth = rocketImg.getWidth();
                    rocketImgHeight = rocketImg.getHeight();

                    URL rocketLandedImgUrl = this.getClass().getResource("resources/images/rocket_landed.png");
                    rocketLandedImg = ImageIO.read(rocketLandedImgUrl);

                    URL rocketCrashedImgUrl = this.getClass().getResource("resources/images/rocket_crashed.png");
                    rocketCrashedImg = ImageIO.read(rocketCrashedImgUrl);
                    break;
                case 1:
                    URL rocketYellowImgUrl = this.getClass().getResource("resources/images/rocket_yellow.png");
                    rocketImg = ImageIO.read(rocketYellowImgUrl);
                    rocketImgWidth = rocketImg.getWidth();
                    rocketImgHeight = rocketImg.getHeight();

                    URL rocketYellowLandedImgUrl = this.getClass().getResource("resources/images/rocket_yellow_landed.png");
                    rocketLandedImg = ImageIO.read(rocketYellowLandedImgUrl);

                    URL rocketYellowCrashedImgUrl = this.getClass().getResource("resources/images/rocket_yellow_crashed.png");
                    rocketCrashedImg = ImageIO.read(rocketYellowCrashedImgUrl);
                    break;
                case 2:
                    URL rocketPinkImgUrl = this.getClass().getResource("resources/images/rocket_pink.png");
                    rocketImg = ImageIO.read(rocketPinkImgUrl);
                    rocketImgWidth = rocketImg.getWidth();
                    rocketImgHeight = rocketImg.getHeight();

                    URL rocketPinkLandedImgUrl = this.getClass().getResource("resources/images/rocket_pink_landed.png");
                    rocketLandedImg = ImageIO.read(rocketPinkLandedImgUrl);

                    URL rocketPinkCrashedImgUrl = this.getClass().getResource("resources/images/rocket_pink_crashed.png");
                    rocketCrashedImg = ImageIO.read(rocketPinkCrashedImgUrl);
                    break;
                case 3:
                    URL rocketBlueImgUrl = this.getClass().getResource("resources/images/rocket_blue.png");
                    rocketImg = ImageIO.read(rocketBlueImgUrl);
                    rocketImgWidth = rocketImg.getWidth();
                    rocketImgHeight = rocketImg.getHeight();

                    URL rocketBlueLandedImgUrl = this.getClass().getResource("resources/images/rocket_blue_landed.png");
                    rocketLandedImg = ImageIO.read(rocketBlueLandedImgUrl);

                    URL rocketBlueCrashedImgUrl = this.getClass().getResource("resources/images/rocket_blue_crashed.png");
                    rocketCrashedImg = ImageIO.read(rocketBlueCrashedImgUrl);
                    break;
            }
            
            URL rocketFireImgUrl = this.getClass().getResource("resources/images/rocket_fire.png");
            rocketFireImg = ImageIO.read(rocketFireImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetPlayer()
    {
        landed = false;
        crashed = false;
        
        x = random.nextInt(Framework.frameWidth - rocketImgWidth);
        y = 10;
        
        speedX = 0;
        speedY = 0;
    }
    
    
    /**
     * Here we move the rocket.
     */
    public void Update()
    {

        if(Canvas.mouseButtonState(MouseEvent.BUTTON1)){
            PointerInfo pt = MouseInfo.getPointerInfo();
            mousex=pt.getLocation().x-353;
            mousey=pt.getLocation().y-171;

                speedX = (int)((mousex - x) * 0.1);
                speedY = (int)((mousey - y) * 0.1);

            x += speedX;
            y += speedY;
        };
        // Calculating speed for moving up or down.
        if(Canvas.keyboardKeyState(KeyEvent.VK_W))
            speedY -= speedAccelerating;
        else
            speedY += speedStopping;
        
        // Calculating speed for moving or stopping to the left.
        if(Canvas.keyboardKeyState(KeyEvent.VK_A))
            speedX -= speedAccelerating;
        else if(speedX < 0)
            speedX += speedStopping;
        
        // Calculating speed for moving or stopping to the right.
        if(Canvas.keyboardKeyState(KeyEvent.VK_D))
            speedX += speedAccelerating;
        else if(speedX > 0)
            speedX -= speedStopping;
        
        // Moves the rocket.
        x += speedX;
        y += speedY;
    }
    
    public void Draw(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        g2d.drawString("Rocket coordinates: " + x + " : " + y, 5, 15);
        
        // If the rocket is landed.
        if(landed)
        {
            g2d.drawImage(rocketLandedImg, x, y, null);
        }
        // If the rocket is crashed.
        else if(crashed)
        {
            g2d.drawImage(rocketCrashedImg, x, y + rocketImgHeight - rocketCrashedImg.getHeight(), null);
        }
        // If the rocket is still in the space.
        else
        {
            // If player hold down a W key we draw rocket fire.
            if(Canvas.keyboardKeyState(KeyEvent.VK_W))
                g2d.drawImage(rocketFireImg, x + 12, y + 66, null);
            g2d.drawImage(rocketImg, x, y, null);
        }
    }
    
}
