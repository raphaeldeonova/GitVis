package com.deonova.model;

import com.deonova.ui.DragPanel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ObjectManager {
    private List<AppObject> objectList;
    private List<DragPanel.DraggableImage> images;

    public ObjectManager() {
        this.objectList = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public AppObject getObject(DragPanel.DraggableImage img){
        if(images.contains(img)){
            return objectList.get(images.indexOf(img));
        }
        return null;
    }

    public List<DragPanel.DraggableImage> getImages(){
        return images;
    }

    public void add(AppObject object){
        this.objectList.add(object);
        this.images.add(new DragPanel.DraggableImage(object.getImage()));
    }

}
