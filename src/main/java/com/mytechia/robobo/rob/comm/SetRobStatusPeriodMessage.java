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

import com.mytechia.commons.framework.simplemessageprotocol.Endianness;
import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import static com.mytechia.robobo.rob.comm.MessageType.SetRobStatusPeriodMessage;

/**
 *  Implementation for SetRobStatusPeriodMessage.
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class SetRobStatusPeriodMessage extends RoboCommand {

    private int period;


    public SetRobStatusPeriodMessage(int period) {
        super();
        this.setCommandType(SetRobStatusPeriodMessage.commandType);
        this.period = period;
    }


    public SetRobStatusPeriodMessage(byte [] messageData) throws MessageFormatException {
        super(messageData);
    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeInt(this.period, "period");

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.period = messageDecoder.readInt("period");

        return messageDecoder.getArrayIndex();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetRobStatusPeriodMessage that = (SetRobStatusPeriodMessage) o;

        return period == that.period;

    }

    @Override
    public int hashCode() {
        return period;
    }


}
