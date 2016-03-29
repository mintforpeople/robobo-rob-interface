/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

public interface IRobStatusListener {

    void statusMotorsMT(MotorStatus left, MotorStatus right);

    void statusMotorPan(MotorStatus status);

    void statusMotorTilt(MotorStatus status);

    void statusGaps(GapStatus gap1, GapStatus gap2);

    void statusFalls(FallStatus fall1, FallStatus fall2);

    void statusBumps(BumpStatus bump1, BumpStatus bump2);

    void statusObstacle(ObstacleSensorStatus obs1, ObstacleSensorStatus obs2);

    void statusBattery(BatteryStatus battery);

}
