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

    private final List<IRobStatusListener> robStatusListeners = new ArrayList<>();
    

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
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusMotorsMT(left, right);
        });
    }

    void fireStatusMotorPan(MotorStatus status) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusMotorPan(status);
        });
    }

    void fireStatusMotorTilt(MotorStatus status) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusMotorTilt(status);
        });
    }

    void fireStatusGaps(GapStatus gap1, GapStatus gap2) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusGaps(gap1, gap2);
        });
    }

    void fireStatusFalls(FallStatus fall1, FallStatus fall2) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusFalls(fall1, fall2);
        });
    }

    void fireStatusBumps(BumpStatus bump1, BumpStatus bump2) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusBumps(bump1, bump2);
        });
    }

    void fireStatusObstacle(ObstacleSensorStatus obs1, ObstacleSensorStatus obs2) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusObstacle(obs1, obs2);
        });
    }

    void fireStatusBattery(BatteryStatus battery) {
        robStatusListeners.stream().forEach((robStatusListener) -> {
            robStatusListener.statusBattery(battery);
        });
    }

}
