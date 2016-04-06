/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.rob.ObstacleSensorStatus.ObstacleSensorStatusId;

public class ObstacleSensorStatus extends RobDeviceStatus<ObstacleSensorStatusId> {

    private short distance;

    public ObstacleSensorStatus(ObstacleSensorStatusId id) {
        super(id);

    }

    public short getDistance() {
        return distance;
    }

    public void setDistance(short distance) {
        this.distance = distance;
    }
    
    public enum ObstacleSensorStatusId{
        Obstable1,Obstable2,Obstable3,Obstable4, Obstable5,Obstable6,Obstable7;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ObstacleSensorStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", distance=");
        builder.append(distance);
        builder.append("]");
        return builder.toString();
    }

}
