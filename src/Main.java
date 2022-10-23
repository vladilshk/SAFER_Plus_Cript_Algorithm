import Safer.ImageConv;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Main {
    public static void main(String[] args) throws IOException {

        SAFER safer = new SAFER();

        int[][] image = ImageConv.getImage();

        int[][] info = new int[100][16];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < image[0].length; j++) {
                info[i][j] = image[i][j];
            }
        }

        image = safer.codeText(image);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                image[i][j] = info[i][j];
            }
        }
        ImageConv.makeImage(image, "coded");

        image = safer.decodeText(image);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                image[i][j] = info[i][j];
            }
        }

        ImageConv.makeImage(image, "decoded");
    }

}