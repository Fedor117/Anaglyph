package controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnaglyphMaker {

    private static final String OUTPUT_LOCATION = "outputImages//anaglyph.generated.png";

    private BufferedImage leftImage;
    private BufferedImage rightImage;
    private BufferedImage outputImage;

    public AnaglyphMaker(BufferedImage leftImage, BufferedImage rightImage) {
        this.leftImage  = leftImage;
        this.rightImage = rightImage;
    }

    public void createAnaglyph() {
        createResult(leftImage, rightImage);

        try {
            ImageIO.write(outputImage, "png", new File(OUTPUT_LOCATION));
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
