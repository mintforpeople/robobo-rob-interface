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
import com.mytechia.robobo.rob.BumpStatus;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.MotorStatus;
import com.mytechia.robobo.rob.ObstacleSensorStatus;

import java.util.Arrays;

import static com.mytechia.robobo.rob.comm.MessageType.RobStatusMessage;

/**
 * Implementation for RobStatusMessage.
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class RobStatusMessage extends RoboCommand {
    
    private static final String GAPS = "gaps";
    private static final String FALLS = "falls";
    private static final String IRS = "irs";
    private static final String OBSTACLES = "obstacles";
    private static final String BUMPS = "bumps";
    private static final String MOTOR_VOLTAGES = "motorVoltages";
    private static final String MOTOR_ANGLES = "motorAngles";
    private static final String MOTOR_VELOCITIES = "motorVelocities";
    private static final String BATERRY_LEVEL = "bateryLevel";
    private static final String DOCK_CONNECTION="dockConnection";

    private byte gaps;

    private byte falls;

    private double[] irs;               // 8 * double

    private double[] obstacles;         // 8 * double

    private double[] bumps;             // 4 * double

    private double[] motorVelocities;   // 4 * double

    private double[] motorAngles;       // 4 * double

    private double[] motorVoltages;     // 4 * double

    private int baterryLevel;

    private boolean dockConnection;

    public RobStatusMessage(
            byte gaps,
            byte falls,
            double[] irs,
            double[] obstacles,
            double[] bumps,
            double[] motorVelocities,
            double[] motorAngles,
            double[] motorVoltages,
            int baterryLevel,
            boolean dockConnection) {

        super();
        this.setCommandType(RobStatusMessage.commandType);
        this.gaps = gaps;
        this.falls = falls;
        this.irs = irs;
        this.obstacles = obstacles;
        this.bumps = bumps;
        this.motorVelocities = motorVelocities;
        this.motorAngles = motorAngles;
        this.motorVoltages = motorVoltages;
        this.baterryLevel= baterryLevel;
        this.dockConnection= dockConnection;

    }

    public RobStatusMessage(byte[] messageData) throws MessageFormatException {
        super(messageData);
    }

    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.gaps, GAPS);

        messageCoder.writeByte(this.falls, FALLS);

        messageCoder.writeDoubleArray(this.irs, IRS);

        messageCoder.writeDoubleArray(this.obstacles, OBSTACLES);

        messageCoder.writeDoubleArray(this.bumps, BUMPS);

        messageCoder.writeDoubleArray(this.motorVelocities, MOTOR_VELOCITIES);

        messageCoder.writeDoubleArray(this.motorAngles, MOTOR_ANGLES);

        messageCoder.writeDoubleArray(this.motorVoltages, MOTOR_VOLTAGES);

        messageCoder.writeInt(this.baterryLevel, BATERRY_LEVEL);

        byte byteDockConnection= (byte) (this.dockConnection? 1: 0);
        messageCoder.writeByte(byteDockConnection, DOCK_CONNECTION);

        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.gaps = messageDecoder.readByte(GAPS);

        this.falls = messageDecoder.readByte(FALLS);

        this.irs = messageDecoder.readDoubleArray(IRS, IRSensorStatus.IRSentorStatusId.values().length);

        this.obstacles = messageDecoder.readDoubleArray(OBSTACLES, ObstacleSensorStatus.ObstacleSensorStatusId.values().length);

        this.bumps = messageDecoder.readDoubleArray(BUMPS, BumpStatus.BumpStatusId.values().length);

        this.motorVelocities = messageDecoder.readDoubleArray(MOTOR_VELOCITIES, MotorStatus.MotorStatusId.values().length);

        this.motorAngles = messageDecoder.readDoubleArray(MOTOR_ANGLES, MotorStatus.MotorStatusId.values().length);

        this.motorVoltages = messageDecoder.readDoubleArray(MOTOR_VOLTAGES, MotorStatus.MotorStatusId.values().length);

        this.baterryLevel = messageDecoder.readInt(BATERRY_LEVEL);

        byte byteDocConnection = messageDecoder.readByte(DOCK_CONNECTION);

        this.dockConnection= (byteDocConnection==1);

        return messageDecoder.getArrayIndex();
    }

    public static String getGAPS() {
        return GAPS;
    }

    public static String getFALLS() {
        return FALLS;
    }

    public static String getIRS() {
        return IRS;
    }

    public static String getOBSTACLES() {
        return OBSTACLES;
    }

    public static String getBUMPS() {
        return BUMPS;
    }

    public static String getMOTOR_VOLTAGES() {
        return MOTOR_VOLTAGES;
    }

    public static String getMOTOR_ANGLES() {
        return MOTOR_ANGLES;
    }

    public static String getMOTOR_VELOCITIES() {
        return MOTOR_VELOCITIES;
    }

    public byte getGaps() {
        return gaps;
    }

    public byte getFalls() {
        return falls;
    }

    public double[] getIrs() {
        return irs;
    }

    public double[] getObstacles() {
        return obstacles;
    }

    public double[] getBumps() {
        return bumps;
    }

    public double[] getMotorVelocities() {
        return motorVelocities;
    }

    public double[] getMotorAngles() {
        return motorAngles;
    }

    public double[] getMotorVoltages() {
        return motorVoltages;
    }

    public void setFalls(byte falls) {
        this.falls = falls;
    }

    public void setGaps(byte gaps) {
        this.gaps = gaps;
    }

    public int getBateryLevel() {
        return baterryLevel;
    }

    public void setBaterryLevel(int baterryLevel) {
        this.baterryLevel = baterryLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RobStatusMessage that = (RobStatusMessage) o;

        if (gaps != that.gaps) {
            return false;
        }
        if (falls != that.falls) {
            return false;
        }

        if(this.baterryLevel!= that.baterryLevel){
            return false;
        }
        if (!Arrays.equals(irs, that.irs)) {
            return false;
        }
        if (!Arrays.equals(obstacles, that.obstacles)) {
            return false;
        }
        if (!Arrays.equals(bumps, that.bumps)) {
            return false;
        }
        if (!Arrays.equals(motorVelocities, that.motorVelocities)) {
            return false;
        }
        if (!Arrays.equals(motorAngles, that.motorAngles)) {
            return false;
        }

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

