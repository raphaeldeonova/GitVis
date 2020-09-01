package com.deonova.model;

import java.util.ArrayList;
import java.util.List;

public class GatesManager {
    private List<Gate> gateList;

    public GatesManager() {
        this.gateList = new ArrayList<>();
    }

    public void addGate(Gate gate){
        this.gateList.add(gate);
    }
}
