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

/**
 *  Implementation for MoveMTMessage
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MoveMTMessage extends Command {

    private double angVel1;

    private double angle1;

    private double angVel2;

    private double angle2;

    private long time;


    public MoveMTMessage(
            double angVel1,
            double angle1,
            double angVel2,
            double angle2,
            long time) {

        super();
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

        messageCoder.writeDouble(this.angVel1, "angVel1");

        messageCoder.writeDouble(this.angle1, "angle1");

        messageCoder.writeDouble(this.angVel2, "angVel2");

        messageCoder.writeDouble(this.angle2, "angle2");

        messageCoder.writeLong(this.time, "time");

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.angVel1 = messageDecoder.readDouble("angVel1");

        this.angle1 = messageDecoder.readDouble("angle1");

        this.angVel2 = messageDecoder.readDouble("angVel2");

        this.angle2 = messageDecoder.readDouble("angle2");

        this.time = messageDecoder.readLong("time");

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
