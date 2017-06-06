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

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;

/**
 * This is the low level communication interface between the smartphone (OBO)
 * and the robotic platform (ROB).
 */
public interface IRobComm {

    /**
     * Changes the color of the LED light identified by 'ledId'.
     *
     * @param ledId the identifier of the LED light whose color is going to
     * change
     * @param r the red value of the RGB color
     * @param g the green value of the RGB color
     * @param b the blue value of the RGB color
     */
    public void setLEDColor(int ledId, int r, int g, int b) throws CommunicationException;

    /**
     * Changes the automatic mode of operation of the LEDs.
     *
     * @param mode the new mode of operation of the LEDs
     */
    public void setLEDsMode(byte mode) throws CommunicationException;

    /**
     * Sends a move command to the two motors in charge of wheel movement.
     *
     * The 'mode' paramter configures the direction of movement of the motors: -
     * 0 --- STOP | STOP - 1 --- REVERSE | REVERSE - 2 --- FORWARD | FORWARD - 4
     * --- FORWARD | REVERSE - 8 --- REVERSE | FORWARD
     *  @param mode direction of movement of the motors
     * @param angVel1 angular velocity of the motor 1
     * @param angle1 total angle of movement of the motor 1
     * @param angVel2 angular velocity of the motor 2
     * @param angle2 total angle of movement of the motor 2
     */
    public void moveMT(byte mode, int angVel1, int angle1, int angVel2, int angle2) throws CommunicationException;

    /**
     * Sends a move command to the two motors in charge of wheel movement.
     *
     * The 'mode' paramter configures the direction of movement of the motors: -
     * 0 --- STOP | STOP - 1 --- REVERSE | REVERSE - 2 --- FORWARD | FORWARD - 4
     * --- FORWARD | REVERSE - 8 --- REVERSE | FORWARD
     *
     * @param mode direction of movement of the motors
     * @param angVel1 angular velocity of the motor 1
     * @param angVel2 angular velocity o the motor 2
     * @param time total time duration of the movement
     */
    public void moveMT(byte mode, int angVel1, int angVel2, long time) throws CommunicationException;

    /**
     * Sends a move command to the motor in charge of the smartphone PAN
     * movement.
     *
     * @param angVel angular velocity of the motor
     * @param angle total angle of movement
     */
    public void movePan(int angVel, int angle) throws CommunicationException;

    /**
     * Sends a move command to the motor in charge of the smartphone TILT
     * movement.
     *
     * @param angVel angular velocity of the motor
     * @param angle total angle of movement
     */
    public void moveTilt(int angVel, int angle) throws CommunicationException;

    public void movePanTilt(int angVelPan, int anglePan, int angVelTilt, int angleTilt) throws CommunicationException;

    /**
     * Resets the pan and tilt offset...
     *
     */
    public void resetPanTiltOffset() throws CommunicationException;

    /**
     * Changes the period used by the ROB to send ROB-STATUS-MESSAGES.
     *
     * @param period the period in millisecods, a value of 0 stops the sending
     * of status messages
     */
    public void setRobStatusPeriod(int period) throws CommunicationException;

    void addRobStatusListener(IRobCommStatusListener rsListener);

    void removeRobStatusListener(IRobCommStatusListener rsListener);

    void addStopWarningListener(IRobCommStopWarningListener swListener);

    void removeStopWarningListener(IRobCommStopWarningListener swListener);

    void setOperationMode(byte operationMode) throws CommunicationException;

    void infraredConfiguration(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws CommunicationException;

    void setControlValues(byte motorId, int startki, int perturbationski, int stopki) throws CommunicationException;

    void maxValueMotors(int m1Tension, int m1Time, int m2Tension, int m2Time, int panTension, int panTime,
            int tiltTension, int tiltTime) throws CommunicationException;

}
