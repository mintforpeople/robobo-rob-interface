/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gómez <julio.gomez@mytechia.com>
 *
 *   This file is part of Robobo ROB Interface Library.
 *
 *   Robobo ROB Interface Library is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Robobo ROB Interface Library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Robobo ROB Interface Library.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

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
        Pan,Tilt,Left,Right;
    }

}
