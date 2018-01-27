/**
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "1982-08-31";
        Date date = new Date();
        try {
            date = sdf.parse(dateInString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ssXXX");
        formatter.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        // Prints the date in the CET timezone
        System.out.println(formatter.format(date));

    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
}