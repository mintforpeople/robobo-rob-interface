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
import static com.mytechia.robobo.rob.BumpStatus.BumpStatusId;
import static com.mytechia.robobo.rob.FallStatus.FallStatusId;
import static com.mytechia.robobo.rob.GapStatus.GapStatusId;
import static com.mytechia.robobo.rob.IRSensorStatus.IRSentorStatusId;
import static com.mytechia.robobo.rob.MotorStatus.MotorStatusId;
import static com.mytechia.robobo.rob.ObstacleSensorStatus.ObstacleSensorStatusId;

import com.mytechia.robobo.rob.comm.*;
import com.mytechia.robobo.util.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DefaultRob implements IRobCommStatusListener,IRobCommStopWarningListener, IRobCommErrorListener, IRob {
    
    private static final Logger LOGGER= LoggerFactory.getLogger(DefaultRob.class);

    private static final int MOTOR_COUNT = 4;
    private static final int ANGLE_CONVERSION_FACTOR = 10000;

    private static final short MAX_ANG_VEL = 100;
    private static final short MIN_ANG_VEL = -100;

    private static final short PT_ANG_VEL = 6;

    private static final int MAX_PAN_ANGLE = 345;
    private static final int MIN_PAN_ANGLE = 10;

    private static final short MAX_PAN_ANG_VEL = 100;
    private static final short MIN_PAN_ANG_VEL = 0;


    private static final short MAX_TILT_ANG_VEL = 100;
    private static final short MIN_TILT_ANG_VEL = 0;


    private static final int MAX_TILT_ANGLE = 105;
    private static final int MIN_TILT_ANGLE = 5;
    
    private static final int MAX_COLOR_VALUE = 4095; //12bits


    private int min_battery = 310; //1%
    private int max_battery = 400; //100%

    private IRobComm roboCom;

    private BatteryStatus battery= new BatteryStatus();

    private final List<ObstacleSensorStatus> obstacles = new ArrayList<ObstacleSensorStatus>();

    private final List<BumpStatus> bumps = new ArrayList<BumpStatus>();

    private final List<FallStatus> falls = new ArrayList<FallStatus>();

    private final List<GapStatus> gaps = new ArrayList<GapStatus>();

    private final List<IRSensorStatus> irSensors = new ArrayList<IRSensorStatus>();

    private final List<LedStatus> leds = new ArrayList<LedStatus>();

    private MotorStatus panMotor, tiltMotor, leftMotor, rightMotor;
    
    private WallConnectionStatus wallConnectonStatus= new WallConnectionStatus();

    private final DispatcherRobStatusListener dispatcherRobStatusListener = new DispatcherRobStatusListener();

    private final DispatcherStopWarningListener dispatcherStopWarningListener = new DispatcherStopWarningListener();

    private final DispatcherRobErrorListener dispatcherRobErrorListener = new DispatcherRobErrorListener();

    private StopWarningType lastStopWarning;

    private boolean roboboBaseSleep= false;

    
    public DefaultRob(IRobComm roboCom){
        
        if(roboCom==null){
            throw new NullPointerException("The parameter roboCom is required");
        }
        
        initObstacles();
        
        initBumps();
        
        initFalls();
        
        initGaps();
        
        initIrSensors();
        
        initMotors();

        initLeds();

        this.roboCom = roboCom;
        
        this.roboCom.addRobStatusListener(this);

        this.roboCom.addStopWarningListener(this);

        this.roboCom.addRobCommErrorListener(this);
        
    }
    
    private void initIrSensors() {
        for (IRSentorStatusId irSensor: IRSentorStatusId.values()) {
            irSensors.add(new IRSensorStatus(irSensor));
        }
    }

    private void initLeds() {
        for (LedStatus.LedStatusId led: LedStatus.LedStatusId.values()) {
            leds.add(new LedStatus(led));
        }
    }

    private void initMotors() {
        this.panMotor = new MotorStatus(MotorStatusId.Pan);
        this.tiltMotor = new MotorStatus(MotorStatusId.Tilt);
        this.leftMotor = new MotorStatus(MotorStatusId.Left);
        this.rightMotor = new MotorStatus(MotorStatusId.Right);        
    }

    private void initGaps() {

        for(GapStatusId id: GapStatusId.values()){
            gaps.add(new GapStatus(id));
        }

    }

    private void initFalls() {
        for(FallStatusId id: FallStatusId.values()){
            falls.add(new FallStatus(id));
        }
    }

    private void initObstacles() {
        for (ObstacleSensorStatusId id: ObstacleSensorStatusId.values()){
            obstacles.add(new ObstacleSensorStatus(id));
        }
    }

    private void initBumps() {

        for (BumpStatusId id: BumpStatusId.values()){
            bumps.add(new BumpStatus(id));
        }
    }


    

    @Override
    public void robStatus(RobStatusMessage robStatusMessage) {
        

        Date updateDate= new Date(System.currentTimeMillis());

        this.updateGaps(robStatusMessage, updateDate);

        this.updateIRSensorStatus(robStatusMessage, updateDate);

        this.updateFalls(robStatusMessage, updateDate);

        this.updateBumps(robStatusMessage, updateDate);

        this.updateObstacles(robStatusMessage, updateDate);

        this.updateAllMotorStatus(robStatusMessage, updateDate);

        this.updateBateryStatus(robStatusMessage, updateDate);
        
        this.updateWallConnection(robStatusMessage, updateDate);

    }


    private void updateWallConnection(RobStatusMessage robStatusMessage, Date updateDate) {
        
        this.wallConnectonStatus.setWallConnetion(robStatusMessage.getWallConnection());
        this.wallConnectonStatus.setLastUpdate(updateDate);
        
        this.dispatcherRobStatusListener.fireStatusWallConnection(wallConnectonStatus);
        
    }

    private void updateBateryStatus(RobStatusMessage robStatusMessage, Date updateDate) {

        int batteryLevel = robStatusMessage.getBatteryLevel();

        this.battery.setBattery(batteryLevel);

        this.battery.setLastUpdate(updateDate);
        
       this.dispatcherRobStatusListener.fireStatusBattery(battery);

    }

    private void updateGaps(RobStatusMessage robStatusMessage, Date updateDate) {

        byte gapsValue = robStatusMessage.getGaps();

        int indexGap=1;
        
        for (int i = 0; i < GapStatusId.values().length; i++) {

            
            
            byte gapValue = readBitAtPosition(gapsValue, indexGap);

            GapStatus gap = gaps.get(i);
            
            gap.setGap((gapValue == 1));
            
            gap.setLastUpdate(updateDate);
            
            indexGap+=2;

        }
        
        this.dispatcherRobStatusListener.fireStatusGaps(new ArrayList(this.gaps));

    }

    private void updateIRSensorStatus(RobStatusMessage robStatusMessage, Date updateDate) {

        int[] irs = robStatusMessage.getIrs();

        if ((irs == null) || (irs.length == 0)) {
            return;
        }
        
        List<IRSensorStatus> listIrSensorStatus= new ArrayList<IRSensorStatus>();

        for (int i = 0; i < irs.length; i++) {

            final IRSensorStatus irSensorStatus = irSensors.get(i);

            irSensorStatus.setDistance(irs[i]);
            
            irSensorStatus.setLastUpdate(updateDate);
            
            listIrSensorStatus.add(irSensorStatus);

        }
        
       this.dispatcherRobStatusListener.fireStatusIRSensorStatus(listIrSensorStatus);

    }
    

    private byte readBitAtPosition(byte value, int position) {
        byte mask = 0x1;

        byte readedBit = (byte) ((value >> position) & mask);

        return readedBit;
    }
    

    private void updateFalls(RobStatusMessage robStatusMessage, Date updateDate) {

        byte fallsValue = robStatusMessage.getFalls();

        int indexFall=1;
        
        for (int i = 0; i < FallStatusId.values().length; i++) {

            byte fallValue = readBitAtPosition(fallsValue, indexFall);

            FallStatus fall = falls.get(i);
            
            fall.setFall((fallValue == 1));
            
            fall.setLastUpdate(updateDate);
            
            indexFall+=2;

        }
        
        this.dispatcherRobStatusListener.fireStatusFalls(new ArrayList(this.falls));

    }

    private void updateBumps(RobStatusMessage robStatusMessage, Date updateDate) {

//        short[] bumpsValues = robStatusMessage.getBumps();
//
//        if ((bumpsValues == null) || (bumpsValues.length == 0)) {
//            return;
//        }
//
//        for (int i = 0; i < bumpsValues.length; i++) {
//
//            BumpStatus bump = bumps.get(i);
//
//            bump.setDistance(bumpsValues[i]);
//            
//            bump.setLastUpdate(updateDate);
//
//        }

    }

    private void updateObstacles(RobStatusMessage robStatusMessage, Date updateDate) {
        
//        short[] obstablesValues = robStatusMessage.getObstacles();
//        
//        if ((obstablesValues == null) || (obstablesValues.length == 0)) {
//            return;
//        }
//        
//        for (int i = 0; i < obstablesValues.length; i++) {
//            
//            ObstacleSensorStatus obstacle = obstacles.get(i);
//            
//            obstacle.setDistance(obstablesValues[i]);
//            
//            obstacle.setLastUpdate(updateDate);
//            
//        }
        
    }

    private void updateAllMotorStatus(RobStatusMessage roStatusMessage, Date updateDate) {

        int[] motorAngle = roStatusMessage.getMotorAngles();

        short[] motorVelocities = roStatusMessage.getMotorVelocities();

        int[] motorVoltages = roStatusMessage.getMotorVoltages();
        
        
        if ((motorAngle.length == MOTOR_COUNT) && (motorVelocities.length == MOTOR_COUNT)
                && (motorVoltages.length == MOTOR_COUNT)) {
            
            updateMotorStatus(panMotor, 0, motorAngle, motorVelocities, motorVoltages);
            updateMotorStatus(tiltMotor, 1, motorAngle, motorVelocities, motorVoltages);
            updateMotorStatus(leftMotor, 2, motorAngle, motorVelocities, motorVoltages);
            updateMotorStatus(rightMotor, 3, motorAngle, motorVelocities, motorVoltages);
            
        }
        
        this.dispatcherRobStatusListener.fireStatusMotorPan(panMotor);
        this.dispatcherRobStatusListener.fireStatusMotorTilt(tiltMotor);
        this.dispatcherRobStatusListener.fireStatusMotorsMT(leftMotor, rightMotor);

    }
    
    
    private void updateMotorStatus(
            MotorStatus ms, int index, 
            int[] motorAngle, short[] motorVelocities, int[] motorVoltages) {
        
        ms.setVariationAngle(motorAngle[index]);
        ms.setAngularVelocity(motorVelocities[index]);
        ms.setVoltage(motorVoltages[index]);
        
    }

    /**
     * Sets the color of a led
     * @param led Led index (1-7)
     * @param color Led color
     * @throws InternalErrorException
     */
    @Override
    public void setLEDColor(int led, Color color) throws CommunicationException, IllegalArgumentException {
        
        this.awakeRoboboBase();


        if ((color.getRed()>MAX_COLOR_VALUE)||(color.getGreen()>MAX_COLOR_VALUE)||(color.getBlue()>MAX_COLOR_VALUE)){
            throw new IllegalArgumentException("Invalid color");
        }
        LedStatus s ;
        try {
            s = new LedStatus(LedStatus.LedStatusId.values()[led-1]);

            s.setColor(color.getRed(), color.getGreen(), color.getBlue());
            this.dispatcherRobStatusListener.fireStatusLeds(s);

        } catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("Invalid led id");
        }

        this.roboCom.setLEDColor(led, color.getRed(), color.getGreen(), color.getBlue());



     
    }

    @Override
    public void setLEDsMode(LEDsModeEnum mode) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.setLEDsMode(mode.code);
   
    }

    /**
     * Moves the motors by degrees
     * @param angVelR Angular Speed of the right Motor
     * @param angleR Angle of the right Motor
     * @param angVelL Angular Speed of the left Motor
     * @param angleL Angle of the left Motor
     * @throws InternalErrorException
     */
    @Override
    public void moveMT( int angVelR, int angleR, int angVelL, int angleL) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.moveMT( limitAngVel(angVelL, MAX_ANG_VEL, MIN_ANG_VEL), angleL, limitAngVel(angVelR, MAX_ANG_VEL, MIN_ANG_VEL), angleR);
    }

    /**
     * Moves the motors by time
     * @param angVelR Angular speed of the right motor
     * @param angVelL Angular speed of the left motor
     * @param time Time in milliseconds
     * @throws InternalErrorException
     */
    @Override
    public void moveMT( int angVelR, int angVelL, long time) throws CommunicationException {

        this.awakeRoboboBase();
        
        this.roboCom.moveMT( limitAngVel(angVelL, MAX_ANG_VEL, MIN_ANG_VEL), limitAngVel(angVelR, MAX_ANG_VEL, MIN_ANG_VEL), time);
        
    }

    /**
     *
     * @param angVel the velocity (0-255)
     * @param angle  the angle (0-90)
     * @throws InternalErrorException
     */
    @Override
    public void movePan(int angVel, int angle) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.movePan(limitAngVel(angVel, MAX_PAN_ANG_VEL, MIN_PAN_ANG_VEL), limitAngle(angle, MAX_PAN_ANGLE, MIN_PAN_ANGLE));
    }



    @Override
    public void moveTilt(int angVel, int angle) throws CommunicationException {

        this.awakeRoboboBase();
        
        this.roboCom.moveTilt(limitAngVel(angVel, MAX_TILT_ANG_VEL, MIN_TILT_ANG_VEL), limitAngle(angle, MAX_TILT_ANGLE, MIN_TILT_ANGLE));
    
    }


    @Override
    public void resetPanTiltOffset() throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.resetPanTiltOffset();
    }
    
    @Override
    public void setRobStatusPeriod(int period) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.setRobStatusPeriod(period);

    }

    @Override
    public void setOperationMode(byte operationMode) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.setOperationMode(operationMode);

    }

    @Override
    public void configureInfrared(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.infraredConfiguration(infraredId, commandCode, dataByteLow, dataByteHigh);

    }


    @Override
    public void maxValueMotors(int m1Tension, int m1Time, int m2Tension, int m2Time, int panTension, int panTime,
            int tiltTension, int tiltTime) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.maxValueMotors(m1Tension, m1Time, m2Tension, m2Time, panTension, panTime, tiltTension, tiltTime);

    }

    @Override
    public void resetRob() throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.resetRob();
    }

    @Override
    public void changeRobBTName(String name) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.changeRobName(name);
    }

    @Override
    public void resetRobBTName() throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.changeRobName("");
    }

    @Override
    public void resetWheelEncoders(RobMotorEnum motor) throws CommunicationException {

        this.awakeRoboboBase();

        this.roboCom.resetWheelEncoders(motor);
    }

    @Override
    public List<MotorStatus> getLastStatusMotors() {
        ArrayList<MotorStatus> motors = new ArrayList<MotorStatus>();
        motors.add(panMotor);
        motors.add(tiltMotor);
        motors.add(leftMotor);
        motors.add(rightMotor);
        return motors;
    }

    @Override
    public List<IRSensorStatus> getLastStatusIRs() {
        return new ArrayList<IRSensorStatus>(this.irSensors);
    }

    @Override
    public List<GapStatus> getLastGapsStatus() {
        return new ArrayList<GapStatus>(this.gaps);
    }

    @Override
    public List<FallStatus> getLastStatusFalls() {
        return new ArrayList<FallStatus>(this.falls);
    }

    @Override
    public List<BumpStatus> getLastStatusBumps() {
        return new ArrayList<BumpStatus>(this.bumps);
    }

    @Override
    public List<ObstacleSensorStatus> getLastStatusObstacles() {
        return new ArrayList<ObstacleSensorStatus>(this.obstacles);
    }

    @Override
    public BatteryStatus getLastStatusBattery() {
        return this.battery;
    }

    @Override
    public StopWarningType getLastStopWarning() {
        return lastStopWarning;
    }

    @Override
    public void addRobStatusListener(IRobStatusListener listener) {
        dispatcherRobStatusListener.subscribetoContentChanges(listener);
    }

    @Override
    public void addStopWarningListener(IStopWarningListener listener) {
        dispatcherStopWarningListener.subscribetoStopWarnings(listener);
    }

    @Override
    public void addRobErrorListener(IRobErrorListener listener) {
        this.dispatcherRobErrorListener.subscribeToRobError(listener);
    }

    @Override
    public void removeStopWarningListener(IStopWarningListener listener) {
        dispatcherStopWarningListener.unsubscribeFromStopWarnings(listener);

    }

    @Override
    public void removeRobStatusListener(IRobStatusListener listener) {
        dispatcherRobStatusListener.unsubscribeFromContentChanges(listener);
    }

    @Override
    public void removeRobErrorListener(IRobErrorListener listener) {
        this.dispatcherRobErrorListener.unsubscribeFromRobError(listener);
    }


    private void awakeRoboboBase() throws CommunicationException {

        if(lastStopWarning==null){
            return;
        }

        if(this.roboboBaseSleep){
            this.roboboBaseSleep=false;
            this.roboCom.moveMT((short)0,(short)0,(long)0);
        }

    }


    private short limitAngVel(int angVel, short max, short min) {
        if (angVel > max)
            return max;
        else if (angVel < min)
            return min;
        else        
            return (short) angVel;
    }

    
    private int limitAngle(int angle, int max, int min) {
        if (angle > max) {
            return max;
        }
        else if (angle < min) {
            return min;
        }
        else {
            return angle;
        }
    }

    private int calcBattery(int value){

        if (value > max_battery){
            max_battery = value;
        }else if (value < min_battery){
            min_battery = value;
        }

        return Math.round(((float)(value-min_battery)/(float)(max_battery-min_battery))*100.0f);

    }



    @Override
    public void stopWarning(StopWarningMessage sw) {
        lastStopWarning = sw.getMessage();

        if((lastStopWarning!=null) && (lastStopWarning==StopWarningType.SLEEP_WARNING)){
            roboboBaseSleep=true;
        }

        dispatcherStopWarningListener.fireStatusBattery(sw);

    }


    @Override
    public void robError(CommunicationException ex) {
        dispatcherRobErrorListener.fireRobCommError(ex);
    }
}
