package com.deonova.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Gate {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private List<Boolean> inputs;
    private boolean output;
    private final BufferedImage image;

    public Gate(BufferedImage icon){
        inputs = new ArrayList<>();
        output = processOutput(inputs);
        image = icon;
    }

    public Image getImage() {
        return image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);
    }

    public void addInput(boolean input){
        inputs.add(input);
    }

    public abstract boolean processOutput(List<Boolean> inputs);
}
