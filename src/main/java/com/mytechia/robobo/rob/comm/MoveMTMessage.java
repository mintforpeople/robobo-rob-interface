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

import static com.mytechia.robobo.rob.comm.MessageType.MoveMTMessage;

/**
 *  Implementation for MoveMTMessage
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MoveMTMessage extends RoboCommand {

    private int angVel1;

    private int angle1;

    private int angVel2;

    private int angle2;

    private long time;


    public MoveMTMessage(
            int angVel1,
            int angle1,
            int angVel2,
            int angle2,
            long time) {

        super();
        this.setCommandType(MoveMTMessage.commandType);
        this.angVel1 = angVel1;
        this.angle1 = angle1;
        this.angVel2 = angVel2;
        this.angle2 = angle2;
        this.time = time;

    }


    public MoveMTMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);

    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeInt(this.angVel1, "angVel1");

        messageCoder.writeInt(this.angle1, "angle1");

        messageCoder.writeInt(this.angVel2, "angVel2");

        messageCoder.writeInt(this.angle2, "angle2");

        messageCoder.writeInt((int) this.time, "time");

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.angVel1 = messageDecoder.readInt("angVel1");

        this.angle1 = messageDecoder.readInt("angle1");

        this.angVel2 = messageDecoder.readInt("angVel2");

        this.angle2 = messageDecoder.readInt("angle2");

        this.time = messageDecoder.readInt("time");

        return messageDecoder.getArrayIndex();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveMTMessage that = (MoveMTMessage) o;

        if (Double.compare(that.angVel1, angVel1) != 0) return false;
        if (Double.compare(that.angle1, angle1) != 0) return false;
        if (Double.compare(that.angVel2, angVel2) != 0) return false;
        if (Double.compare(that.angle2, angle2) != 0) return false;
        return time == that.time;

    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(angVel1);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angle1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angVel2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angle2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }
}
