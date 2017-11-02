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

public class MovePanMessage extends RoboCommand {


    private static final String PAN_ANGLE = "panAngle";

    private static final String PAN_ANGULAR_VELOCITY = "panAngularVelocity";

    private int panAngularVelocity;

    private int panAngle;


    public MovePanMessage(int panAngularVelocity, int panAngle) {
        super();
        super.setCommandType(MessageType.MovePanMessage.commandType);
        this.panAngularVelocity = panAngularVelocity;
        this.panAngle = panAngle;

    }

    public MovePanMessage(byte[] message) throws MessageFormatException {
        super(message);
        super.setCommandType(MessageType.MovePanMessage.commandType);
    }

    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeUShort(panAngularVelocity, PAN_ANGULAR_VELOCITY);

        messageCoder.writeInt(panAngle, PAN_ANGLE);

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] binaryMessage, int arg1) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.panAngularVelocity = messageDecoder.readUShort(PAN_ANGULAR_VELOCITY);

        this.panAngle = messageDecoder.readInt(PAN_ANGLE);

        return this.getMessageDecoder().getArrayIndex();
    }

    public int getPanAngularVelocity() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovePanMessage that = (MovePanMessage) o;

        if (panAngularVelocity != that.panAngularVelocity) return false;
        return panAngle == that.panAngle;

    }

    @Override
    public int hashCode() {
        int result = panAngularVelocity;
        result = 31 * result + panAngle;
        return result;
    }
}
