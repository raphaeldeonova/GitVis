package com.deonova.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrGate extends Gate{

    public OrGate() {
        super(loadImage());
    }

    private static BufferedImage loadImage(){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("./src/com/deonova/ui/images/orGate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    @Override
    public boolean processOutput(List<Boolean> inputs) {
        return false;
    }
}
