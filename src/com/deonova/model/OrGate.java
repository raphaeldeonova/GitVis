package com.deonova.model;

import javax.swing.*;
import java.util.List;

public class OrGate extends Gate{

    public OrGate(ImageIcon icon) {
        super(icon); // TODO: Find appropriate image for orgate, store images in a single package;
    }

    @Override
    public boolean processOutput(List<Boolean> inputs) {
        return false;
    }
}
