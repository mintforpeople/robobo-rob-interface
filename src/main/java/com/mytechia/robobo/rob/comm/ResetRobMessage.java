/**
 * *****************************************************************************
 * <p>
 * Copyright (C) 2017 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2017 Victor Sonora Pombo <victor.pombo@mytechia.com>
 * <p>
 * This file is part of Luminare360 Firmware.
 * ****************************************************************************
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;



/**
 *
 * Created by victorsonorapombo.
 */
public class ResetRobMessage extends RoboCommand  {


    public ResetRobMessage() {

        super();
        this.setCommandType(MessageType.ResetRobMessage.commandType);

    }



    public ResetRobMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);
        this.setCommandType(MessageType.ResetRobMessage.commandType);

    }



    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        return new byte[]{};

    }


    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {

        return 0;

    }


}
