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

/**
 * This is the low level communication interface between the smartphone (OBO)
 * and the robotic platform (ROB).
 */
public interface IRobComm {

    /** Changes the color of the LED light identified by 'ledId'.
     *
     * @param ledId the identifier of the LED light whose color is going to change
     * @param r the red value of the RGB color
     * @param g the green value of the RGB color
     * @param b the blue value of the RGB color
     */
    public void setLEDColor(int ledId, int r, int g, int b);


    /** Changes the automatic mode of operation of the LEDs.
     *
     * @param mode the new mode of operation of the LEDs
     */
    public void setLEDsMode(byte mode);





    /** Sends a move command to the two motors in charge of wheel movement.
     * 
     * The 'mode' paramter configures the direction of movement of the motors:
     * - 0 --> STOP | STOP
     * - 1 --> REVERSE | REVERSE
     * - 2 --> FORWARD | FORWARD
     * - 4 --> FORWARD | REVERSE
     * - 8 --> REVERSE | FORWARD
     *
     * @param mode direction of movement of the motors
     * @param angVel1 angular velocity of the motor 1
     * @param angle1 total angle of movement of the motor 1
     * @param angVel2 angular velocity of the motor 2
     * @param angle2 total angle of movement of the motor 2
     */
    public void moveMT(byte mode, short angVel1, int angle1, short angVel2, int angle2) ;

    /** Sends a move command to the two motors in charge of wheel movement.
     *
     * The 'mode' paramter configures the direction of movement of the motors:
     * - 0 --> STOP | STOP
     * - 1 --> REVERSE | REVERSE
     * - 2 --> FORWARD | FORWARD
     * - 4 --> FORWARD | REVERSE
     * - 8 --> REVERSE | FORWARD
     * 
     * @param mode direction of movement of the motors
     * @param angVel1 angular velocity of the motor 1
     * @param angVel2 angular velocity o the motor 2
     * @param time total time duration of the movement
     */
    public void moveMT(byte mode, short angVel1, short angVel2, long time);





    /** Sends a move command to the motor in charge of the smartphone PAN movement.
     *
     * @param angVel angular velocity of the motor
     * @param angle total angle of movement
     */
    public void movePan(short angVel, int angle);

    /** Sends a move command to the motor in charge of the smartphone PAN movement.
     *
     * @param angVel angular velocity of the motor
     * @param time total time duration of the movement
     */
    public void movePan(short angVel, long time);


    /** Sends a move command to the motor in charge of the smartphone TILT movement.
     *
     * @param angVel angular velocity of the motor
     * @param angle total angle of movement
     */
    public void moveTilt(short angVel, int angle);

    /** Sends a move command to the motor in charge of the smartphone TILT movement.
     *
     * @param angVel angular velocity of the motor
     * @param time total time duration of the movement
     */
    public void moveTilt(short angVel, long time);


    /** Resets the pan & tilt offset...
     *
     */
    public void resetPanTiltOffset();


    /** Changes the period used by the ROB to send ROB-STATUS-MESSAGES.
     *
     * @param period the period in millisecods, a value of 0 stops the sending of status messages
     */
    public void setRobStatusPeriod(int period);
    
    void addRobStatusListener(IRobCommStatusListener rsListener);
    
    void removeRobStatusListener(IRobCommStatusListener rsListener);

}
