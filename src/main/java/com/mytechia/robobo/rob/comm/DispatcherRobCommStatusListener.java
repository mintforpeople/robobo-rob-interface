/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage instances of DispatcherRobStatusListener. And it
 * implements methods to fire events of DispatcherRobStatusListener.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class DispatcherRobCommStatusListener {

    private final List<IRobCommStatusListener> robCommStatusListeners = new ArrayList<IRobCommStatusListener>();
    

    public void subscribeToRobCommStatus(IRobCommStatusListener robCommStatusListener) {
        if (robCommStatusListener == null) {
            return;
        }

        this.robCommStatusListeners.add(robCommStatusListener);
    }

    void unsubscribeFromRobCommStatus(IRobCommStatusListener robCommStatusListener) {
        this.robCommStatusListeners.remove(robCommStatusListener);
    }
    

    void fireReceivedStatusMotorsMT(RobStatusMessage rs) {
        
        for (IRobCommStatusListener robCommStatusListener : robCommStatusListeners) {
            robCommStatusListener.robStatus(rs);
        }
        
    }




}
