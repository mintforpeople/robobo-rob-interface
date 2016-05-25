/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.rob.FallStatus.FallStatusId;

public class FallStatus extends RobDeviceStatus<FallStatusId> {

    private boolean fall;

    
    public FallStatus(FallStatusId id){
        super(id);
    }
    
    

    public boolean isFall() {
        return fall;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }
    
    public static enum FallStatusId{
        Fall1, Fall2, Fall3, Fall4;
    }
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FallStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", fall=");
        builder.append(fall);
        builder.append("]");
        return builder.toString();
    }

}
