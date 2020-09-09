package com.deonova.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NotGate extends Gate{

    /*
        A Not Gate (Inverter) may have at most 1 input. An inverter processes its single input by inverting
        it (false -> true, true -> false).
     */

    public NotGate() {
        super(loadImage());
    }

    private static BufferedImage loadImage(){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("./src/com/deonova/ui/images/notGate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    //EFFECTS: adds an input, and the state of the input to this gate.
    //         then notifies every receivers.
    //MODIFIES: this -> inputs
    @Override
    public void update(Object bool, SignalSender sender) {
        if(bool instanceof Boolean){
            inputs.clear();
            inputs.put(sender, (Boolean) bool);
        }
        notifyReceivers();
    }

    //EFFECTS: return an output by inverting it's single input.
    @Override
    public boolean processOutput() {
        boolean output = false;
        for(SignalSender sender: inputs.keySet()){
            output = !inputs.get(sender);
        }
        return output;
    }
}
