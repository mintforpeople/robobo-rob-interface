/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.rob.BumpStatus.BumpStatusId;

public class BumpStatus extends RobDeviceStatus<BumpStatusId> {

    private short distance;

    public BumpStatus(BumpStatusId id) {
        super(id);
    }

    public short getDistance() {
        return distance;
    }

    public void setDistance(short distance) {
        this.distance = distance;
    }
    
    public static  enum BumpStatusId{
        Bumb1, Bumb2, Bumb3, Bumb4;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BumpStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", distance=");
        builder.append(distance);
        builder.append("]");
        return builder.toString();
    }

}
