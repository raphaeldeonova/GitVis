package com.deonova.ui;

import com.deonova.model.AppObject;
import com.deonova.model.Gate;
import com.deonova.model.InputHolder;

import javax.swing.*;
import java.awt.*;

public class DraggableImage{
    private static final int SIGNAL_OFFSET = 30;
    private ImageIcon img;
    private AppObject object;
    private Point imageCorner;
    private Point prevPt;
    private Point signalsReceiverPoint;
    private Point signalSenderPoint;
    private DraggableImage receiver; //image to receive signal from this image

    public DraggableImage(AppObject object) {
        this.object = object;
        this.img = new ImageIcon(object.getImage());
        this.imageCorner = new Point(0,0);
        updateSignalPoints();
        this.receiver = null;
    }

    public void connectReceiver(DraggableImage receiver){
        this.receiver = receiver;
    }

    public void updateSignalPoints(){
        if (object.getClass() == InputHolder.class){
            this.signalSenderPoint = new Point(
                    this.imageCorner.x + InputHolder.WIDTH,
                    this.imageCorner.y + InputHolder.HEIGHT/2
            );
            this.signalsReceiverPoint = null;
        } else {
            this.signalSenderPoint = new Point(
                    this.imageCorner.x + Gate.WIDTH,
                    this.imageCorner.y + Gate.HEIGHT / 2
            );
            this.signalsReceiverPoint = new Point(
                    this.imageCorner.x,
                    this.imageCorner.y + Gate.HEIGHT / 2
            );
        }
    }

    public void setImg(Image img) {
        this.img = new ImageIcon(img);
    }

    //returns true if the point is inside image
    public boolean contains(Point p){
        boolean cond1;
        boolean cond2;
        if (object.getClass() == InputHolder.class){
            cond1 = p.x >= imageCorner.getX() && p.x <= imageCorner.getX() + InputHolder.WIDTH;
            cond2 = p.y >= imageCorner.getY() && p.y <= imageCorner.getY() + InputHolder.WIDTH;
        } else {
            cond1 = p.x >= imageCorner.getX() && p.x <= imageCorner.getX() + Gate.WIDTH;
            cond2 = p.y >= imageCorner.getY() && p.y <= imageCorner.getY() + Gate.WIDTH;
        }
        return cond1 && cond2;

    }

    //returns true if the point is around signal receiver
    public boolean aroundReceiver(Point p){
        if(object.getClass() == InputHolder.class){
            return false;
        }
        double dx = signalsReceiverPoint.getX() - p.getX();
        double dy = signalsReceiverPoint.getY() - p.getY();
        double d = Math.sqrt((dx*dx + dy*dy));
        return d <= SIGNAL_OFFSET;
    }

    //returns true if the point is around signal sender
    public boolean aroundSender(Point p){
        double dx = signalSenderPoint.getX() - p.getX();
        double dy = signalSenderPoint.getY() - p.getY();
        double d = Math.sqrt((dx*dx + dy*dy));
        return d <= SIGNAL_OFFSET;
    }

    public void setImageCorner(Point imageCorner) {
        this.imageCorner = imageCorner;
    }

    public void setPrevPt(Point prevPt) {
        this.prevPt = prevPt;
    }

    public ImageIcon getImg() {
        return img;
    }

    public Point getImageCorner() {
        return imageCorner;
    }

    public Point getPrevPt() {
        return prevPt;
    }

    public Point getSignalSenderPoint() {
        return signalSenderPoint;
    }

    public Point getSignalsReceiverPoint() {
        return signalsReceiverPoint;
    }

    public DraggableImage getReceiver() {
        return receiver;
    }
}
