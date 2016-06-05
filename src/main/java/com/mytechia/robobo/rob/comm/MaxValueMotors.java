/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gómez <julio.gomez@mytechia.com>
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

import static com.mytechia.robobo.rob.comm.MessageType.MaxValueMotors;

import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

public class MaxValueMotors extends RoboCommand {

	private static final String TILT_TIME = "tiltTime";

	private static final String TILT_TENSION = "tiltTension";

	private static final String PAN_TIME = "panTime";

	private static final String PAN_TENSION = "panTension";

	private static final String M2_TIME = "m2Time";

	private static final String M2_TENSION = "m2Tension";

	private static final String M1_TIME = "m1Time";

	private static final String M1_TENSION = "m1Tension";

	private int  m1Tension;
	
	private int m1Time;
	
	private int m2Tension;
	
	private int m2Time;
	
	private int panTension;
	
	private int panTime;
	
	private int tiltTension;
	
	private int tiltTime;
	
	
	

	public MaxValueMotors(int m1Tension, int m1Time, int m2Tension, int m2Time, int panTension, int panTime,
			int tiltTension, int tiltTime) {
		super();
		super.setCommandType(MaxValueMotors.commandType);
		this.m1Tension = m1Tension;
		this.m1Time = m1Time;
		this.m2Tension = m2Tension;
		this.m2Time = m2Time;
		this.panTension = panTension;
		this.panTime = panTime;
		this.tiltTension = tiltTension;
		this.tiltTime = tiltTime;
	}

	public MaxValueMotors(byte[] message) throws MessageFormatException {
		super(message);
		super.setCommandType(MaxValueMotors.commandType);
	}

	@Override
	protected int decodeMessageData(byte[] binaryMessage, int arg1) throws MessageFormatException {
		
		MessageDecoder messageDecoder = this.getMessageDecoder();
		
		this.m1Tension=messageDecoder.readInt(M1_TENSION);
		
		this.m1Time=messageDecoder.readInt(M1_TIME);
		
		this.m2Tension=messageDecoder.readInt(M2_TENSION);
		
		this.m2Time=messageDecoder.readInt(M2_TIME);
		
		this.panTension=messageDecoder.readInt(PAN_TENSION);
		
		this.panTime=messageDecoder.readInt(PAN_TIME);
		
		this.tiltTension=messageDecoder.readInt(TILT_TENSION);
		
		this.tiltTime=messageDecoder.readByte(TILT_TIME);
		
		return messageDecoder.getArrayIndex();
	}

	public int getM1Tension() {
		return m1Tension;
	}

	public void setM1Tension(int m1Tension) {
		this.m1Tension = m1Tension;
	}

	public int getM1Time() {
		return m1Time;
	}

	public void setM1Time(int m1Time) {
		this.m1Time = m1Time;
	}

	public int getM2Tension() {
		return m2Tension;
	}

	public void setM2Tension(int m2Tension) {
		this.m2Tension = m2Tension;
	}

	public int getM2Time() {
		return m2Time;
	}

	public void setM2Time(int m2Time) {
		this.m2Time = m2Time;
	}

	public int getPanTension() {
		return panTension;
	}

	public void setPanTension(int panTension) {
		this.panTension = panTension;
	}

	public int getPanTime() {
		return panTime;
	}

	public void setPanTime(int panTime) {
		this.panTime = panTime;
	}

	public int getTiltTension() {
		return tiltTension;
	}

	public void setTiltTension(int tiltTension) {
		this.tiltTension = tiltTension;
	}

	public int getTiltTime() {
		return tiltTime;
	}

	public void setTiltTime(int tiltTime) {
		this.tiltTime = tiltTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + m1Tension;
		result = prime * result + m1Time;
		result = prime * result + m2Tension;
		result = prime * result + m2Time;
		result = prime * result + panTension;
		result = prime * result + panTime;
		result = prime * result + tiltTension;
		result = prime * result + tiltTime;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaxValueMotors other = (MaxValueMotors) obj;
		if (m1Tension != other.m1Tension)
			return false;
		if (m1Time != other.m1Time)
			return false;
		if (m2Tension != other.m2Tension)
			return false;
		if (m2Time != other.m2Time)
			return false;
		if (panTension != other.panTension)
			return false;
		if (panTime != other.panTime)
			return false;
		if (tiltTension != other.tiltTension)
			return false;
		if (tiltTime != other.tiltTime)
			return false;
		return true;
	}
	
	
	

}
