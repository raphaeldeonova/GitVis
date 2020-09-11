package com.deonova.ui;

import com.deonova.model.AppObject;
import com.deonova.model.InputHolder;
import com.deonova.model.ObjectManager;
import com.deonova.model.SignalReceiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DragPanel extends JPanel {
    private ObjectManager objectManager;
    private DraggableImage currImage;
    private boolean isPoking;
    private boolean isDragging;

    public DragPanel(){
        objectManager = new ObjectManager();
        isPoking = false;
        isDragging = false;
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    public void setPoking(boolean bool){
        isPoking = bool;
    }

    public void addObject(AppObject object){
        this.objectManager.add(object);
        System.out.println();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        objectManager.update();

        for(DraggableImage image: objectManager.getImages()){

            image.getImg().paintIcon(this, g,
                    (int)image.getImageCorner().getX(),
                    (int)image.getImageCorner().getY());

            if(image.getReceivers().size() != 0){
                for(DraggableImage receiver: image.getReceivers()){
                    g.drawLine(
                            (int) image.getSignalSenderPoint().getX(),
                            (int) image.getSignalSenderPoint().getY(),
                            (int) receiver.getSignalsReceiverPoint().getX(),
                            (int) receiver.getSignalsReceiverPoint().getY()
                    );
                }
            }

        }
        repaint();
    }

    private class ClickListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            if(!isPoking) {
                for (DraggableImage image : objectManager.getImages()) {
                    if(image.aroundSender(e.getPoint())){
                        currImage = image;
                        isDragging = true;
                    } else if (image.contains(e.getPoint())) {
                        currImage = image;
                        currImage.setPrevPt(e.getPoint());
                    } else if (image.aroundReceiver(e.getPoint())){
                        for(DraggableImage potentialSender: objectManager.getImages()){
                            if(objectManager.isConnectedTo(potentialSender, image)){
                                objectManager.disconnectObject(potentialSender, image);
                                currImage = potentialSender;
                                isDragging = true;
                                break;
                            }
                        }

                    }
                }
            } else {
                for (DraggableImage image : objectManager.getImages()) {
                    if (image.contains(e.getPoint())) {
                        AppObject object = objectManager.getObject(image);
                        if(object instanceof InputHolder){
                            InputHolder input = (InputHolder) object;
                            input.poke();
                            image.setImg(input.getImage());
                            repaint();
                            break;
                        }
                    }
                }
            }
        }

        private int count = 0;
        @Override
        public void mouseReleased(MouseEvent e) {
            if(isDragging){
                for(DraggableImage image: objectManager.getImages()){
                    if(image.aroundReceiver(e.getPoint())){
                        objectManager.connectObject(currImage, image);
                        System.out.println("Drawing line from " + currImage.getSignalSenderPoint() + "to " + image.getSignalsReceiverPoint());
                    }
                }
            } else{
                count++;
            }
            isDragging = false;
            currImage = null;
            if(count == 6){
                System.out.println();
            }
        }
    }

    private class DragListener extends MouseMotionAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            if(currImage != null) {
                if (!isDragging) {
                    Point currentPoint = e.getPoint();
                    currImage.getImageCorner().translate(
                            (int) (currentPoint.getX() - currImage.getPrevPt().getX()),
                            (int) (currentPoint.getY() - currImage.getPrevPt().getY())
                    );
                    currImage.updateSignalPoints();
                    currImage.setPrevPt(currentPoint);
                } else {
                    Graphics g = getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    g2.drawLine(
                            (int) currImage.getSignalSenderPoint().getX(),
                            (int) currImage.getSignalSenderPoint().getY(),
                            e.getX(),
                            e.getY()
                    );
                }
                repaint();
            }
        }
    }
}
