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

import static com.mytechia.robobo.rob.comm.MessageType.RobSetLEDsModeMessage;

/**
 *  Implementation for SetLEDsModeMessage
 *
 * Created by Victor Sonora Pombo.
 */
public class RobSetLEDsModeMessage  extends RoboCommand {


    private byte mode;


    public RobSetLEDsModeMessage(byte mode) {
        super();
        this.setCommandType(RobSetLEDsModeMessage.commandType);
        this.mode = mode;
    }


    public RobSetLEDsModeMessage(byte [] messageData) throws MessageFormatException {
        super(messageData);
    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.mode, "mode");

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.mode = messageDecoder.readByte("mode");

        return messageDecoder.getArrayIndex();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobSetLEDsModeMessage that = (RobSetLEDsModeMessage) o;

        return mode == that.mode;

    }

    @Override
    public int hashCode() {
        return (int) mode;
    }


}


