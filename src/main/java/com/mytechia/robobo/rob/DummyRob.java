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
import com.mytechia.robobo.util.Color;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation for testing of IRob interface
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class DummyRob implements IRob{
    
        private static final Logger LOGGER= LoggerFactory.getLogger(DummyRob.class);

    @Override
    public void setLEDColor(int led, Color color) throws InternalErrorException {
        LOGGER.info("Call method {}(led={}, color={}))", "setLEDColor", led, color);
    }

    @Override
    public void setLEDsMode(LEDsModeEnum mode) throws InternalErrorException {
        LOGGER.info("Call method {}(mode={})", "setLEDsMode", mode);
    }

    @Override
    public void moveMT(MoveMTMode mode, int angVel1, int angle1, int angVel2, int angle2) throws InternalErrorException {
        LOGGER.info("Call method {}(mode={}, angVel1={}, angle1={}, angVel2={}, angle2={})", "moveMT", mode, angVel1, angle1, angVel2, angle2);
    }

    @Override
    public void moveMT(MoveMTMode mode, int angVel1, int angVel2, long time) throws InternalErrorException {
        LOGGER.info("Call method {}(mode={}, angVel1={}, angVel2={}, time={})", "moveMT", mode, angVel1, angVel2, time);
        
    }

    @Override
    public void movePan(int angVel, int angle) throws InternalErrorException {
        LOGGER.info("Call method {}(angVel={}, angle={})", "movePan", angVel, angle);
    }

    @Override
    public void moveTilt(int angVel, int angle) throws InternalErrorException {
        LOGGER.info("Call method {}(angVel={}, angle={})", "moveTilt", angVel, angle);
    }

    @Override
    public void setOperationMode(byte operationMode) throws InternalErrorException {
        LOGGER.info("Call method {}(operationMode={})", "setOperationMode", operationMode);
    }

    @Override
    public void resetPanTiltOffset() throws InternalErrorException {
        LOGGER.info("Call method {}()", "resetPanTiltOffset");
    }

    @Override
    public List<MotorStatus> getLastStatusMotors() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<IRSensorStatus> getLastStatusIRs() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<GapStatus> getLastGapsStatus() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<FallStatus> getLastStatusFalls() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<BumpStatus> getLastStatusBumps() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ObstacleSensorStatus> getLastStatusObstacles() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public BatteryStatus getLastStatusBattery() {
        return new BatteryStatus();
    }

    @Override
    public StopWarningType getLastStopWarning() {
        return null;
    }

    @Override
    public void setRobStatusPeriod(int period) throws InternalErrorException {
        LOGGER.info("Call method ({} arguments={})");
    }

    @Override
    public void addRobStatusListener(IRobStatusListener listener) {
        LOGGER.info("Call method ({} arguments={})");
    }

    @Override
    public void addStopWarningListener(IStopWarningListener listener) {
        LOGGER.info("Call method ({} arguments={})");
    }

    @Override
    public void removeStopWarningListener(IStopWarningListener listener) {

    }

    @Override
    public void removeRobStatusListener(IRobStatusListener listener) {
        
    }

    @Override
    public void configureInfrared(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws InternalErrorException {
        LOGGER.info("Call method {}(infraredId={}, commandCode={}, dataByteLow={}, dataByteHigh={})", "configureInfrared",  infraredId,  commandCode,  dataByteLow,  dataByteHigh);
    }

    @Override
    public void configureMotorSControlValue(byte motorId, int startki, int perturbationski, int stopki) {
        LOGGER.info("Call method {}(motorId={}, startki={}, perturbationki={}, stopki={})", "configureInfrared",  motorId,  startki,  perturbationski, stopki );

    }

    @Override
    public void maxValueMotors(int m1Tension, int m1Time, int m2Tension, int m2Time, int panTension, int panTime, int tiltTension, int tiltTime) throws InternalErrorException {
        LOGGER.info("Call method {}( m1Tension={}, m1Time={},  m2Tension={}, m2Time={}, panTension={}, panTime={}, tiltTension={}, tiltTime={})", "maxValueMotors",  m1Tension, m1Time,  m2Tension, m2Time, panTension,  panTime,  tiltTension,  tiltTime);
    }
    
}
