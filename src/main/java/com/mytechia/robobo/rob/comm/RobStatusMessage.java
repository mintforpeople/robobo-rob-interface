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
 *  Implementation for RobStatusMessage.
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class RobStatusMessage extends Command {


    private byte gaps;

    private byte falls;

    private double[] irs;               // 8 * double

    private double[] obstacles;         // 4 * double

    private double[] bumps;             // 4 * double

    private double[] motorVelocities;   // 4 * double

    private double[] motorAngles;       // 4 * double

    private double[] motorVoltages;     // 4 * double



    public RobStatusMessage(
            byte gaps,
            byte falls,
            double[] irs,
            double[] obstacles,
            double[] bumps,
            double[] motorVelocities,
            double[] motorAngles,
            double[] motorVoltages) {

        super();
        this.gaps = gaps;
        this.falls = falls;
        this.irs = irs;
        this.obstacles = obstacles;
        this.bumps = bumps;
        this.motorVelocities = motorVelocities;
        this.motorAngles = motorAngles;
        this.motorVoltages = motorVoltages;

    }


    public RobStatusMessage(byte [] messageData) throws MessageFormatException {
        super(messageData);
    }


    @Override
    protected final byte[] codeMessageData() throws MessageFormatException
    {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.gaps, "gaps");

        messageCoder.writeByte(this.falls, "falls");

        messageCoder.writeDoubleArray(this.irs, "irs");

        messageCoder.writeDoubleArray(this.obstacles, "obstacles");

        messageCoder.writeDoubleArray(this.bumps, "bumps");

        messageCoder.writeDoubleArray(this.motorVelocities, "motorVelocities");

        messageCoder.writeDoubleArray(this.motorAngles, "motorAngles");

        messageCoder.writeDoubleArray(this.motorVoltages, "motorVoltages");

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.gaps = messageDecoder.readByte("gaps");

        this.falls = messageDecoder.readByte("falls");

        this.irs = messageDecoder.readDoubleArray("irs", 8);

        this.obstacles = messageDecoder.readDoubleArray("obstacles", 4);

        this.bumps = messageDecoder.readDoubleArray("bumps", 4);

        this.motorVelocities = messageDecoder.readDoubleArray("motorVelocities", 4);

        this.motorAngles = messageDecoder.readDoubleArray("motorAngles", 4);

        this.motorVoltages = messageDecoder.readDoubleArray("motorVoltages", 4);

        return messageDecoder.getArrayIndex();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobStatusMessage that = (RobStatusMessage) o;

        if (gaps != that.gaps) return false;
        if (falls != that.falls) return false;
        if (!Arrays.equals(irs, that.irs)) return false;
        if (!Arrays.equals(obstacles, that.obstacles)) return false;
        if (!Arrays.equals(bumps, that.bumps)) return false;
        if (!Arrays.equals(motorVelocities, that.motorVelocities)) return false;
        if (!Arrays.equals(motorAngles, that.motorAngles)) return false;
        return Arrays.equals(motorVoltages, that.motorVoltages);

    }

    @Override
    public int hashCode() {
        int result = (int) gaps;
        result = 31 * result + (int) falls;
        result = 31 * result + Arrays.hashCode(irs);
        result = 31 * result + Arrays.hashCode(obstacles);
        result = 31 * result + Arrays.hashCode(bumps);
        result = 31 * result + Arrays.hashCode(motorVelocities);
        result = 31 * result + Arrays.hashCode(motorAngles);
        result = 31 * result + Arrays.hashCode(motorVoltages);
        return result;
    }


}

