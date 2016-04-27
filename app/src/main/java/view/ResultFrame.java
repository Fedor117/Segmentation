package view;

import controller.SegmentationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ResultFrame extends JFrame implements ActionListener {

    public static final String NAME = "Segmentation";
    public static final String PERFORM_BTN_NAME = "Perform calculation";
    public static final String ERROR_MESSAGE = "Check number of clusters. 3 is minimal.";

    private SegmentationController controller;
    private JButton                performBtn;
    private JTextField             clusterField;
    private BufferedImage          inputImage;
    private BufferedImage          resultImage;
    private JLabel                 inputLabel = new JLabel();
    private JLabel                 resultLabel = new JLabel();
    private JPanel                 imagePanel   = new JPanel();

    public ResultFrame(SegmentationController controller) {
        super(NAME);
        this.controller = controller;
        controller.calculate(controller.getOriginal(),
                SegmentationController.DEFAULT_NUM_OF_CLUSTERS);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(dimension.width / 15, dimension.height / 5);
        setSize(dimension.width / 5 * 3, dimension.height / 5 * 3);

        clusterField     = new JTextField(10);
        performBtn       = new JButton(PERFORM_BTN_NAME);
        JPanel funcPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        performBtn.addActionListener(this);

        funcPanel.setLayout(new FlowLayout());
        funcPanel.add(clusterField);
        funcPanel.add(performBtn);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(funcPanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel);

        imagePanel.setLayout(new FlowLayout());
        imagePanel.add(inputLabel);
        imagePanel.add(resultLabel);

        this.setImages();
        this.setDesign();
        this.add(mainPanel);
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
        inputImage = controller.getOriginal();
        resultImage = controller.getResult();

        inputLabel.setIcon(new ImageIcon(inputImage));
        resultLabel.setIcon(new ImageIcon(resultImage));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == performBtn) {
            try {
                this.resultImage = controller.calculate(controller.getOriginal(),
                        Integer.parseInt(clusterField.getText()));
                this.resultLabel.setIcon(new ImageIcon(resultImage));
                this.repaint();
            } catch (Exception ex) {
                System.out.println(ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
