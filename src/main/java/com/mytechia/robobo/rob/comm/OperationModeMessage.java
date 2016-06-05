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
import static com.mytechia.robobo.rob.comm.MessageType.OperationModeMessage;

import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

public class OperationModeMessage extends RoboCommand {

    private static final String COMMAND_CODE = "commandCode";

    private byte commandCode;

    public OperationModeMessage(byte commandCode) {
        this.setCommandType(OperationModeMessage.commandType);
        this.commandCode = commandCode;
    }

    public OperationModeMessage(byte[] message) throws MessageFormatException {
        super(message);
        this.setCommandType(OperationModeMessage.commandType);
    }

    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(commandCode, COMMAND_CODE);

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] binaryMessage, int arg1) throws MessageFormatException {

        MessageDecoder decoder = this.getMessageDecoder();

        this.commandCode = decoder.readByte(COMMAND_CODE);

        return decoder.getArrayIndex();
    }

    public byte getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(byte commandCode) {
        this.commandCode = commandCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + commandCode;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OperationModeMessage other = (OperationModeMessage) obj;
        if (commandCode != other.commandCode) {
            return false;
        }
        return true;
    }

}
