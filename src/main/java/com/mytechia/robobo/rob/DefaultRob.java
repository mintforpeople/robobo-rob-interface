/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */

package com.mytechia.robobo.rob;

import static com.mytechia.robobo.rob.BumpStatus.BumpStatusId;
import static com.mytechia.robobo.rob.FallStatus.FallStatusId;
import static com.mytechia.robobo.rob.GapStatus.GapStatusId;
import static com.mytechia.robobo.rob.IRSensorStatus.IRSentorStatusId;
import static com.mytechia.robobo.rob.MotorStatus.MotorStatusId;
import static com.mytechia.robobo.rob.ObstacleSensorStatus.ObstacleSensorStatusId;
import com.mytechia.robobo.rob.comm.IRobComm;
import com.mytechia.robobo.rob.comm.IRobCommStatusListener;
import com.mytechia.robobo.rob.comm.RobStatusMessage;
import com.mytechia.robobo.util.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DefaultRob implements IRobCommStatusListener, IRob {
    
    private static final Logger LOGGER= LoggerFactory.getLogger(DefaultRob.class);

    private static final int MOTOR_COUNT = 4;
    
    private IRobComm roboCom;

    private BatteryStatus battery= new BatteryStatus();

    private final List<ObstacleSensorStatus> obstacles = new ArrayList<ObstacleSensorStatus>();

    private final List<BumpStatus> bumps = new ArrayList<BumpStatus>();

    private final List<FallStatus> falls = new ArrayList<FallStatus>();

    private final List<GapStatus> gaps = new ArrayList<GapStatus>();

    private final List<IRSensorStatus> irSensors = new ArrayList<IRSensorStatus>();

    private MotorStatus panMotor, tiltMotor, leftMotor, rightMotor;

    private final DispatcherRobStatusListener dispatcherRobStatusListener = new DispatcherRobStatusListener();
    
    
    
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

        this.roboCom = roboCom;
        
        this.roboCom.addRobStatusListener(this);
        
        
    }
    
    private void initIrSensors() {
        for (IRSentorStatusId irSensor: IRSentorStatusId.values()) {
            irSensors.add(new IRSensorStatus(irSensor));
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
        
        LOGGER.trace("Received robStatusMessage");
        
        Date updateDate= new Date(System.currentTimeMillis());

        this.updateGaps(robStatusMessage, updateDate);

        this.updateIRSensorStatus(robStatusMessage, updateDate);

        this.updateFalls(robStatusMessage, updateDate);

        this.updateBumps(robStatusMessage, updateDate);

        this.updateObstacles(robStatusMessage, updateDate);

        this.updateAllMotorStatus(robStatusMessage, updateDate);

        this.updateBateryStatus(robStatusMessage, updateDate);

    }

    private void updateBateryStatus(RobStatusMessage roStatusMessage, Date updateDate) {

        int bateryLevel=roStatusMessage.getBateryLevel();

        this.battery.setBattery(bateryLevel);

        this.battery.setLastUpdate(updateDate);

    }

    private void updateGaps(RobStatusMessage robStatusMessage, Date updateDate) {

        byte gapsValue = robStatusMessage.getGaps();

        for (int i = 0; i < GapStatusId.values().length; i++) {

            byte gapValue = readBitAtPosition(gapsValue, i);

            GapStatus gap = gaps.get(i);
            
            gap.setGap((gapValue == 1));
            
            gap.setLastUpdate(updateDate);

        }
        
        this.dispatcherRobStatusListener.fireStatusGaps(new ArrayList(this.gaps));

    }

    private void updateIRSensorStatus(RobStatusMessage robStatusMessage, Date updateDate) {

        short[] irs = robStatusMessage.getIrs();

        if ((irs == null) || (irs.length == 0)) {
            return;
        }

        for (int i = 0; i < irs.length; i++) {

            final IRSensorStatus irSensorStatus = irSensors.get(i);

            irSensorStatus.setDistance(irs[i]);
            
            irSensorStatus.setLastUpdate(updateDate);

        }
        
        

    }
    

    private byte readBitAtPosition(byte value, int position) {
        byte mask = 0x1;

        byte readedBit = (byte) ((value >> position) & mask);

        return readedBit;
    }
    

    private void updateFalls(RobStatusMessage robStatusMessage, Date updateDate) {

        byte fallsValue = robStatusMessage.getFalls();

        for (int i = 0; i < FallStatusId.values().length; i++) {

            byte fallValue = readBitAtPosition(fallsValue, i);

            FallStatus fall = falls.get(i);
            
            fall.setFall((fallValue == 1));
            
            fall.setLastUpdate(updateDate);

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
    

    @Override
    public void setLEDColor(int led, Color color) {
        this.roboCom.setLEDColor(led, color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public void setLEDsMode(LEDsModeEnum mode) {
        this.roboCom.setLEDsMode(mode.code);
    }

    @Override
    public void moveMT(MoveMTMode mode, short angVel1, int angle1, short angVel2, int angle2) {
        this.roboCom.moveMT(mode.getMode(), angVel1, angle1, angVel2, angle2);
    }

    @Override
    public void moveMT(MoveMTMode mode, short angVel1, short angVel2, long time) {
        this.roboCom.moveMT(mode.getMode(), angVel1, angVel2, time);
    }

    @Override
    public void movePan(short angVel, int angle) {
        this.roboCom.movePan(angVel, angle);
    }

    @Override
    public void movePan(short angVel, long time) {
        this.roboCom.movePan(angVel, time);
    }

    @Override
    public void moveTilt(short angVel, int angle) {
        this.roboCom.moveTilt(angVel, angle);
    }

    @Override
    public void moveTilt(short angVel, long time) {
        this.roboCom.moveTilt(angVel, time);
    }

    @Override
    public void resetPanTiltOffset() {
        this.roboCom.resetPanTiltOffset();
    }
    
    @Override
    public void setRobStatusPeriod(int period) {
        this.roboCom.setRobStatusPeriod(period);
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
    public void addRobStatusListener(IRobStatusListener listener) {
        dispatcherRobStatusListener.subscribetoContentChanges(listener);
    }

    @Override
    public void removeRobStatusListener(IRobStatusListener listener) {
        dispatcherRobStatusListener.unsubscribeFromContentChanges(listener);
    }






}
