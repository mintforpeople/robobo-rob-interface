/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import java.util.Collection;

public interface IRobStatusListener {

    void statusMotorsMT(MotorStatus left, MotorStatus right);

    void statusMotorPan(MotorStatus status);

    void statusMotorTilt(MotorStatus status);

    void statusGaps(Collection<GapStatus> gaps);

    void statusFalls(Collection<FallStatus> fall);
    
    void statusIRSensorStatus(Collection<IRSensorStatus> irSensorStatus);

    void statusBattery(BatteryStatus battery);

}
