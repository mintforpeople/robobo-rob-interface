/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
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
    RobotSetLEDsModeMessage((byte)4),
    MoveMTMessage((byte)5),
    MovePanTiltMessage((byte)6),
    ResetPanTiltOffsetMessage((byte)7);

    public final byte commandType;

    MessageType(byte code){
        this.commandType = code;
    }

}
