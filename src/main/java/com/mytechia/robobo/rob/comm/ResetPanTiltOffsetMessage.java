/**
 * *****************************************************************************
 * <p>
 * Copyright (C) 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2016 Victor Sonora Pombo <victor.pombo@mytechia.com>
 * <p>
 * This file is part of robobo-rob-interface.
 * ****************************************************************************
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

/**
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class ResetPanTiltOffsetMessage extends Command {


    public ResetPanTiltOffsetMessage() {

        super();
        this.setCommandType((byte)7);

    }


    public ResetPanTiltOffsetMessage(byte [] messageData) throws MessageFormatException {
        super(messageData);
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        return 0;
    }
}
