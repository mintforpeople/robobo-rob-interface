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

package com.mytechia.robobo.rob;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.mytechia.robobo.util.Color;

import java.util.List;

public interface IRob {

	 void setLEDColor(int led, Color color) throws InternalErrorException;

	 void setLEDsMode(LEDsModeEnum mode) throws InternalErrorException;

	 void moveMT(MoveMTMode mode, short angVel1, int angle1, short angVel2, int angle2) throws InternalErrorException;

	 void moveMT(MoveMTMode mode, short angVel1, short angVel2, long time) throws InternalErrorException;

         /** Moves the PAN of the ROB at 'angVel' velocity until reaching 'angle'
          * 
          * @param angVel the velocity (0-255)
          * @param angle  the angle (0-90)
          */
	 void movePan(short angVel, int angle) throws InternalErrorException;



         /** Moves the TILT of the ROB at 'angVel' velocity until reaching 'angle'
          * 
          * @param angVel the velocity (0-255)
          * @param angle  the angle (0-90)
          */
	 void moveTilt(short angVel, int angle) throws InternalErrorException;

	 void setOperationMode(byte operationMode) throws InternalErrorException;


	 void resetPanTiltOffset() throws InternalErrorException;

	 List<MotorStatus> getLastStatusMotors();

	 List<IRSensorStatus> getLastStatusIRs();

	 List<GapStatus> getLastGapsStatus();

	 List<FallStatus> getLastStatusFalls();

	 List<BumpStatus> getLastStatusBumps();

	 List<ObstacleSensorStatus> getLastStatusObstacles();

	 BatteryStatus getLastStatusBattery();
         
         void setRobStatusPeriod(int period) throws InternalErrorException;

	 void addRobStatusListener(IRobStatusListener listener);

	 void removeRobStatusListener(IRobStatusListener listener);
	 
	 void configureInfrared(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws InternalErrorException;
	 
	 
	 void maxValueMotors(int m1Tension, int m1Time, 
			 			int m2Tension, int m2Time, int panTension, int panTime,
			 			int tiltTension, int tiltTime) throws InternalErrorException;
}