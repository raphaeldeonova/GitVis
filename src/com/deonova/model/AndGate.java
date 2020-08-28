package com.deonova.model;

import javax.swing.*;
import java.util.List;

public class AndGate extends Gate{


    public AndGate(ImageIcon icon) {
        super(icon); //TODO: find approptiate image for and gate, store images in a single package
    }

    @Override
    public boolean processOutput(List<Boolean> inputs) {
        boolean rsf =true;
        for(boolean input : inputs){
            rsf = rsf && input;
        }
        return rsf;
    }
}
