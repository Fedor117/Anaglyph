package view;

import controller.AnaglyphMaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

    private static final String NAME = "Anaglyph Maker";
    private static final String LEFT_IMAGE_NAME = "left.jpg";
    private static final String RIGHT_IMAGE_NAME = "right.jpg";

    private BufferedImage leftImage;
    private BufferedImage rightImage;
    private BufferedImage anaglyph;
    private Box           imagePanel         = new Box(BoxLayout.X_AXIS);
    private JPanel        leftImagePanel     = new JPanel();
    private JPanel        rightImagePanel    = new JPanel();
    private JPanel        outputImagePanel   = new JPanel();
    private JLabel        leftImageLabel     = new JLabel();
    private JLabel        rightImageLabel    = new JLabel();
    private JLabel        anaglyphImageLabel = new JLabel();

    public MainFrame() {
        super(NAME);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(dimension.width / 15, dimension.height / 5);
        setSize(dimension.width / 5 * 3, dimension.height / 5 * 3);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus is not available");
        }

        leftImagePanel.add(leftImageLabel);
        rightImagePanel.add(rightImageLabel);
        outputImagePanel.add(anaglyphImageLabel);

        imagePanel.add(leftImagePanel);
        imagePanel.add(rightImagePanel);
        imagePanel.add(outputImagePanel);

        this.getImages();

        AnaglyphMaker anaglyphMaker = new AnaglyphMaker(leftImage, rightImage);
        anaglyphMaker.createAnaglyph();
        this.anaglyph = anaglyphMaker.getResult();

        Icon outputImageIcon = new ImageIcon(anaglyph);
        anaglyphImageLabel.setIcon(outputImageIcon);

        this.add(imagePanel);
        this.pack();
        this.setVisible(true);
    }

    public void getImages() {
        ClassLoader classLoader = getClass().getClassLoader();
        File rightImageFile = new File(classLoader.getResource(LEFT_IMAGE_NAME).getFile());
        File leftImageFile = new File(classLoader.getResource(RIGHT_IMAGE_NAME).getFile());

        try {
            leftImage   = ImageIO.read(leftImageFile);
            rightImage  = ImageIO.read(rightImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Icon leftImageIcon  = new ImageIcon(leftImage);
        Icon rightImageIcon = new ImageIcon(rightImage);

        leftImageLabel.setIcon(leftImageIcon);
        rightImageLabel.setIcon(rightImageIcon);
    }

}
