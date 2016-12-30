/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2016 Luis Felipe Llamas Luaces <luis.llamas@mytechia.com>
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

import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.robobo.rob.StopWarningType;

/**
 * Implementation for StopWarningMessage.
 *
 * Created by Luis Felipe Llamas Luaces.
 */
public class StopWarningMessage extends RoboCommand {
    private static final String WARNING_TYPE = "warningType";
    private static final String WARNING_DETAILS = "warningDetails";


    private StopWarningType message;
    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();
        byte type;
        byte details;
        type = messageDecoder.readByte(WARNING_TYPE);
        details = messageDecoder.readByte(WARNING_DETAILS);
        this.message = StopWarningType.toStopWarningType(type,details);

        return 0;
    }

    public StopWarningType getMessage() {
        return message;
    }

    public void setMessage(StopWarningType message) {
        this.message = message;
    }
}
