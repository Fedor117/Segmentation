package view;

import controller.SegmentationController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ResultFrame extends JFrame {

    public static final String NAME = "Segmentation";

    private SegmentationController controller;
    private JPanel                 imagePanel   = new JPanel();
    private ArrayList<JLabel>      labels       = new ArrayList<>();

    public ResultFrame(SegmentationController controller) {
        super(NAME);
        this.controller = controller;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(dimension.width / 15, dimension.height / 5);
        setSize(dimension.width / 5 * 3, dimension.height / 5 * 3);

        imagePanel.setLayout(new FlowLayout());

        this.setImages();
        this.setDesign();
        this.add(imagePanel);
        this.pack();
        this.setVisible(true);
    }

    public void setDesign() {
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
    }

    private void setImages() {
        ArrayList<BufferedImage> images = new ArrayList<>();
        images.add(controller.getOriginal());
        images.add(controller.getResult());

        for (BufferedImage image: images) {
            ImageIcon icon = new ImageIcon(image);
            JLabel label = new JLabel();
            label.setIcon(icon);
            labels.add(label);
        }

        for (JLabel label : labels)
            imagePanel.add(label);
    }

}
