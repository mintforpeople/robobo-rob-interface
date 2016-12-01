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

import static com.mytechia.robobo.rob.comm.MessageType.MoveMTMessage;

/**
 *  Implementation for MoveMTMessage
 *
 * Created by Victor Sonora Pombo.
 */
public class MoveMTMessage extends RoboCommand {

    private byte mode;
    
    private short angVel1;

    private int angle1;

    private short angVel2;

    private int angle2;

    private long time;


    public MoveMTMessage(
            byte mode,
            short angVel1,
            int angle1,
            short angVel2,
            int angle2,
            long time) {

        super();
        this.setCommandType(MoveMTMessage.commandType);
        this.mode = mode;
        this.angVel1 = angVel1;
        this.angle1 = angle1;
        this.angVel2 = angVel2;
        this.angle2 = angle2;
        this.time = time;

    }


    public MoveMTMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);

    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();
        
        messageCoder.writeByte(this.mode, "mode");

        messageCoder.writeInt(this.angle1, "angle1");
        
        messageCoder.writeShort(this.angVel1, "angVel1");
        
        messageCoder.writeInt((int) this.time, "time1");

        messageCoder.writeInt(this.angle2, "angle2");
        
        messageCoder.writeShort(this.angVel2, "angVel2");

        messageCoder.writeInt((int) this.time, "time2");



        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.mode = messageDecoder.readByte("mode");

        this.angle1 = messageDecoder.readInt("angle1");
        
        this.angVel1 = messageDecoder.readShort("angVel1");
        
        this.time = messageDecoder.readInt("time1");
        
        this.angle2 = messageDecoder.readInt("angle2");

        this.angVel2 = messageDecoder.readShort("angVel2");

        this.time = messageDecoder.readInt("time2");

        return messageDecoder.getArrayIndex();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.mode;
        hash = 23 * hash + this.angVel1;
        hash = 23 * hash + this.angle1;
        hash = 23 * hash + this.angVel2;
        hash = 23 * hash + this.angle2;
        hash = 23 * hash + (int) (this.time ^ (this.time >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveMTMessage that = (MoveMTMessage) o;

        if (Byte.compare(that.mode, mode) != 0) return false;
        if (Double.compare(that.angVel1, angVel1) != 0) return false;
        if (Double.compare(that.angle1, angle1) != 0) return false;
        if (Double.compare(that.angVel2, angVel2) != 0) return false;
        if (Double.compare(that.angle2, angle2) != 0) return false;
        return time == that.time;

    }


    
}
