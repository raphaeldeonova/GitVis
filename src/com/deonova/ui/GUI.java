package com.deonova.ui;

import com.deonova.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class GUI {

    private JFrame frame = new JFrame("GitVis");
    private JPanel rootPanel = new JPanel();
    private JButton andButton = new JButton("AndGate");
    private JButton orButton = new JButton("OrGate");
    private JButton inputButton = new JButton("Input");
    private JCheckBox pokeTool = new JCheckBox("Poke");

    private DragPanel gatesPanel = new DragPanel();

    public GUI() {
        initializeGraphics();
        initializeActionListeners();
        frame.setVisible(true);
    }

    private void initializeActionListeners(){
        andButton.addActionListener(e -> {
            Gate gate = new AndGate();
            gatesPanel.addObject(gate);
        });

        orButton.addActionListener(e -> {
            Gate gate = new OrGate();
            gatesPanel.addObject(gate);
        });

        inputButton.addActionListener(e -> {
            InputHolder input = new InputHolder();
            gatesPanel.addObject(input);
        });

        pokeTool.addItemListener(e -> {
            gatesPanel.setPoking(e.getStateChange() == ItemEvent.SELECTED);
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

        inputButton.setBounds(20, 150, 100,25);
        rootPanel.add(inputButton);

        pokeTool.setBounds(20,200, 100, 25);
        rootPanel.add(pokeTool);
    }
}
