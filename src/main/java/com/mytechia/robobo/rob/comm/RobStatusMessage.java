/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2016 Victor Sonora Pombo <victor.pombo@mytechia.com>
 *
 *   This file is part of Robobo ROB Interface Library.
 *
 *   Robobo ROB Interface Library is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Robobo ROB Interface Library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Robobo ROB Interface Library.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.MotorStatus;

import static com.mytechia.robobo.rob.comm.MessageType.RobStatusMessage;

/**
 * Implementation for RobStatusMessage.
 *
 * Created by Victor Sonora Pombo.
 */
public class RobStatusMessage extends RoboCommand {
    
    private static final String WALL_CONNECTION = "wallConnection";
    private static final String BATERRY_INFORMATION = "baterryInformation";
    private static final String GAPS = "gaps";
    private static final String FALLS = "falls";
    private static final String IRS = "irs";
    private static final String OBSTACLES = "obstacles";
    private static final String BUMPS = "bumps";
    private static final String MOTOR_VOLTAGES = "motorVoltages";
    private static final String MOTOR_ANGLES = "motorAngles";
    private static final String MOTOR_VELOCITIES = "motorVelocities";
    
    private byte gaps;

    private byte falls;

    private int[] irs;               // 9 * short

    private short[] motorVelocities;   // 4 * (2 bytes)

    private int[] motorAngles;       // 4 * (2 bytes)

    private int[] motorVoltages;     // 4 * (2 bytes)

    private byte wallConnection;

    private short batteryLevel;

    public RobStatusMessage(
            byte gaps,
            byte falls,
            int[] irs,
            short[] obstacles,
            short[] bumps,
            short[] motorVelocities,
            int[] motorAngles,
            int[] motorVoltages,
            byte wallConnection,
            short batteryInformation) {

        super();
        this.setCommandType(RobStatusMessage.commandType);
        this.gaps = gaps;
        this.falls = falls;
        this.irs = irs;
        //this.obstacles = obstacles;
        //this.bumps = bumps;
        this.motorVelocities = motorVelocities;
        this.motorAngles = motorAngles;
        this.motorVoltages = motorVoltages;
        this.wallConnection= wallConnection;
        this.batteryLevel= batteryInformation;

    }

    public RobStatusMessage(byte[] messageData) throws MessageFormatException {
        super(messageData);
    }
    
    private short[] transformToShortArray(double[] arrays){
        short[] shortValues= new short[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            shortValues[i]= (short) arrays[i];
        }
        
        return shortValues;
        
    }
    
    private int[] transformToIntArray(double[] arrays){
        int[] shortValues= new int[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            shortValues[i]= (int) arrays[i];
        }
        
        return shortValues;
        
    }

    @Override
    protected final byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(this.gaps, GAPS);

        messageCoder.writeByte(this.falls, FALLS);
        
        messageCoder.writeUShortArray(this.irs, IRS);

        //messageCoder.writeShortArray(this.obstacles, OBSTACLES);

        //messageCoder.writeShortArray(this.bumps, BUMPS);

        messageCoder.writeShortArray(this.motorVelocities, MOTOR_VELOCITIES);

        messageCoder.writeIntArray(this.motorAngles, MOTOR_ANGLES);

        messageCoder.writeIntArray(this.motorVoltages, MOTOR_VOLTAGES);

//        messageCoder.writeInt(this.baterryLevel, BATERRY_LEVEL);
//
//        byte byteDockConnection= (byte) (this.dockConnection? 1: 0);
//        messageCoder.writeByte(byteDockConnection, DOCK_CONNECTION);

        return messageCoder.getBytes();
        
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.gaps = messageDecoder.readByte(GAPS);

        this.falls = messageDecoder.readByte(FALLS);

        this.irs = messageDecoder.readUShortArray(IRS, IRSensorStatus.IRSentorStatusId.values().length);

        this.motorVelocities = messageDecoder.readShortArray(MOTOR_VELOCITIES, MotorStatus.MotorStatusId.values().length);

        this.motorAngles = messageDecoder.readIntArray(MOTOR_ANGLES, MotorStatus.MotorStatusId.values().length);
        
        this.motorVoltages = messageDecoder.readIntArray(MOTOR_VOLTAGES, MotorStatus.MotorStatusId.values().length);
        
        this.wallConnection= messageDecoder.readByte(WALL_CONNECTION);
        
        this.batteryLevel= messageDecoder.readShort(BATERRY_INFORMATION);

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

    public int[] getIrs() {
        return irs;
    }

    /*
    public short[] getObstacles() {
        return obstacles;
    }

    public short[] getBumps() {
        return bumps;
    }
    */

    public short[] getMotorVelocities() {
        return motorVelocities;
    }

    public int[] getMotorAngles() {
        return motorAngles;
    }

    public int[] getMotorVoltages() {
        return motorVoltages;
    }

    public void setFalls(byte falls) {
        this.falls = falls;
    }

    public void setGaps(byte gaps) {
        this.gaps = gaps;
    }

	public byte getWallConnection() {
		return wallConnection;
	}

	public void setWallConnection(byte wallConnection) {
		this.wallConnection = wallConnection;
	}

	public void setMotorVoltages(int[] motorVoltages) {
		this.motorVoltages = motorVoltages;
	}



	public void setBatteryLevel(short batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public short getBatteryLevel() {
		return batteryLevel;
	}







}

