package controller;

import view.ResultFrame;

public class EntryPoint {

    public static final int NUM_OF_CLUSTERS = 0;

    public static void main(String[] args) {
        new ResultFrame(new SegmentationController(NUM_OF_CLUSTERS));
    }

}
