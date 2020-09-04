package com.deonova.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AndGate extends Gate{


    public AndGate() {
        super(loadImage());
    }

    private static BufferedImage loadImage(){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("./src/com/deonova/ui/images/andGate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    @Override
    public boolean processOutput() {
        boolean rsf =true;
        for(boolean input : inputs){
            rsf = rsf && input;
        }
        return rsf;
    }
}
