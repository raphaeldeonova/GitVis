package com.deonova.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AndGate extends Gate{

    /*
        An AndGate processes its inputs by applying and operators to every single input (AndMap)
     */

    public AndGate() {
        super(loadImage());
    }

    //EFFECTS: loads the AndGate image to be passed to super in constructor from
    //         com.deonova.model.ui.images
    private static BufferedImage loadImage(){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("./src/com/deonova/ui/images/andGate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    //EFFECTS: returns an output based on applying and to every single input.
    @Override
    public boolean processOutput() {
        boolean rsf = true;
        for(SignalSender sender: inputs.keySet()){
            rsf = rsf && inputs.get(sender);
        }
        return rsf;
    }
}
