/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2016 Victor Sonora Pombo <victor.pombo@mytechia.com>
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

package com.mytechia.robobo.rob.comm;

/**
 *
 *
 * @author Julio Alberto Gomez Fernandez
 */
public enum MessageType {

    AckMessage((byte)0),
    RobStatusMessage((byte)1),
    SetRobStatusPeriodMessage((byte)2),
    SetLEDColorMessage((byte)3),
    RobSetLEDsModeMessage((byte)4),
    MoveMTMessage((byte)5),
    MovePanMessage((byte)6),
    ResetPanTiltOffsetMessage((byte)7),
    InfraredConfigurationMessage((byte)8),
    OperationModeMessage((byte)9), 
    MaxValueMotors((byte)10),
    StopWarning((byte)11),
    FirmwareVersionMessage((byte)12),
    ResetRobMessage((byte)13),
    MoveTiltMessage((byte)14),
    ChangeBtNameMessage((byte)15),
    TiltCalibrationMessage((byte) 0x10),//Probablemente no se implemente
    ResetEncodersMessage((byte) 0x11);

    public final byte commandType;

    MessageType(byte code){
        this.commandType = code;
    }

}
