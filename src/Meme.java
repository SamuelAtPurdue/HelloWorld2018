package com.hello;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Random;

/**
 * Created by Samuel Hild on 9/16/2018.
 */
public class Meme {
    public Meme(){
        BufferedImage shialaBufferedImage;
        File file = new File("C:\\Users\\agent\\IdeaProjects\\HelloWorld2018\\src\\com\\hello\\Meme\\motivation.jpg");
        Random random = new Random(LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset()));
        //System.out.println(random.nextInt(100));
        try {

            shialaBufferedImage = ImageIO.read(file);
            Tyrone.postImage("Shia");
        }catch (Exception except) {
            System.err.print("Failed to read file: Memes too dank.");
            except.printStackTrace();
        }
    }//public Image randomMeme(){
        //Image meme;


        //return meme;
//    }
}
