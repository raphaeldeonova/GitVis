package com.deonova.model;


import java.util.ArrayList;

public interface SignalSender {
    void addReceiver(SignalReceiver receiver);
    void notifyReceivers();
    ArrayList<SignalReceiver> getReceivers();
}
