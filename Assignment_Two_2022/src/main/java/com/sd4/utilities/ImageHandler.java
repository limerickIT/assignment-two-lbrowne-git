package com.sd4.utilities;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageHandler {
    private static final String ImageDir = new File("").getAbsolutePath() + "/src/main/resources/static/assets/images/";
    private static BufferedImage image;

    public static byte[] GetImage(String path){
        try {
            String imagePath = ImageDir + path+".jpg";
            System.out.println(imagePath);

            File file = new File(imagePath);
            if(file.exists()){
                BufferedImage bi;
                    bi = ImageIO.read(file);
                
                ByteArrayOutputStream bArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bi, "JPG", bArrayOutputStream);
                return bArrayOutputStream.toByteArray();
            }
            else{
                return null;
            }
            }
        catch (IOException e) {
            return null;
        }
    }
}
