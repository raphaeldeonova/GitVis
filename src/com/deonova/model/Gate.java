package com.deonova.model;

import com.deonova.ui.DraggableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public abstract class Gate implements AppObject, SignalReceiver, SignalSender{
    /*
        A gate has an image, outputs that this gate points to, and inputs that points to this gate.
        Gates are responsible for gathering inputs, processing those inputs to create a single output,
        and sending that output to another SignalReceiver.
     */

    //Gate ids are positive integers, to differentiate with inputHolders
    //ids are used to create hashcodes
    private static long id = 19;

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private BufferedImage image;

    //gates and outputHolders that this gate points to
    protected List<SignalReceiver> outputs;

    //gates and inputHolders that points to this gate
    protected Map<SignalSender, Boolean> inputs;

    protected DraggableImage draggableImage;


    //EFFECTS: creates a Gate object with image icon, empty inputs and empty outputs.
    //         every Gate instance should have a unique ID.
    public Gate(BufferedImage icon){
        inputs = new HashMap<>();
        outputs = new ArrayList<>();
        image = icon;
        id++;
        draggableImage = null;
    }

    public void linkDraggableImage(DraggableImage image){
        if(this.draggableImage == null){
            this.draggableImage = image;
        }
    }

    @Override
    public DraggableImage getDraggableImage() {
        return draggableImage;
    }

    //EFFECTS: gets the scaled instance's image
    @Override
    public Image getImage() {
        return image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);
    }

    @Override
    public ArrayList<SignalReceiver> getReceivers() {
        return (ArrayList<SignalReceiver>) outputs;
    }

    @Override
    //EFFECTS: adds a receiver that this gate points to, to a collection of receivers (outputs)
    //MODIFIES: this -> outputs
    public void addReceiver(SignalReceiver receiver){
        if(!outputs.contains(receiver)){
            this.outputs.add(receiver);
        }
    }

    //EFFECTS: gets a single boolean output based on the current inputs and kind of gate
    public abstract boolean processOutput();


    //EFFECTS: adds an input, and the state of the input to this gate.
    //         then notifies every receivers.
    //MODIFIES: this -> inputs
    @Override
    public void update(Object bool, SignalSender sender) {
        if(bool instanceof Boolean){
            this.inputs.put(sender, (Boolean) bool);
        }
        notifyReceivers();
    }


    //EFFECTS: notifies every receivers this gate points to about this gate and the correct output.
    //MODIFIES: this -> outputs
    @Override
    public void notifyReceivers() {
        for(SignalReceiver receiver: outputs){
            receiver.update(processOutput(), this);
        }
    }

    //EFFECTS: removes the given receivers from outputs (if present)
    @Override
    public void removeReceiver(SignalReceiver receiver) {
        this.outputs.remove(receiver);
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
