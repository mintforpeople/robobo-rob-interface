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
import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

import java.util.Arrays;

/**
 *  Implementation for SetLEDColorMessage.
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class SetLEDColorMessage extends Command {

    private byte ledId;

    private byte[] ledColor;


    public SetLEDColorMessage(
            byte ledId,
            byte[] ledColor) {

        super();
        this.ledId = ledId;
        this.ledColor = ledColor;

    }


    public SetLEDColorMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);

    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.ledId, "ledId");

        messageCoder.writeByteArrayWithSize(this.ledColor, "ledColor");

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.ledId = messageDecoder.readByte("ledId");

        this.ledColor = messageDecoder.readByteArray("ledColor");

        return messageDecoder.getArrayIndex();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetLEDColorMessage that = (SetLEDColorMessage) o;

        if (ledId != that.ledId) return false;
        return Arrays.equals(ledColor, that.ledColor);

    }

    @Override
    public int hashCode() {
        int result = (int) ledId;
        result = 31 * result + Arrays.hashCode(ledColor);
        return result;
    }


}
