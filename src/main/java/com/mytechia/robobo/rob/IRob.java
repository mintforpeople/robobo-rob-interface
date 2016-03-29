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

	 void moveMT(double angVel1, double angle1, double angVel2, double angle2);

	 void moveMT(double angVel1, double angVel2, long time);

	 void movePan(double angVel, double angle);

	 void movePan(double angVel, long time);

	 void moveTilt(double angVel, double angle);

	 void moveTilt(double angVel, long time);

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
