package com.deonova.ui;

import javax.swing.*;

public class GUI {
    private JFrame frame = new JFrame("GitVis");
    private JPanel panel = new JPanel();

    public GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.add(panel);
        JLabel testLabel = new JLabel("Testing GUI");
        panel.add(testLabel);

        frame.setVisible(true);
    }
}
