package com.deonova.ui;

import com.deonova.model.AndGate;
import com.deonova.model.Gate;
import com.deonova.model.GatesManager;
import com.deonova.model.OrGate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private GatesManager gateManager = new GatesManager();

    private JFrame frame = new JFrame("GitVis");
    private JPanel rootPanel = new JPanel();
    private JButton andButton = new JButton("AndGate");
    private JButton orButton = new JButton("OrGate");

    private DragPanel gatesPanel = new DragPanel();

    public GUI() {
        initializeGraphics();
        initializeActionListeners();
        frame.setVisible(true);
    }

    private void initializeActionListeners(){
        andButton.addActionListener(e -> {
            AndGate gate = new AndGate();
            gateManager.addGate(gate);
            gatesPanel.addImage(gate.getImage());
        });

        orButton.addActionListener(e -> {
            OrGate gate = new OrGate();
            gateManager.addGate(gate);
            gatesPanel.addImage(gate.getImage());
        });
    }

    private void initializeGraphics() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        rootPanel.setLayout(null);
        frame.add(rootPanel);
        rootPanel.add(gatesPanel);

        gatesPanel.setBounds(170,50,600,500);
        gatesPanel.setBackground(new Color(229, 229, 229));

        rootPanel.setBackground(new Color(171, 203, 252));

        andButton.setBounds(20,50,100,25);

        orButton.setBounds(20,100,100,25);
        rootPanel.add(andButton);
        rootPanel.add(orButton);
    }
}
