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

package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

import static com.mytechia.robobo.rob.comm.MessageType.MovePanTiltMessage;

/**
 *  Implementation for MovePanTiltMessage
 *
 * Created by Victor Sonora Pombo.
 */
public class OldMovePanTiltMessage extends RoboCommand {

    private byte panTilt;

    private short angVel;

    private int angle;

    private int time;


    public OldMovePanTiltMessage(
            byte panTilt,
            short angVel,
            int angle,
            int time) {

        super();
        this.setCommandType(MovePanTiltMessage.commandType);
        this.panTilt = panTilt;
        this.angVel = angVel;
        this.angle = angle;
        this.time = time;

    }


    public OldMovePanTiltMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);

    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.panTilt, "panTilt");

        messageCoder.writeShort(this.angVel, "angVel");

        messageCoder.writeInt(this.angle, "angle");

        messageCoder.writeInt(this.time, "time");

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.panTilt = messageDecoder.readByte("panTilt");

        this.angVel = messageDecoder.readShort("angVel");

        this.angle = messageDecoder.readInt("angle");

        this.time = messageDecoder.readInt("time");

        return messageDecoder.getArrayIndex();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OldMovePanTiltMessage that = (OldMovePanTiltMessage) o;

        if (panTilt != that.panTilt) return false;
        if (Double.compare(that.angVel, angVel) != 0) return false;
        if (Double.compare(that.angle, angle) != 0) return false;
        return time == that.time;

    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) panTilt;
        temp = Double.doubleToLongBits(angVel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }


}
