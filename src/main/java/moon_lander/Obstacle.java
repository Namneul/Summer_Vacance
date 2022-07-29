package moon_lander;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The meteor which player will have to avoid
 *
 * @author https://github.com/dvpaa/Java-2022-Summer
 */

public class Obstacle {

    /**
     * We use this to generate a random number for starting x coordinate of the meteor.
     */
    private Random random;

    private int prob;

    /**
     * Images of the meteor in air.
     */
    private BufferedImage laserImg;


    /**
     * Width of meteor.
     */
    public int laserImgWidth;

    /**
     * Height of meteor.
     */
    public int laserImgHeight;

    /**
     * X coordinate of the meteor.
     */
    public int x;

    /**
     * X coordinate of the meteor.
     */
    public int y;

    public Obstacle()
    {

        Initialize();
        LoadContent();

    }

    private void Initialize()
    {
        random = new Random();
        ResetLasers();
    }


    private void LoadContent()
    {
        try
        {
            URL laserImgUrl = this.getClass().getResource("resources/images/laserBeam.png");
            laserImg = ImageIO.read(laserImgUrl);
            laserImgWidth = laserImg.getWidth();
            laserImgHeight = laserImg.getHeight();
        }
        catch (IOException ex) {
            Logger.getLogger(Obstacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ResetLasers()
    {

        prob = random.nextInt(100);
        if(prob <= 5){
            y = random.nextInt(Framework.frameHeight - laserImgHeight);
        }
    }

    public void Update()
    {
        prob = random.nextInt(100);
        if(prob <= 5){
        y = random.nextInt(Framework.frameHeight - laserImgHeight);
        } else {
            y = 10000;
        }

    }

    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(laserImg, x, y, null);
    }
}