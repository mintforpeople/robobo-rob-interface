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

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

public class MovePanTiltMessage extends RoboCommand {

    private static final String TILT_ANGLE = "tiltAngle";

    private static final String TILT_ANGULAR_VELOCITY = "tiltAngularVelocity";

    private static final String PAN_ANGLE = "panAngle";

    private static final String PAN_ANGULAR_VELOCITY = "panAngularVelocity";

    private short panAngularVelocity;

    private int panAngle;

    private short tiltAngularVelocity;

    private int tiltAngle;

    public MovePanTiltMessage(short panAngularVelocity, int panAngle, short tiltAngularVelocity, int tiltAngle) {
        super();
        super.setCommandType(MessageType.MovePanTiltMessage.commandType);
        this.panAngularVelocity = panAngularVelocity;
        this.panAngle = panAngle;
        this.tiltAngularVelocity = tiltAngularVelocity;
        this.tiltAngle = tiltAngle;
    }

    public MovePanTiltMessage(byte[] message) throws MessageFormatException {
        super(message);
        super.setCommandType(MessageType.MovePanTiltMessage.commandType);
    }

    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeShort(panAngularVelocity, PAN_ANGULAR_VELOCITY);

        messageCoder.writeInt(panAngle, PAN_ANGLE);

        messageCoder.writeShort(tiltAngularVelocity, TILT_ANGULAR_VELOCITY);

        messageCoder.writeInt(tiltAngle, TILT_ANGLE);

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] binaryMessage, int arg1) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.panAngularVelocity = messageDecoder.readShort(PAN_ANGULAR_VELOCITY);

        this.panAngle = messageDecoder.readInt(PAN_ANGLE);

        this.tiltAngularVelocity = messageDecoder.readShort(TILT_ANGULAR_VELOCITY);

        this.tiltAngle = messageDecoder.readInt(TILT_ANGLE);

        return this.getMessageDecoder().getArrayIndex();
    }

    public short getPanAngularVelocity() {
        return panAngularVelocity;
    }

    public void setPanAngularVelocity(short panAngularVelocity) {
        this.panAngularVelocity = panAngularVelocity;
    }

    public int getPanAngle() {
        return panAngle;
    }

    public void setPanAngle(int panAngle) {
        this.panAngle = panAngle;
    }

    public short getTiltAngularVelocity() {
        return tiltAngularVelocity;
    }

    public void setTiltAngularVelocity(short tiltAngularVelocity) {
        this.tiltAngularVelocity = tiltAngularVelocity;
    }

    public int getTiltAngle() {
        return tiltAngle;
    }

    public void setTiltAngle(int tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

}
