/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.rob.MotorStatus.MotorStatusId;



public class MotorStatus extends RobDeviceStatus<MotorStatusId> {

    private int angularVelocity;

    private int variationAngle;

    private int voltage;
    
    

    public MotorStatus(MotorStatusId id) {
        super(id);

    }

    public int getAngularVelocity() {
        return angularVelocity;
    }

    public int getVariationAngle() {
        return variationAngle;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setAngularVelocity(int angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setVariationAngle(int variationAngle) {
        this.variationAngle = variationAngle;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }
    
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MotorStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", angularVelocity=");
        builder.append(angularVelocity);
        builder.append(", variationAngle=");
        builder.append(variationAngle);
        builder.append(", voltage=");
        builder.append(voltage);
        builder.append("]");
        return builder.toString();
    }

    public static enum MotorStatusId{
        Left,Right,Pan,Tilt;
    }

}
