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
 * The obstacle that rocket should avoid,
 *
 */

public class Obstacle {

    /**
     * We use this to generate a random number for starting x coordinate of the rocket.
     */
    private Random random;

    private BufferedImage laserImg;

    /**
     * X coordinate of the rocket.
     */
    public int x;
    /**
     * Y coordinate of the rocket.
     */
    public int y;

    public boolean hit;

    public int laserImgWidth;
    /**
     * Height of rocket.
     */
    public int laserImgHeight;

    public void laser_beam(){

    }

    public Obstacle()
    {
        Initialize();
        LoadContent();

        // Now that we have rocketImgWidth we set starting x coordinate.
    }


    private void Initialize()
    {
        Resetlaser();
    }
    public void Resetlaser(){
        random = new Random();
        x =Framework.frameWidth/2;
        y = random.nextInt(Framework.frameHeight - laserImgHeight*2);
    }

    private void LoadContent()
    {
        try
        {
                    URL laserImgUrl = this.getClass().getResource("resources/images/laser.png");
                    laserImg = ImageIO.read(laserImgUrl);
                    laserImgWidth = Framework.frameWidth;
                    laserImgHeight = laserImg.getHeight();
            }

        catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Draw(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        g2d.drawImage(laserImg,x,y,null);

    }

}
