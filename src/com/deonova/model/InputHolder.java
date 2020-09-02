package com.deonova.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InputHolder implements AppObject, StateHolder, SignalSender{
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    private Boolean input;
    private Image currentImage;
    private static Image imageOn = null;
    private static Image imageOff = null;

    public InputHolder(){
        input = false;
        if (imageOn == null && imageOff == null) {
            try {
                imageOn = ImageIO.read(new File("./src/com/deonova/ui/images/inputOn.png"));
                imageOff = ImageIO.read(new File("./src/com/deonova/ui/images/inputOff.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentImage = imageOff;
    }


    @Override
    public Image getImage(){
        return currentImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);
    }

    @Override
    public void poke(){
        input = !input;
        if(currentImage.equals(imageOff)){
            currentImage = imageOn;
        } else {
            currentImage = imageOff;
        }
    }

    @Override
    public void sendSignal(SignalReceiver receiver) {
        receiver.receiveSignal(this.input);
    }
}
