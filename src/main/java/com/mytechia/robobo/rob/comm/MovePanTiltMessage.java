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
 *  Implementation for MovePanTiltMessage
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MovePanTiltMessage extends Command {

    private byte panTilt;

    private double angVel;

    private double angle;

    private long time;


    public MovePanTiltMessage(
            byte panTilt,
            double angVel,
            double angle,
            long time) {

        super();
        this.setCommandType((byte)6);
        this.panTilt = panTilt;
        this.angVel = angVel;
        this.angle = angle;
        this.time = time;

    }


    public MovePanTiltMessage(byte [] messageData) throws MessageFormatException {

        super(messageData);

    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.panTilt, "panTilt");

        messageCoder.writeDouble(this.angVel, "angVel");

        messageCoder.writeDouble(this.angle, "angle");

        messageCoder.writeLong(this.time, "time");

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.panTilt = messageDecoder.readByte("panTilt");

        this.angVel = messageDecoder.readDouble("angVel");

        this.angle = messageDecoder.readDouble("angle");

        this.time = messageDecoder.readLong("time");

        return messageDecoder.getArrayIndex();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovePanTiltMessage that = (MovePanTiltMessage) o;

        if (panTilt != that.panTilt) return false;
        if (Double.compare(that.angVel, angVel) != 0) return false;
        if (Double.compare(that.angle, angle) != 0) return false;
        return time == that.time;

    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) panTilt;
        temp = Double.doubleToLongBits(angVel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }


}
