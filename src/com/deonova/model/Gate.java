package com.deonova.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Gate implements AppObject, SignalReceiver, SignalSender{
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    protected List<Boolean> inputs;
    private boolean output;
    private final BufferedImage image;

    public Gate(BufferedImage icon){
        inputs = new ArrayList<>();
        output = false; // default
        image = icon;
    }

    @Override
    public Image getImage() {
        return image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);
    }

    public void addInput(boolean input){
        inputs.add(input);
    }

    public abstract boolean processOutput();

    @Override
    public void receiveSignal(Boolean signal) {
        addInput(signal);
    }

    @Override
    public void sendSignal(SignalReceiver receiver) {
        receiver.receiveSignal(processOutput());
    }
}
