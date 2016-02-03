package controller;

import Jama.Matrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

public class AnaglyphMaker {

    private BufferedImage leftImage;
    private BufferedImage rightImage;
    private BufferedImage outputImage;

    private Matrix        leftMatrix      = new Matrix(new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 1}});
    private Matrix        rightMatrix     = new Matrix(new double[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 0}});
    private Matrix        leftImagePixel  = new Matrix(new double[][]{{0}, {0}, {0}});
    private Matrix        rightImagePixel = new Matrix(new double[][]{{0}, {0}, {0}});

    public AnaglyphMaker(BufferedImage leftImage, BufferedImage rightImage) {
        this.leftImage  = leftImage;
        this.rightImage = rightImage;
    }

    public void createAnaglyph() {
        createResult(leftImage, rightImage);

        try {
            ImageIO.write(outputImage, "png", new File("outputImages//anaglyph.generated.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage createResult(BufferedImage leftImage, BufferedImage rightImage) {
        outputImage = new BufferedImage(leftImage.getWidth(), leftImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < leftImage.getWidth(); ++i) {
            for (int j = 0; j < leftImage.getHeight(); ++j) {
                Color leftImageColor   = new Color(leftImage.getRGB(i, j));
                Color rightImageColor  = new Color(rightImage.getRGB(i, j));
                Color outputImageColor = new Color(leftImageColor.getRed(),
                        rightImageColor.getGreen(),
                        rightImageColor.getBlue());

                outputImage.setRGB(i, j, outputImageColor.getRGB());
            }
        }

        return outputImage;
    }

    public BufferedImage getResult() {
        return this.outputImage;
    }

}
