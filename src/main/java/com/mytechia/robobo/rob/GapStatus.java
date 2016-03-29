/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.rob.GapStatus.GapStatusId;

public class GapStatus extends RobDeviceStatus<GapStatusId> {

    private boolean gap;

    public GapStatus(GapStatusId id) {
        super(id);
    }

    public boolean isGap() {
        return gap;
    }

    void setGap(boolean gap) {
        this.gap = gap;
    }
    
    public static enum GapStatusId{
        Gap1, Gap2, Gap3, Gap4, Gap5, Gap6, Gap7, Gap8;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GapStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", gap=");
        builder.append(gap);
        builder.append("]");
        return builder.toString();
    }

}
