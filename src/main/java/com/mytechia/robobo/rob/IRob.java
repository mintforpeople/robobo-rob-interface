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

	 void moveMT(short angVel1, short angle1, short angVel2, short angle2);

	 void moveMT(short angVel1, short angVel2, long time);

	 void movePan(short angVel, short angle);

	 void movePan(short angVel, long time);

	 void moveTilt(short angVel, short angle);

	 void moveTilt(short angVel, long time);

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
