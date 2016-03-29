/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

public class BatteryStatus extends RobDeviceStatus {

    private int battery;

    private boolean charging;

    public BatteryStatus() {
        super(BatteryStatusId.BaterryStatus1);
    }
    
    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BatteryStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", battery=");
        builder.append(battery);
        builder.append(", charging=");
        builder.append(charging);
        builder.append("]");
        return builder.toString();
    }
    
    public static enum BatteryStatusId{
        BaterryStatus1;
    }

}
