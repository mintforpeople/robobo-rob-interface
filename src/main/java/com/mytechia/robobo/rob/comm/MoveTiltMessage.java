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

public class MoveTiltMessage extends RoboCommand {

    private static final String TILT_ANGLE = "tiltAngle";

    private static final String TILT_ANGULAR_VELOCITY = "tiltAngularVelocity";


    private int tiltAngularVelocity;

    private int tiltAngle;

    public MoveTiltMessage(int tiltAngularVelocity, int tiltAngle) {
        super();
        super.setCommandType(MessageType.MoveTiltMessage.commandType);
        this.tiltAngularVelocity = tiltAngularVelocity;
        this.tiltAngle = tiltAngle;
    }

    public MoveTiltMessage(byte[] message) throws MessageFormatException {
        super(message);
        super.setCommandType(MessageType.MoveTiltMessage.commandType);
    }

    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeUShort(tiltAngularVelocity, TILT_ANGULAR_VELOCITY);

        messageCoder.writeInt(tiltAngle, TILT_ANGLE);

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] binaryMessage, int arg1) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.tiltAngularVelocity = messageDecoder.readUShort(TILT_ANGULAR_VELOCITY);

        this.tiltAngle = messageDecoder.readInt(TILT_ANGLE);

        return this.getMessageDecoder().getArrayIndex();
    }


    public int getTiltAngularVelocity() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveTiltMessage that = (MoveTiltMessage) o;

        if (tiltAngularVelocity != that.tiltAngularVelocity) return false;
        return tiltAngle == that.tiltAngle;

    }

    @Override
    public int hashCode() {
        int result = tiltAngularVelocity;
        result = 31 * result + tiltAngle;
        return result;
    }
}
