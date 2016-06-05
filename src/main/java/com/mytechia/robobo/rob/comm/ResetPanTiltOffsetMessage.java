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
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

import static com.mytechia.robobo.rob.comm.MessageType.ResetPanTiltOffsetMessage;

/**
 *
 * Created by Victor Sonora Pombo.
 */
public class ResetPanTiltOffsetMessage extends RoboCommand {


    public ResetPanTiltOffsetMessage() {

        super();
        this.setCommandType(ResetPanTiltOffsetMessage.commandType);

    }


    public ResetPanTiltOffsetMessage(byte [] messageData) throws MessageFormatException {
        super(messageData);
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        return 0;
    }
    
    
    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte((byte)0, "NONE");

        return messageCoder.getBytes();

    }


}
