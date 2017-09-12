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

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

/**
 * Created by victorsonorapombo.
 */
public class ChangeNameMessage extends RoboCommand {

    private String name;


    public ChangeNameMessage(String name) {

        super();
        this.setCommandType(MessageType.ChangeBtNameMessage.commandType);
        this.name = name;

    }


    public ChangeNameMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);
        this.setCommandType(MessageType.ChangeBtNameMessage.commandType);

    }


    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeString(name, "name");

        return messageCoder.getBytes();

    }


    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.name = messageDecoder.readString("name");

        return this.getMessageDecoder().getArrayIndex();

    }


    public String getName() {

        return this.name;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeNameMessage that = (ChangeNameMessage) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
