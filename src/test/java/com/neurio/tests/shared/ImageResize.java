package com.neurio.tests.shared; /**
 * Created by Robert on 7/14/2016.
 * Image Resize Class
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * @author mkyong
 *
 */
public class ImageResize {

    private static final int IMG_WIDTH = 360;
    private static final int IMG_HEIGHT = 640;

    public static void main(String[] args) {
        try {
            int fileNum = (int) Files.list(Paths.get("android\\")).count();
            for (int i = 1; i <= fileNum; i++) {
                String file = "android\\Case" + i + ".png";
                BufferedImage originalImage = ImageIO.read(new File(file));
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                BufferedImage resizeImageJpg = resizeImage(originalImage, type);
                ImageIO.write(resizeImageJpg, "png", new File(file));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    public static void start(String directory) {
        try {
            int fileNum = (int) Files.list(Paths.get(directory + "\\")).count();
            for (int i = 1; i <= fileNum; i++) {
                String file = directory + "\\Case" + i + ".png";
                BufferedImage originalImage = ImageIO.read(new File(file));
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                BufferedImage resizeImageJpg = resizeImage(originalImage, type);
                ImageIO.write(resizeImageJpg, "png", new File(file));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}