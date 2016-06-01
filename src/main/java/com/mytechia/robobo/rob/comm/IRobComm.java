/**
 * *****************************************************************************
 * <p>
 * Copyright (C) 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2016 Gervasio Varela <gervasio.varela@mytechia.com>
 * <p>
 * This file is part of Robobo RobComm Library.
 * ****************************************************************************
 */
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
     * 0 --> STOP | STOP - 1 --> REVERSE | REVERSE - 2 --> FORWARD | FORWARD - 4
     * --> FORWARD | REVERSE - 8 --> REVERSE | FORWARD
     *
     * @param mode direction of movement of the motors
     * @param angVel1 angular velocity of the motor 1
     * @param angle1 total angle of movement of the motor 1
     * @param angVel2 angular velocity of the motor 2
     * @param angle2 total angle of movement of the motor 2
     */
    public void moveMT(byte mode, short angVel1, int angle1, short angVel2, int angle2) throws CommunicationException;

    /**
     * Sends a move command to the two motors in charge of wheel movement.
     *
     * The 'mode' paramter configures the direction of movement of the motors: -
     * 0 --> STOP | STOP - 1 --> REVERSE | REVERSE - 2 --> FORWARD | FORWARD - 4
     * --> FORWARD | REVERSE - 8 --> REVERSE | FORWARD
     *
     * @param mode direction of movement of the motors
     * @param angVel1 angular velocity of the motor 1
     * @param angVel2 angular velocity o the motor 2
     * @param time total time duration of the movement
     */
    public void moveMT(byte mode, short angVel1, short angVel2, long time) throws CommunicationException;

    /**
     * Sends a move command to the motor in charge of the smartphone PAN
     * movement.
     *
     * @param angVel angular velocity of the motor
     * @param angle total angle of movement
     */
    public void movePan(short angVel, int angle) throws CommunicationException;

    /**
     * Sends a move command to the motor in charge of the smartphone TILT
     * movement.
     *
     * @param angVel angular velocity of the motor
     * @param angle total angle of movement
     */
    public void moveTilt(short angVel, int angle) throws CommunicationException;

    /**
     * Resets the pan & tilt offset...
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

    void setOperationMode(byte operationMode) throws CommunicationException;

    void infraredConfiguration(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws CommunicationException;

    void maxValueMotors(int m1Tension, int m1Time, int m2Tension, int m2Time, int panTension, int panTime,
            int tiltTension, int tiltTime) throws CommunicationException;

}
