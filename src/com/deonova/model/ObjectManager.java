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

    public void connectObject(DraggableImage sender, DraggableImage receiver){
        AppObject obj1 = getObject(sender);
        AppObject obj2 = getObject(receiver);
        if(obj1 instanceof  SignalSender && obj2 instanceof SignalReceiver){
            if(!(obj2 instanceof OutputHolder && ((OutputHolder) obj2).isConnected())){
                ((SignalSender) obj1).addReceiver((SignalReceiver) obj2);
            }
        }
    }

    public AppObject getObject(DraggableImage img){
        if(images.contains(img)){
            return objectList.get(images.indexOf(img));
        }
        return null;
    }

    public List<DraggableImage> getImages(){
        return images;
    }

    public void add(AppObject object){
        this.objectList.add(object);
        this.images.add(new DraggableImage(object));
    }

}
