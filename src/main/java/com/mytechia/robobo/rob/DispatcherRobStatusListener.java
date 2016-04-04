/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage instances of DispatcherRobStatusListener. And it
 * implements methods to fire events of DispatcherRobStatusListener.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class DispatcherRobStatusListener {

    private final List<IRobStatusListener> robStatusListeners = new ArrayList<IRobStatusListener>();
    

    public void subscribetoContentChanges(IRobStatusListener robStatusListener) {
        if (robStatusListener == null) {
            return;
        }

        this.robStatusListeners.add(robStatusListener);
    }

    void unsubscribeFromContentChanges(IRobStatusListener contentChangesListener) {
        this.robStatusListeners.remove(contentChangesListener);
    }
    

    void fireStatusMotorsMT(MotorStatus left, MotorStatus right) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            
            robStatusListener.statusMotorsMT(left, right);
        }
    }

    void fireStatusMotorPan(MotorStatus status) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusMotorPan(status);
            
        }
    }

    void fireStatusMotorTilt(MotorStatus status) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusMotorTilt(status);
            
        }
    }

    void fireStatusGaps(GapStatus gap1, GapStatus gap2) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusGaps(gap1, gap2);
            
        }
    }

    void fireStatusFalls(FallStatus fall1, FallStatus fall2) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusFalls(fall1, fall2);
            
        }
    }

    void fireStatusBumps(BumpStatus bump1, BumpStatus bump2) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusBumps(bump1, bump2);
            
        }
    }

    void fireStatusObstacle(ObstacleSensorStatus obs1, ObstacleSensorStatus obs2) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusObstacle(obs1, obs2);
            
        }
    }

    void fireStatusBattery(BatteryStatus battery) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusBattery(battery);
            
        }
    }

}
