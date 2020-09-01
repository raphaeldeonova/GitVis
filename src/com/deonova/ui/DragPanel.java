package com.deonova.ui;

import com.deonova.model.Gate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class DragPanel extends JPanel {
    private ArrayList<DraggableImage> images;
    private DraggableImage currImage;

    public DragPanel(){
        images = new ArrayList<>();
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    public void addImage(Image img){
        images.add(new DraggableImage(img));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(DraggableImage image: images){
            image.getImg().paintIcon(this, g,
                    (int)image.getImageCorner().getX(),
                    (int)image.getImageCorner().getY());
        }
        repaint();
    }

    private class ClickListener extends MouseAdapter{

        //TODO: implement the boundary check (contains...) so that it accurately checks every image
        @Override
        public void mousePressed(MouseEvent e) {
            for(DraggableImage image: images){
                System.out.println(image.contains(e.getPoint()));
                if(image.contains(e.getPoint())){
                    currImage = image;
                    currImage.setPrevPt(e.getPoint());
                }
            }
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

    private class DraggableImage{
        private ImageIcon img;
        private Point imageCorner;
        private Point prevPt;

        public DraggableImage(Image img) {
            this.img = new ImageIcon(img);
            this.imageCorner = new Point(0,0);
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
