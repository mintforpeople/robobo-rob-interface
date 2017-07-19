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

	 void setLEDColor(int led, Color color) throws InternalErrorException, IllegalArgumentException;

	 void setLEDsMode(LEDsModeEnum mode) throws InternalErrorException;


		/**
		 * Moves the motors by degrees
		 * @param angVelR Angular Speed of the right Motor
		 * @param angleR Angle of the right Motor
		 * @param angVelL Angular Speed of the left Motor
		 * @param angleL Angle of the left Motor
		 * @throws InternalErrorException
		 */
	 void moveMT(int angVelR, int angleR, int angVelL, int angleL) throws InternalErrorException;


		/**
		 * Moves the motors by time
		 * @param angVelR Angular speed of the right motor
		 * @param angVelL Angular speed of the left motor
		 * @param time Time in milliseconds
		 * @throws InternalErrorException
		 */

	 void moveMT(int angVelR, int angVelL, long time) throws InternalErrorException ;

         /** Moves the PAN of the ROB at 'angVel' velocity until reaching 'angle'
          * 
          * @param angVel the velocity (0-255)
          * @param angle  the angle (0-90)
          */
	 void movePan(int angVel, int angle) throws InternalErrorException;



         /** Moves the TILT of the ROB at 'angVel' velocity until reaching 'angle'
          * 
          * @param angVel the velocity (0-255)
          * @param angle  the angle (0-90)
          */
	 void moveTilt(int angVel, int angle) throws InternalErrorException;

	 void movePanTilt(int angVelPan, int anglePan, int angVelTilt, int angleTilt) throws InternalErrorException;

	 void setOperationMode(byte operationMode) throws InternalErrorException;


	 void resetPanTiltOffset() throws InternalErrorException;

	 List<MotorStatus> getLastStatusMotors();

	 List<IRSensorStatus> getLastStatusIRs();

	 List<GapStatus> getLastGapsStatus();

	 List<FallStatus> getLastStatusFalls();

	 List<BumpStatus> getLastStatusBumps();

	 List<ObstacleSensorStatus> getLastStatusObstacles();

	 BatteryStatus getLastStatusBattery();

	StopWarningType getLastStopWarning();
         
	 void setRobStatusPeriod(int period) throws InternalErrorException;

	 void addRobStatusListener(IRobStatusListener listener);

	void addStopWarningListener(IStopWarningListener listener);

	void removeStopWarningListener(IStopWarningListener listener);

	 void removeRobStatusListener(IRobStatusListener listener);
	 
	 void configureInfrared(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws InternalErrorException;
	 
	 void maxValueMotors(int m1Tension, int m1Time, 
			 			int m2Tension, int m2Time, int panTension, int panTime,
			 			int tiltTension, int tiltTime) throws InternalErrorException;
}
