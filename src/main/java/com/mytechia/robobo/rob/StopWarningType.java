/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gómez <julio.gomez@mytechia.com>
 *   Copyright 2016 Gervasio Varela <gervasio.varela@mytechia.com>
 *   Copyright 2016 Luis Llamas <luis.llamas@mytechia.com>
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

/**
 * Enumeration of StopWarning types.
 *
 * @author Luis Llamas
 */
public enum StopWarningType {
    MOTOR_WARNING_M1_BLOCKED((byte) 1,(byte) 1),
    MOTOR_WARNING_M2_BLOCKED((byte) 1,(byte) 2),
    MOTOR_WARNING_M3_BLOCKED((byte) 1, (byte) 4),
    MOTOR_WARNING_M4_BLOCKED((byte) 1, (byte) 6),
    MOTOR_WARNING_FALL((byte) 1, (byte) 8),
    IR_WARNING_S1((byte) 2,(byte) 1),
    IR_WARNING_S2((byte) 2,(byte) 2),
    IR_WARNING_S3((byte) 2,(byte) 4),
    IR_WARNING_S4((byte) 2,(byte) 6),
    IR_WARNING_S5((byte) 2,(byte) 8),
    IR_WARNING_S6((byte) 2,(byte) 10),
    IR_WARNING_S7((byte) 2,(byte) 12),
    IR_WARNING_S8((byte) 2,(byte) 14),
    IR_WARNING_S9((byte) 2,(byte) 16),
    BATTERY_WARNING_CONNECTED((byte) 4,(byte) 1),
    BATTERY_WARNING_DISCONNECTED((byte) 4,(byte) 2),
    BATTERY_WARNING_LOW((byte) 4,(byte) 4),
    RANGE_WARNING_PAN_SPEED((byte) 8,(byte) 1),
    RANGE_WARNING_PAN_ANGLE((byte) 8,(byte) 2),
    RANGE_WARNING_TILT_SPEED((byte) 8,(byte) 4),
    RANGE_WARNING_TILT_ANGLE((byte) 8,(byte) 6),
    RANGE_WARNING_WHEELS_SPEED((byte) 8,(byte) 8),
    IR_CONFIG_WARNING_OK((byte) 10,(byte) 1),
    IR_CONFIG_WARNING_NOT_CONFIGURED((byte) 10,(byte) 2);

    private byte type;
    private byte details;

    StopWarningType(byte type, byte details){
        this.type = type;
        this.details = details;
    }

    public byte getWarningType(){ return  this.type;}
    public byte getWarningDetails(){ return  this.details;}


    public static StopWarningType toStopWarningType(byte type, byte details){
        for (StopWarningType stopWarningType: StopWarningType.values()){
            if((stopWarningType.getWarningType() == type)&&(stopWarningType.getWarningDetails()== details)){
                return stopWarningType;
            }
        }
        return null;
    }

}
