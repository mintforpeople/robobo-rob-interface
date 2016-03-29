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

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

import static com.mytechia.robobo.rob.comm.MessageType.RobotSetLEDsModeMessage;

/**
 *  Implementation for SetLEDsModeMessage
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class RobSetLEDsModeMessage  extends RoboCommand {


    private byte mode;


    public RobSetLEDsModeMessage(byte mode) {
        super();
        this.setCommandType(RobotSetLEDsModeMessage.commandType);
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


