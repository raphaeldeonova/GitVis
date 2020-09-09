package com.deonova.ui;

import com.deonova.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DraggableImage{
    private static final int SIGNAL_OFFSET = 30;
    private ImageIcon img;
    private AppObject object;
    private Point imageCorner;
    private Point prevPt;
    private Point signalsReceiverPoint;
    private Point signalSenderPoint;

    public DraggableImage(AppObject object) {
        this.object = object;
        this.object.linkDraggableImage(this);
        this.img = new ImageIcon(object.getImage());
        this.imageCorner = new Point(0,0);
        updateSignalPoints();
    }

    public void connectReceiver(SignalReceiver receiver){
        if(this.object instanceof SignalSender){
            ((SignalSender) this.object).addReceiver(receiver);
        }
    }

    public void updateSignalPoints(){
        if (object instanceof InputHolder){
            this.signalSenderPoint = new Point(
                    this.imageCorner.x + InputHolder.WIDTH,
                    this.imageCorner.y + InputHolder.HEIGHT/2
            );
            this.signalsReceiverPoint = null;
        } else if(object instanceof OutputHolder){
            this.signalSenderPoint = null;
            this.signalsReceiverPoint = new Point(
              this.imageCorner.x,
              this.imageCorner.y + OutputHolder.HEIGHT/2
            );
        }else {
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
        if (object instanceof InputHolder){
            cond1 = p.x >= imageCorner.getX() && p.x <= imageCorner.getX() + InputHolder.WIDTH;
            cond2 = p.y >= imageCorner.getY() && p.y <= imageCorner.getY() + InputHolder.WIDTH;
        } else if (object instanceof OutputHolder){
            cond1 = p.x >= imageCorner.getX() && p.x <= imageCorner.getX() + OutputHolder.WIDTH;
            cond2 = p.y >= imageCorner.getY() && p.y <= imageCorner.getY() + OutputHolder.WIDTH;
        }else {
            cond1 = p.x >= imageCorner.getX() && p.x <= imageCorner.getX() + Gate.WIDTH;
            cond2 = p.y >= imageCorner.getY() && p.y <= imageCorner.getY() + Gate.WIDTH;
        }
        return cond1 && cond2;

    }

    //returns true if the point is around signal receiver
    public boolean aroundReceiver(Point p){
        if(signalsReceiverPoint == null){
            return false;
        }
        double dx = signalsReceiverPoint.getX() - p.getX();
        double dy = signalsReceiverPoint.getY() - p.getY();
        double d = Math.sqrt((dx*dx + dy*dy));
        return d <= SIGNAL_OFFSET;
    }

    //returns true if the point is around signal sender
    public boolean aroundSender(Point p){
        if(signalSenderPoint == null){
            return false;
        }
        double dx = signalSenderPoint.getX() - p.getX();
        double dy = signalSenderPoint.getY() - p.getY();
        double d = Math.sqrt((dx*dx + dy*dy));
        return d <= SIGNAL_OFFSET;
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

    public ArrayList<DraggableImage> getReceivers() {
        if(this.object instanceof SignalSender){
            ArrayList<SignalReceiver> receivers = ((SignalSender) this.object).getReceivers();

            ArrayList<DraggableImage> rsf = new ArrayList<>();
            for(SignalReceiver receiver: receivers){
                if(receiver instanceof AppObject){
                    rsf.add(((AppObject) receiver).getDraggableImage());
                }
            }
            return rsf;
        } else {
            return new ArrayList<>();
        }
    }
}
