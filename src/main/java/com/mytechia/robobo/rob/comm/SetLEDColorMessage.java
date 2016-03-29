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

import java.util.Arrays;

import static com.mytechia.robobo.rob.comm.MessageType.SetLEDColorMessage;

/**
 *  Implementation for SetLEDColorMessage.
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class SetLEDColorMessage extends RoboCommand {

    public static final String BLUE = "blue";
    public static final String GREEN = "green";
    public static final String RED = "red";
    public static final String LED_ID = "ledId";
    private byte ledId;

    private short red;

    private short green;

    private short blue;


    public SetLEDColorMessage(
            byte ledId,
            short red, short green, short blue) {

        super();
        this.setCommandType(SetLEDColorMessage.commandType);
        this.ledId = ledId;
        this.red= red;
        this.green= green;
        this.blue= blue;

    }


    public SetLEDColorMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);

    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.ledId, LED_ID);

        messageCoder.writeShort(red, RED);

        messageCoder.writeShort(green, GREEN);

        messageCoder.writeShort(blue, BLUE);


        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();


        this.ledId = messageDecoder.readByte(LED_ID);

        red= messageDecoder.readShort(RED);

        green= messageDecoder.readShort(GREEN);

        blue= messageDecoder.readShort(BLUE);

        return messageDecoder.getArrayIndex();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetLEDColorMessage that = (SetLEDColorMessage) o;

        if (ledId != that.ledId) return false;
        if (red != that.red) return false;
        if (green != that.green) return false;
        return blue == that.blue;

    }

    @Override
    public int hashCode() {
        int result = (int) ledId;
        result = 31 * result + (int) red;
        result = 31 * result + (int) green;
        result = 31 * result + (int) blue;
        return result;
    }
}
