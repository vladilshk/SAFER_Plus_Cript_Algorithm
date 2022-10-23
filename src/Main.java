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

        image = safer.codeText(image);
        //ImageConv.makeImage(image, "coded");
        image = safer.decodeText(image);

        ImageConv.makeImage(image, "decoded");
    }

}