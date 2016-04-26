package controller;

public class Cluster {

    int id;
    int pixelCount;
    int red;
    int green;
    int blue;
    int numOfRed;
    int numOfGreen;
    int numOfBlue;

    public Cluster(int id, int rgb) {
        int r = rgb>>16&0x000000FF;
        int g = rgb>> 8&0x000000FF;
        int b = rgb>> 0&0x000000FF;

        red   = r;
        green = g;
        blue  = b;

        this.id = id;

        addPixel(rgb);
    }

    int getId() {
        return id;
    }

    int getRGB() {
        int r = numOfRed / pixelCount;
        int g = numOfGreen / pixelCount;
        int b = numOfBlue / pixelCount;
        return 0xff000000|r<<16|g<<8|b;
    }

    void addPixel(int color) {
        int r = color>>16&0x000000FF;
        int g = color>> 8&0x000000FF;
        int b = color>> 0&0x000000FF;

        numOfRed   += r;
        numOfGreen += g;
        numOfBlue  += b;

        pixelCount++;

        red   = numOfRed / pixelCount;
        green = numOfGreen / pixelCount;
        blue  = numOfBlue / pixelCount;
    }

    void removePixel(int color) {
        int r = color>>16&0x000000FF;
        int g = color>> 8&0x000000FF;
        int b = color>> 0&0x000000FF;

        numOfRed   -= r;
        numOfGreen -= g;
        numOfBlue  -= b;

        pixelCount--;

        red   = numOfRed / pixelCount;
        green = numOfGreen / pixelCount;
        blue  = numOfBlue / pixelCount;
    }

    int distance(int color) {
        int r = color>>16&0x000000FF;
        int g = color>> 8&0x000000FF;
        int b = color>> 0&0x000000FF;

        int rx = Math.abs(red - r);
        int gx = Math.abs(green - g);
        int bx = Math.abs(blue - b);

        int d = (rx+gx+bx) / 3;

        return d;
    }

}
