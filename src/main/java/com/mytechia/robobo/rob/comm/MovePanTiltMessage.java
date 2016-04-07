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

import static com.mytechia.robobo.rob.comm.MessageType.MovePanTiltMessage;

/**
 *  Implementation for MovePanTiltMessage
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MovePanTiltMessage extends RoboCommand {

    private byte panTilt;

    private int angVel;

    private int angle;

    private int time;


    public MovePanTiltMessage(
            byte panTilt,
            int angVel,
            int angle,
            int time) {

        super();
        this.setCommandType(MovePanTiltMessage.commandType);
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

        messageCoder.writeInt(this.angVel, "angVel");

        messageCoder.writeInt(this.angle, "angle");

        messageCoder.writeInt(this.time, "time");

        return messageCoder.getBytes();

    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {

        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.panTilt = messageDecoder.readByte("panTilt");

        this.angVel = messageDecoder.readInt("angVel");

        this.angle = messageDecoder.readInt("angle");

        this.time = messageDecoder.readInt("time");

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
