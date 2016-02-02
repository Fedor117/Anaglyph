package controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

public class FilterController {

    private static BufferedImage image;

    private static final float[] FILTER_SHARPEN = { -1, -1, -1, -1, 9, -1, -1, -1, -1 };
    private static final float[] FILTER_SMTH    = { 0, 1, 1, 1, 2, 1, 0, 1, 0 };
    private static final float[] FILTER_EMBOSS  = { 1, 0, 0, 0, 1, 0, 0, 0, -1 };

    private static float         val            = 1.0f / 9.0f;
    private static float[]       filterBlur     = { val, val, val, val, val, val, val, val, val };

    public static BufferedImage lightSharpen(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, FILTER_SHARPEN));

        return op.filter(img, null);
    }

    public static BufferedImage smth(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 1, FILTER_SMTH));

        return op.filter(img, null);
    }

    public static BufferedImage lightEmboss(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, FILTER_EMBOSS));

        return op.filter(img, null);
    }

    public static void main(String[] args) {
        File file = new File("Lenna.png");

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage sharpen  = lightSharpen(image);
        BufferedImage smth     = smth(image);
        BufferedImage embossed = lightEmboss(image);

        try {
            ImageIO.write(sharpen, "png", new File("sharpen.png"));
            ImageIO.write(smth, "png", new File("smth.png"));
            ImageIO.write(embossed, "png", new File("embossed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
