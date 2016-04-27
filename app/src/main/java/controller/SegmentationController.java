package controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class SegmentationController {

    public static final int DEFAULT_NUM_OF_CLUSTERS = 2;
    public static final String PATH_TO_FILE = "app//src//main//resources//Lenna.png";

    private BufferedImage original;
    private BufferedImage result;
    private Cluster[]     clusters;

    public SegmentationController(int numOfClusters) {
        original = loadImage();

        if (numOfClusters == 0 || numOfClusters == 1) {
            numOfClusters = DEFAULT_NUM_OF_CLUSTERS;
        }

        result = calculate(original, numOfClusters);
    }

    public BufferedImage calculate(BufferedImage image, int numOfClusters) {
        int  width  = image.getWidth();
        int  height = image.getHeight();

        clusters = createClusters(image, numOfClusters);

        int[] lookupTable = new int[width * height];
        Arrays.fill(lookupTable, -1);

        boolean pixelChangedCluster = true;

        while (pixelChangedCluster) {
            pixelChangedCluster = false;
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    int pixel = image.getRGB(x, y);
                    Cluster cluster = findMinimalCluster(pixel);
                    if (lookupTable[width * y + x] != cluster.getId()) {
                        if (lookupTable[width * y + x] != -1) {
                            clusters[lookupTable[width*y+x]].removePixel(pixel);
                        }
                        cluster.addPixel(pixel);
                        pixelChangedCluster = true;
                        lookupTable[width * y + x] = cluster.getId();
                    }
                }
            }
        }
        BufferedImage result = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int clusterId = lookupTable[width * y + x];
                result.setRGB(x, y, clusters[clusterId].getRGB());
            }
        }

        return result;
    }

    public Cluster[] createClusters(BufferedImage image, int k) {
        Cluster[] result = new Cluster[k];

        int x = 0;
        int y = 0;
        int dx = image.getWidth() / k;
        int dy = image.getHeight() / k;

        for (int i = 0; i < k; ++i) {
            result[i] = new Cluster(i, image.getRGB(x, y));
            x += dx;
            y += dy;
        }

        return result;
    }

    public Cluster findMinimalCluster(int rgb) {
        Cluster cluster = null;
        int min = Integer.MAX_VALUE;
        for (Cluster clusterTmp : clusters) {
            int distance = clusterTmp.distance(rgb);
            if (distance < min) {
                min = distance;
                cluster = clusterTmp;
            }
        }
        return cluster;
    }

    public static BufferedImage loadImage() {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new File(PATH_TO_FILE));
        } catch (Exception e) {
            System.out.println(e.toString() + "Image '"
                    + PATH_TO_FILE + "' not found.");
        }
        return result;
    }

    public BufferedImage getResult() {
        return result;
    }

    public BufferedImage getOriginal() {
        return original;
    }

}
