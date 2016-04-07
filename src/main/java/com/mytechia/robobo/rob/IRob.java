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

	 void moveMT(int angVel1, int angle1, int angVel2, int angle2);

	 void moveMT(int angVel1, int angVel2, long time);

	 void movePan(int angVel, int angle);

	 void movePan(int angVel, long time);

	 void moveTilt(int angVel, int angle);

	 void moveTilt(int angVel, long time);

	 void resetPanTiltOffset();

	 List<MotorStatus> getLastStatusMotors();

	 List<IRSensorStatus> getLastStatusIRs();

	 List<GapStatus> getLastGapsStatus();

	 List<FallStatus> getLastStatusFalls();

	 List<BumpStatus> getLastStatusBumps();

	 List<ObstacleSensorStatus> getLastStatusObstacles();

	 BatteryStatus getLastStatusBattery();

	 void addRobStatusListener(IRobStatusListener listener);

	 void removeRobStatusListener(IRobStatusListener listener);

}
