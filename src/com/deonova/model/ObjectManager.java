package com.deonova.model;

import com.deonova.ui.DraggableImage;

import java.util.*;
import java.util.List;

public class ObjectManager {
    private List<AppObject> objectList;
    private List<DraggableImage> images;

    public ObjectManager() {
        this.objectList = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public void update(){
        for(AppObject object: objectList){
            images.get(objectList.indexOf(object)).setImg(object.getImage());
            if(object instanceof InputHolder){
                ((InputHolder) object).notifyReceivers();
            }
        }
    }

    //EFFECTS: returns true if the sender image is connected to the receiver
    public boolean isConnectedTo(DraggableImage sender, DraggableImage receiver){
        AppObject obj1 = getObject(sender);
        AppObject obj2 = getObject(receiver);
        if(obj1 instanceof SignalSender && obj2 instanceof SignalReceiver){
            return ((SignalSender) obj1).getReceivers().contains(obj2);
        }
        return false;
    }

    //EFFECTS: disconnects the sender from the receiver (i.e removes receiver from sender)
    //MODIFIES: sender
    public void disconnectObject(DraggableImage sender, DraggableImage receiver){
        AppObject obj1 = getObject(sender);
        AppObject obj2 = getObject(receiver);
        if(obj1 instanceof SignalSender && obj2 instanceof SignalReceiver){
            ((SignalSender) obj1).removeReceiver((SignalReceiver) obj2);
        }
    }

    //EFFECTS: connects the sender to a receiver
    //MODIFIES: sender
    public void connectObject(DraggableImage sender, DraggableImage receiver){
        AppObject obj1 = getObject(sender);
        AppObject obj2 = getObject(receiver);
        if(obj1 instanceof  SignalSender && obj2 instanceof SignalReceiver){
            if(!(obj2 instanceof OutputHolder && ((OutputHolder) obj2).isConnected())){
                ((SignalSender) obj1).addReceiver((SignalReceiver) obj2);
            }
        }
    }

    //EFFECTS: returns the object given the draggable image
    public AppObject getObject(DraggableImage img){
        if(images.contains(img)){
            return objectList.get(images.indexOf(img));
        }
        return null;
    }

    //EFFECTS: returns the list of available draggableImages
    public List<DraggableImage> getImages(){
        return images;
    }

    //EFFECTS: adds the object to objectList, and adds a new draggableImage to images
    //MODIFIES: this -> objectList, images
    public void add(AppObject object){
        this.objectList.add(object);
        this.images.add(new DraggableImage(object));
    }

    //EFFECTS: removes the object from objectList, disconnects every sender to this
    //         and deletes the appropriate DraggableImage from images
    public void remove(AppObject object){
        if(objectList.contains(object)){
            images.remove(objectList.indexOf(object));
            if(object instanceof SignalReceiver){
                for(AppObject obj: objectList){
                    if(obj instanceof SignalSender){
                        ((SignalSender) obj).removeReceiver((SignalReceiver) object);
                    }
                }
            }
            objectList.remove(object);
        }
    }

}
