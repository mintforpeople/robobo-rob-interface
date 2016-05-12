/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.robobo.util.Color;

import java.util.List;

public interface IRob {

	 void setLEDColor(int led, Color color);

	 void setLEDsMode(LEDsModeEnum mode);

	 void moveMT(MoveMTMode mode, short angVel1, int angle1, short angVel2, int angle2);

	 void moveMT(MoveMTMode mode, short angVel1, short angVel2, long time);

         /** Moves the PAN of the ROB at 'angVel' velocity until reaching 'angle'
          * 
          * @param angVel the velocity (0-255)
          * @param angle  the angle (0-90)
          */
	 void movePan(short angVel, int angle);

	 void movePan(short angVel, long time);

         /** Moves the TILT of the ROB at 'angVel' velocity until reaching 'angle'
          * 
          * @param angVel the velocity (0-255)
          * @param angle  the angle (0-90)
          */
	 void moveTilt(short angVel, int angle);

	 void moveTilt(short angVel, long time);

	 void resetPanTiltOffset();

	 List<MotorStatus> getLastStatusMotors();

	 List<IRSensorStatus> getLastStatusIRs();

	 List<GapStatus> getLastGapsStatus();

	 List<FallStatus> getLastStatusFalls();

	 List<BumpStatus> getLastStatusBumps();

	 List<ObstacleSensorStatus> getLastStatusObstacles();

	 BatteryStatus getLastStatusBattery();
         
         void setRobStatusPeriod(int period);

	 void addRobStatusListener(IRobStatusListener listener);

	 void removeRobStatusListener(IRobStatusListener listener);

}
