package com.deonova.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Gate {
    private List<Boolean> inputs;
    private boolean output;
    private final ImageIcon image; //TODO: what class can be rendered in swing

    public Gate(ImageIcon icon){
        inputs = new ArrayList<>();
        output = processOutput(inputs);
        image = icon;
    }


    public void addInput(boolean input){
        inputs.add(input);
    }

    public abstract boolean processOutput(List<Boolean> inputs);
}
