/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import com.mytechia.commons.framework.simplemessageprotocol.channel.IBasicCommunicationChannel;
import com.mytechia.robobo.rob.comm.*;
import com.mytechia.robobo.util.Color;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.mytechia.robobo.rob.BumpStatus.*;
import static com.mytechia.robobo.rob.IRSensorStatus.*;
import static com.mytechia.robobo.rob.MotorStatus.*;
import static com.mytechia.robobo.rob.ObstacleSensorStatus.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class DefaultRoboTest {


    private RobStatusMessage createDefaultRoboStatusMessage(){

        byte gaps= 0b0000_0000;

        byte falls= 0b0000_0000;

        double[] irs= new double[IRSentorStatusId.values().length];

        double[] obstacles= new double[ObstacleSensorStatusId.values().length];

        double[] bumps= new double[BumpStatusId.values().length];

        double[] motorVelocities= new double[MotorStatusId.values().length];

        double[] motorAngles= new  double[MotorStatusId.values().length];

        double[] motorVoltages= new double[MotorStatusId.values().length];

        int bateryLevel= 0;

        boolean dockConnection= false;


        RobStatusMessage originalMessage =new RobStatusMessage(gaps,
                                                                falls,
                                                                irs,
                                                                obstacles,
                                                                bumps,
                                                                motorVelocities,
                                                                motorAngles,
                                                                motorVoltages,
                                                                bateryLevel,
                                                                dockConnection);

        return originalMessage;

    }

    DefaultRob createDefaultRoboCom() {

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        return new DefaultRob(smpRoboCom);
    }

    @Test
    public void gapsMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        byte gaps= 0b0011_1001;

        statusMessage.setGaps(gaps);

        defaultRob.robStatus(statusMessage);

        List<GapStatus> gapsStatus = defaultRob.getLastGapsStatus();

        assertFalse(gapsStatus.get(7).isGap());
        assertFalse(gapsStatus.get(6).isGap());
        assertTrue(gapsStatus.get(5).isGap());
        assertTrue(gapsStatus.get(4).isGap());
        assertTrue(gapsStatus.get(3).isGap());
        assertFalse(gapsStatus.get(2).isGap());
        assertFalse(gapsStatus.get(1).isGap());
        assertTrue(gapsStatus.get(0).isGap());


    }



    @Test
    public void iRSensorStatusMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        double[] irs=statusMessage.getIrs();

        irs[2]= 2.87;
        irs[5]= (double) 4567;
        irs[7]= (double) 7.89;

        double[] expectedIRSDistance= new double[IRSentorStatusId.values().length];
        expectedIRSDistance[2]= 2.87;
        expectedIRSDistance[5]= 4567;
        expectedIRSDistance[7]=(double) 7.89;


        defaultRob.robStatus(statusMessage);

        List<IRSensorStatus> statusIrs = defaultRob.getLastStatusIRs();

        for (int i=0; i< statusIrs.size(); i++){

            IRSensorStatus irSensorStatus = statusIrs.get(i);

            double expectedIRSensorStatusDistance= expectedIRSDistance[i];

            assertTrue(expectedIRSensorStatusDistance== irSensorStatus.getDistance());

        }


    }


    @Test
    public void fallsMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        byte falls= (byte)0b1011_1001;

        statusMessage.setFalls(falls);

        defaultRob.robStatus(statusMessage);

        List<FallStatus> lastStatusFalls = defaultRob.getLastStatusFalls();

        assertTrue(lastStatusFalls.get(7).isFall());
        assertFalse(lastStatusFalls.get(6).isFall());
        assertTrue(lastStatusFalls.get(5).isFall());
        assertTrue(lastStatusFalls.get(4).isFall());
        assertTrue(lastStatusFalls.get(3).isFall());
        assertFalse(lastStatusFalls.get(2).isFall());
        assertFalse(lastStatusFalls.get(1).isFall());
        assertTrue(lastStatusFalls.get(0).isFall());

    }


    @Test
    public void bumpsMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        double[] bumps=statusMessage.getBumps();
        bumps[0]= 0;
        bumps[1]= 0.1;
        bumps[2]= 4.98;
        bumps[3]= 6.87;

        double[] expectedBumpDistance= new double[BumpStatusId.values().length];
        expectedBumpDistance[0]= 0;
        expectedBumpDistance[1]= 0.1;
        expectedBumpDistance[2]= 4.98;
        expectedBumpDistance[3]= 6.87;


        defaultRob.robStatus(statusMessage);

        List<BumpStatus> bumpStatuses = defaultRob.getLastStatusBumps();

        for (int i=0; i< bumpStatuses.size(); i++){

            BumpStatus bumpStatus = bumpStatuses.get(i);

            double expectedBumpStatusDistance= expectedBumpDistance[i];

            assertTrue(expectedBumpStatusDistance== bumpStatus.getDistance());

        }


    }


    @Test
    public void obstaclesMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        double[] obstacles=statusMessage.getObstacles();
        obstacles[0]= 1;
        obstacles[2]= 2.87;
        obstacles[5]= 4567;
        obstacles[6]= 7.89;

        double[] expectedObstacleStatusDistance= new double[ObstacleSensorStatusId.values().length];
        expectedObstacleStatusDistance[0]= 1;
        expectedObstacleStatusDistance[2]= 2.87;
        expectedObstacleStatusDistance[5]= 4567;
        expectedObstacleStatusDistance[6]= 7.89;

        //Simulamos que recibimos el mensaje status
        defaultRob.robStatus(statusMessage);

        List<ObstacleSensorStatus> obstacleSensorStatusList = defaultRob.getLastStatusObstacles();

        for (int i=0; i< obstacleSensorStatusList.size(); i++){

            ObstacleSensorStatus obstacleSensorStatus = obstacleSensorStatusList.get(i);

            double expectedObstacleSensorDistance= expectedObstacleStatusDistance[i];

            assertTrue(expectedObstacleSensorDistance== obstacleSensorStatus.getDistance());

        }

    }

    @Test
    public void bateryMustBeUpdatedWhenRobStatusMessageArrive(){


        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        int exceptedBateryLevel=10;

        statusMessage.setBaterryLevel(exceptedBateryLevel);

        //Simulamos la llegada el mensaje de estado
        defaultRob.robStatus(statusMessage);

        int actualBateryLevel=defaultRob.getLastStatusBattery().getBattery();

        assertEquals(actualBateryLevel, exceptedBateryLevel);

    }


    @Test
    public void motorMustBeUpdatedWhenRobStatusMessageArrive(){


        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        double[] motorAngles=statusMessage.getMotorAngles();
        motorAngles[0]= 1;
        motorAngles[1]= 2.87;
        motorAngles[2]= 4567;
        motorAngles[3]= 7.89;

        double[] motorVelocities=statusMessage.getMotorVelocities();
        motorVelocities[0]= 1;
        motorVelocities[1]= 9.76;
        motorVelocities[2]= 2.87;
        motorVelocities[3]= 4567;

        double[] motorVoltage=statusMessage.getMotorVoltages();
        motorVoltage[0]= 1.65;
        motorVoltage[1]= 3.76;
        motorVoltage[2]= 1.87;
        motorVoltage[3]= 34;

        double[] expectedVariationAngle= new double[MotorStatusId.values().length];
        expectedVariationAngle[0]= 1;
        expectedVariationAngle[1]= 2.87;
        expectedVariationAngle[2]= 4567;
        expectedVariationAngle[3]= 7.89;

        double[] expectedAngularVelocities= new double[MotorStatusId.values().length];
        expectedAngularVelocities[0]= 1;
        expectedAngularVelocities[1]= 9.76;
        expectedAngularVelocities[2]= 2.87;
        expectedAngularVelocities[3]= 4567;


        double[] expectedVoltage= new double[MotorStatusId.values().length];
        expectedVoltage[0]= 1.65;
        expectedVoltage[1]= 3.76;
        expectedVoltage[2]= 1.87;
        expectedVoltage[3]= 34;

        //Ejercitamos el SUT
        defaultRob.robStatus(statusMessage);

        List<MotorStatus> statusMotorsList = defaultRob.getLastStatusMotors();

        for (int i=0; i< statusMotorsList.size(); i++){

            MotorStatus motorStatus= statusMotorsList.get(i);

            assertTrue(expectedVariationAngle[i]== motorStatus.getVariationAngle());

            assertTrue(expectedAngularVelocities[i]== motorStatus.getAngularVelocity());

            assertTrue(expectedVoltage[i]== motorStatus.getVoltage());

        }
    }

    @Test
    public void testLEDColor() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        int led=23;
        int red=123;
        int green=4090;
        int blue=300;

        defaultRob.setLEDColor(led, new Color(red, green, blue));

        SetLEDColorMessage setLedColorMessage= new SetLEDColorMessage((byte) led, (short)red, (short)green, (short)blue);

        verify(communicationChannel).send(setLedColorMessage);


    }

    @Test
    public void testLEDMode() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);


        LEDsModeEnum mode= LEDsModeEnum.FALL;

        defaultRob.setLEDsMode(mode);

        RobSetLEDsModeMessage setLedColorMessage= new RobSetLEDsModeMessage(mode.code);

        verify(communicationChannel).send(setLedColorMessage);


    }


    @Test
    public void testMoveMTAngles() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        double angVel1=10.1;
        double angle1=66;
        double angVel2=67.1;
        double angle2= 45;

        defaultRob.moveMT(angVel1, angle1, angVel2, angle2);

        MoveMTMessage moveMTMessage= new MoveMTMessage(angVel1, angle1, angVel2, angle2, 0);

        verify(communicationChannel).send(moveMTMessage);

    }

    @Test
    public void testMoveMTTimes() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        double angVel1=10.1;
        double angVel2=67.1;
        long time=60;

        defaultRob.moveMT(angVel1, angVel2, time);

        MoveMTMessage moveMTMessage= new MoveMTMessage(angVel1, 0, angVel2, 0, time);

        verify(communicationChannel).send(moveMTMessage);

    }





    @Test
    public void testMovePanAngles() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        double angVel=120.1;
        double angle=76;

        defaultRob.movePan(angVel, angle);

        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)0, angVel, angle, 0);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    public void testMoveTiltAngles() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        double angVel=120.1;
        double angle=76;

        defaultRob.moveTilt(angVel, angle);

        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)1, angVel, angle, 0);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    public void testMovePanTime() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        double angVel=10.1;
        long time=45;

        defaultRob.movePan(angVel, time);

        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)0, angVel, 0, time);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    public void testMoveTiltTime() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        double angVel=16.1;
        long time=45;

        defaultRob.moveTilt(angVel, time);

        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)1, angVel, 0, time);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    public void testResetPanTiltOffset() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel);

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        defaultRob.resetPanTiltOffset();

        verify(communicationChannel).send(any(ResetPanTiltOffsetMessage.class));

    }



}
