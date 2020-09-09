package com.deonova.model;


import com.deonova.ui.DraggableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OutputHolder implements AppObject, SignalReceiver {

    /*
        An outputHolder has a state that is given by a single sender.
        It may have at most 1 input.
        It is responsible for storing that input state, changing everytime there's change of input.
    */

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    private Boolean output;
    private Boolean isConnected;
    private Image currentImage;
    private static Image imageOn = null;
    private static Image imageOff = null;

    private DraggableImage draggableImage;

    //EFFECTS: loads imageOn and imageOff when first invoked, creates an outputHolder with false state
    public OutputHolder(){
        isConnected = false;
        draggableImage = null;
        output = false;
        try{
            if(imageOn == null && imageOff == null){
                imageOn = ImageIO.read(new File("./src/com/deonova/ui/images/outputOn.png"));
                imageOff = ImageIO.read(new File("./src/com/deonova/ui/images/outputOff.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentImage = imageOff;
    }

    @Override
    public void linkDraggableImage(DraggableImage image) {
        if(draggableImage == null){
            this.draggableImage = image;
        }
    }

    @Override
    public DraggableImage getDraggableImage() {
        return draggableImage;
    }

    public boolean isConnected(){
        return isConnected;
    }

    //EFFECTS: updates the current state based on the boolean passed.
    @Override
    public void update(Object bool, SignalSender sender){
            if (bool instanceof Boolean) {
                output = (Boolean) bool;
            }
            if (output) {
                currentImage = imageOn;
            } else {
                currentImage = imageOff;
            }
            isConnected = true;
    }

    @Override
    public Image getImage() {
        return currentImage.getScaledInstance(WIDTH,HEIGHT, Image.SCALE_FAST);
    }
}
