/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.rob.IRSensorStatus.IRSentorStatusId;

public class IRSensorStatus extends RobDeviceStatus<IRSentorStatusId> {

    private short distance;


    public IRSensorStatus(IRSentorStatusId id) {
        super(id);
    }



    public short getDistance() {
        return distance;
    }

    public void setDistance(short distance) {
        this.distance = distance;
    }
    
    public static enum IRSentorStatusId{
        IRSensorStatus1, IRSensorStatus2, IRSensorStatus3, 
        IRSensorStatus4, IRSensorStatus5, IRSensorStatus6, 
        IRSensorStatus7, IRSensorStatus8, IRSensorStatus9;
    }
    
    

}
