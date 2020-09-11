package com.deonova.model;

import com.deonova.ui.DraggableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class InputHolder implements AppObject, SignalSender{

    /*
        An InputHolder saves a single state to be passed around (true or false).
        It has a state, an image based on that state, and a collection of recivers that this holder points to.
        It is responsible for holding that state, changing that state when poked, and
        notifying every single receivers when a change has been made.
     */

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    //InputHolder ids are negative integers, to differentiate with gates.
    //ids are used to create hashcodes
    private static long id = -79;

    private Boolean input;
    private Image currentImage;
    private static Image imageOn = null;
    private static Image imageOff = null;

    // gates and outputholders that this input points to
    private java.util.List<SignalReceiver> outputs;

    private DraggableImage draggableImage;


    //EFFECTS: loads imageOn and imageOff when invoked for the first time,
    //         creates a new InputHolder with a false state and a new id
    public InputHolder(){
        draggableImage = null;
        outputs = new ArrayList<>();
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
        id--;
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

    //EFFECTS: adds a receiver that this gate points to, to a collection of receivers (outputs)
    //MODIFIES: this -> outputs
    @Override
    public void addReceiver(SignalReceiver receiver){
        if(!outputs.contains(receiver)){
            this.outputs.add(receiver);
        }
    }

    //EFFECTS: remove the given receivers from outputs(if present)
    @Override
    public void removeReceiver(SignalReceiver receiver) {
        this.outputs.remove(receiver);
    }

    //EFFECTS: gets the scaled current image
    @Override
    public Image getImage(){
        return currentImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);
    }

    //EFFECTS: inverts the state and updates the currentimage, and then notifies every receivers
    public void poke(){
        input = !input;
        if(currentImage.equals(imageOff)){
            currentImage = imageOn;
        } else {
            currentImage = imageOff;
        }
        notifyReceivers();
    }

    //EFFECTS: Notifies receiver of the state and this
    //MODIFIES: this -> outputs
    @Override
    public void notifyReceivers() {
        for(SignalReceiver receiver: outputs){
            receiver.update(input, this);
        }
    }

    @Override
    public ArrayList<SignalReceiver> getReceivers() {
        return (ArrayList<SignalReceiver>) outputs;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
