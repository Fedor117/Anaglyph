package controller;

import Jama.Matrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

public class FilterController {

    private static BufferedImage image;

    private static final float[] FILTER_SHARPEN1 = {-1, -1, -1, -1, 9, -1, -1, -1, -1};
    private static final float[] FILTER_SMTH1 = {0, 1, 1, 1, 2, 1, 0, 1, 0};
    private static final float[] FILTER_EMBOSS1 = {1, 0, 0, 0, 1, 0, 0, 0, -1};

    private static float val = 1.0f / 9.0f;
    private static float[] filterBlur = {val, val, val, val, val, val, val, val, val};

    private static final Matrix FILTER_SHARPEN = new Matrix(new double[][]{{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}});
    private static final Matrix FILTER_BLUR    = new Matrix(new double[][]{{val, val, val}, {val, val, val}, {val, val, val}});
    private static final Matrix FILTER_EMBOSS  = new Matrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, -1}});

    private static Matrix inputMatrix  = new Matrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
    private static Matrix outputMatrix = new Matrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});

    public static BufferedImage sharpen(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < input.getWidth(); ++i) {
            for (int j = 0; j < input.getHeight(); ++j) {
                Color inputImageColor  = new Color(input.getRGB(i, j));
                Color outputImageColor = new Color(
                (int) inputMatrix
                        .times(FILTER_BLUR)
                        .get(0, 0),
                (int) inputMatrix
                        .times(FILTER_BLUR)
                        .get(1, 0),
                (int) inputMatrix
                        .times(FILTER_BLUR)
                        .get(2, 0));

                output.setRGB(i, j, outputImageColor.getRGB());
            }
        }

        return output;
    }

    public static BufferedImage lightSharpen(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, FILTER_SHARPEN1));

        return op.filter(img, null);
    }

    public static BufferedImage smth(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, FILTER_SMTH1));

        return op.filter(img, null);
    }

    public static BufferedImage lightEmboss(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, FILTER_EMBOSS1));

        return op.filter(img, null);
    }

    public static BufferedImage lightBlur(BufferedImage img) {
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, filterBlur));

        return op.filter(img, null);
    }

    public static void main(String[] args) {
        File file = new File("src//main//resources//Lenna.png");

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage sharpen = lightSharpen(image);
        BufferedImage smth = smth(image);
        BufferedImage embossed = lightEmboss(image);
        BufferedImage blurred = lightBlur(image);

        try {
            ImageIO.write(sharpen, "png", new File("outputImages//filter.sharpen.png"));
            ImageIO.write(smth, "png", new File("outputImages//filter.smth.png"));
            ImageIO.write(embossed, "png", new File("outputImages//filter.embossed.png"));
            ImageIO.write(image, "png", new File("outputImages//filter.blurred.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
