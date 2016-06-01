/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
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
