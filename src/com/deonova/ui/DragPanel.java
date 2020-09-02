package com.deonova.ui;

import com.deonova.model.AppObject;
import com.deonova.model.Gate;
import com.deonova.model.InputHolder;
import com.deonova.model.ObjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DragPanel extends JPanel {
    private ObjectManager objectManager;
    private DraggableImage currImage;
    private boolean isPoking;

    public DragPanel(){
        objectManager = new ObjectManager();
        isPoking = false;
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(DraggableImage image: objectManager.getImages()){
            image.getImg().paintIcon(this, g,
                    (int)image.getImageCorner().getX(),
                    (int)image.getImageCorner().getY());
        }
        repaint();
    }

    private class ClickListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            if(!isPoking) {
                for (DraggableImage image : objectManager.getImages()) {
                    System.out.println(image.contains(e.getPoint()));
                    if (image.contains(e.getPoint())) {
                        currImage = image;
                        currImage.setPrevPt(e.getPoint());
                    }
                }
            } else {
                for (DraggableImage image : objectManager.getImages()) {
                    if (image.contains(e.getPoint())) {
                        AppObject object = objectManager.getObject(image);
                        if(object.getClass() == InputHolder.class){
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

        @Override
        public void mouseReleased(MouseEvent e) {
            currImage = null;
        }
    }

    private class DragListener extends MouseMotionAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            if(currImage != null) {
                Point currentPoint = e.getPoint();
                currImage.getImageCorner().translate(
                        (int) (currentPoint.getX() - currImage.getPrevPt().getX()),
                        (int) (currentPoint.getY() - currImage.getPrevPt().getY())
                );
                currImage.setPrevPt(currentPoint);
                repaint();
            }
        }
    }

    public static class DraggableImage{
        private ImageIcon img;
        private Point imageCorner;
        private Point prevPt;

        public DraggableImage(Image img) {
            this.img = new ImageIcon(img);
            this.imageCorner = new Point(0,0);
        }

        public void setImg(Image img) {
            this.img = new ImageIcon(img);
        }

        //returns true if the point is inside image
        public boolean contains(Point p){
            boolean cond1 = p.x >= imageCorner.getX() && p.x <= imageCorner.getX() + Gate.WIDTH;
            boolean cond2 = p.y >= imageCorner.getY() && p.y <= imageCorner.getY() + Gate.WIDTH;
            return cond1 && cond2;
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
    }
}
