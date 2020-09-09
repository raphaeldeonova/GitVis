package com.deonova.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OrGate extends Gate{

    /*
        An OrGate processes its inputs by applying or operators to every single input (OrMap)
     */

    public OrGate() {
        super(loadImage());
    }

    //EFFECTS: loads the AndGate image to be passed to super in constructor from
    //         com.deonova.model.ui.images
    private static BufferedImage loadImage(){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("./src/com/deonova/ui/images/orGate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    //EFFECTS: returns an output based on applying or to every single input.
    @Override
    public boolean processOutput() {
        boolean rsf = false;
        for(SignalSender sender: inputs.keySet()){
            rsf = rsf || inputs.get(sender);
        }
        return rsf;
    }
}
