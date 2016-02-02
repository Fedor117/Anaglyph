package controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnaglyphMaker {

    private BufferedImage leftImage;
    private BufferedImage rightImage;
    private BufferedImage outputImage;

    public AnaglyphMaker(BufferedImage leftImage, BufferedImage rightImage) {
        this.leftImage  = leftImage;
        this.rightImage = rightImage;
    }

    public void createAnaglyph() {
        for(int i=0; i < leftImage.getHeight(); i++) {
            for (int j = 0; j < leftImage.getWidth(); j++) {
                Color leftImageColor = new Color(leftImage.getRGB(j, i));
            }
        }

        for(int i=0; i < rightImage.getHeight(); i++) {
            for (int j = 0; j < rightImage.getWidth(); j++) {
                Color rightImageColor = new Color(rightImage.getRGB(j, i));
            }
        }

        outputImage = new BufferedImage(leftImage.getWidth(), leftImage.getHeight(),
                      BufferedImage.TYPE_INT_ARGB);
        int iAlpha=0;
        int iRed=0;
        int iGreen=0;
        int iBlue=0;
        int newPixel=0;

        for(int i = 0; i < leftImage.getWidth(); i++) {
            for(int j=0; j < leftImage.getHeight(); j++) {
                newPixel =0;
                iAlpha   = new Color(leftImage.getRGB(i, j)).getAlpha();
                iRed     = new Color(leftImage.getRGB(i, j)).getRed();
                iGreen   = new Color(leftImage.getRGB(i, j)).getGreen();
                iBlue    = new Color(leftImage.getRGB(i, j)).getBlue();

                if(true) {
                    newPixel = newPixel | iRed<<16;
                }
                if(false) {
                    newPixel = newPixel | iGreen<<8;
                }
                if(false) {
                    newPixel = newPixel | iBlue;
                }

                leftImage.setRGB(i, j, newPixel);
            }
        }

        for(int i = 0; i < rightImage.getWidth(); i++) {
            for(int j=0; j < rightImage.getHeight(); j++) {
                newPixel =0;
                iAlpha   = new Color(rightImage.getRGB(i, j)).getAlpha();
                iRed     = new Color(rightImage.getRGB(i, j)).getRed();
                iGreen   = new Color(rightImage.getRGB(i, j)).getGreen();
                iBlue    = new Color(rightImage.getRGB(i, j)).getBlue();

                if(false) {
                    newPixel = newPixel | iRed<<16;
                }
                if(false) {
                    newPixel = newPixel | iGreen<<8;
                }
                if(true) {
                    newPixel = newPixel | iBlue;
                }
                rightImage.setRGB(i, j, newPixel);
            }
        }

        Graphics graphics = outputImage.createGraphics();
        graphics.drawImage(leftImage, 0, 0, null);
        graphics.drawImage(rightImage, 0, 0, null);
        graphics.dispose();

        try {
            ImageIO.write(outputImage, "jpg", new File("anaglyph.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getResult() {
        return this.outputImage;
    }

}
