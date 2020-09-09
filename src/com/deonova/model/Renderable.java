package com.deonova.model;

import com.deonova.ui.DraggableImage;

import java.awt.*;

public interface Renderable {
    Image getImage();
    void linkDraggableImage(DraggableImage image);
    DraggableImage getDraggableImage();
}
