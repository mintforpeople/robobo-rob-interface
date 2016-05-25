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

import java.util.List;

import static com.mytechia.robobo.rob.BumpStatus.*;
import static com.mytechia.robobo.rob.IRSensorStatus.*;
import static com.mytechia.robobo.rob.MotorStatus.*;
import static com.mytechia.robobo.rob.ObstacleSensorStatus.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 *
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class DefaultRoboTest {


    private RobStatusMessage createDefaultRoboStatusMessage(){

        byte gaps= 0;

        byte falls= 0;

        short[] irs= new short[IRSentorStatusId.values().length];

        short[] obstacles= new short[ObstacleSensorStatusId.values().length];

        short[] bumps= new short[BumpStatusId.values().length];

        short[] motorVelocities= new short[MotorStatusId.values().length];

        int[] motorAngles= new  int[MotorStatusId.values().length];

        int[] motorVoltages= new int[MotorStatusId.values().length];

         byte wallConnection=0;

         short batteryInformation=0;


        RobStatusMessage originalMessage =new RobStatusMessage(gaps,
                                                                falls,
                                                                irs,
                                                                obstacles,
                                                                bumps,
                                                                motorVelocities,
                                                                motorAngles,
                                                                motorVoltages,
                                                                wallConnection,
                                                                batteryInformation);

        return originalMessage;

    }

    DefaultRob createDefaultRoboCom() {

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        return new DefaultRob(smpRoboCom);
    }

    @Test
    public void gapsMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        byte gaps= 57;

        statusMessage.setGaps(gaps);

        defaultRob.robStatus(statusMessage);

        List<GapStatus> gapsStatus = defaultRob.getLastGapsStatus();

        assertFalse(gapsStatus.get(3).isGap());
        assertTrue(gapsStatus.get(2).isGap());
        assertTrue(gapsStatus.get(1).isGap());
        assertFalse(gapsStatus.get(0).isGap());


    }



    @Test
    public void iRSensorStatusMustBeUpdatedWhenRobStatusMessageArrive(){

        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        short[] irs=statusMessage.getIrs();

        irs[2]= 287;
        irs[5]= 4567;
        irs[7]= 789;

        short[] expectedIRSDistance= new short[IRSentorStatusId.values().length];
        expectedIRSDistance[2]= 287;
        expectedIRSDistance[5]= 4567;
        expectedIRSDistance[7]=789;


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

        byte falls= (byte)185;

        statusMessage.setFalls(falls);

        defaultRob.robStatus(statusMessage);

        List<FallStatus> lastStatusFalls = defaultRob.getLastStatusFalls();

        assertTrue(lastStatusFalls.get(3).isFall());
        assertTrue(lastStatusFalls.get(2).isFall());
        assertTrue(lastStatusFalls.get(1).isFall());
        assertFalse(lastStatusFalls.get(0).isFall());

    }


    @Test
    public void bumpsMustBeUpdatedWhenRobStatusMessageArrive(){

//        DefaultRob defaultRob= createDefaultRoboCom();
//
//        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();
//
//        short[] bumps=statusMessage.getBumps();
//        bumps[0]= 0;
//        bumps[1]= 1;
//        bumps[2]= 498;
//        bumps[3]= 687;
//
//        short[] expectedBumpDistance= new short[BumpStatusId.values().length];
//        expectedBumpDistance[0]= 0;
//        expectedBumpDistance[1]= 1;
//        expectedBumpDistance[2]= 498;
//        expectedBumpDistance[3]= 687;
//
//
//        defaultRob.robStatus(statusMessage);
//
//        List<BumpStatus> bumpStatuses = defaultRob.getLastStatusBumps();
//
//        for (int i=0; i< bumpStatuses.size(); i++){
//
//            BumpStatus bumpStatus = bumpStatuses.get(i);
//
//            double expectedBumpStatusDistance= expectedBumpDistance[i];
//
//            assertTrue(expectedBumpStatusDistance== bumpStatus.getDistance());
//
//        }


    }


    @Test
    @Ignore
    public void obstaclesMustBeUpdatedWhenRobStatusMessageArrive(){

//        DefaultRob defaultRob= createDefaultRoboCom();
//
//        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();
//
//        short[] obstacles=statusMessage.getObstacles();
//        obstacles[0]= 1;
//        obstacles[2]= 287;
//        obstacles[5]= 4567;
//        obstacles[6]= 789;
//
//        short[] expectedObstacleStatusDistance= new short[ObstacleSensorStatusId.values().length];
//        expectedObstacleStatusDistance[0]= 1;
//        expectedObstacleStatusDistance[2]= 287;
//        expectedObstacleStatusDistance[5]= 4567;
//        expectedObstacleStatusDistance[6]= 789;
//
//        //Simulamos que recibimos el mensaje status
//        defaultRob.robStatus(statusMessage);
//
//        List<ObstacleSensorStatus> obstacleSensorStatusList = defaultRob.getLastStatusObstacles();
//
//        for (int i=0; i< obstacleSensorStatusList.size(); i++){
//
//            ObstacleSensorStatus obstacleSensorStatus = obstacleSensorStatusList.get(i);
//
//            double expectedObstacleSensorDistance= expectedObstacleStatusDistance[i];
//
//            assertTrue(expectedObstacleSensorDistance== obstacleSensorStatus.getDistance());
//
//        }

    }

    @Test
    @Ignore
    public void bateryMustBeUpdatedWhenRobStatusMessageArrive(){


        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        int exceptedBateryLevel=10;

        //statusMessage.setBaterryLevel(exceptedBateryLevel);

        //Simulamos la llegada el mensaje de estado
        defaultRob.robStatus(statusMessage);

        int actualBateryLevel=defaultRob.getLastStatusBattery().getBattery();

        assertEquals(actualBateryLevel, exceptedBateryLevel);

    }


    @Test
    public void motorMustBeUpdatedWhenRobStatusMessageArrive(){


        DefaultRob defaultRob= createDefaultRoboCom();

        RobStatusMessage statusMessage = createDefaultRoboStatusMessage();

        int[] motorAngles=statusMessage.getMotorAngles();
        motorAngles[0]= 1;
        motorAngles[1]= 287;
        motorAngles[2]= 4567;
        motorAngles[3]= 789;

        short[] motorVelocities=statusMessage.getMotorVelocities();
        motorVelocities[0]= 1;
        motorVelocities[1]= 976;
        motorVelocities[2]= 287;
        motorVelocities[3]= 4567;

        int[] motorVoltage=statusMessage.getMotorVoltages();
        motorVoltage[0]= 165;
        motorVoltage[1]= 376;
        motorVoltage[2]= 187;
        motorVoltage[3]= 34;

        int[] expectedVariationAngle= new int[MotorStatusId.values().length];
        expectedVariationAngle[0]= 1;
        expectedVariationAngle[1]= 287;
        expectedVariationAngle[2]= 4567;
        expectedVariationAngle[3]= 789;

        int[] expectedAngularVelocities= new int[MotorStatusId.values().length];
        expectedAngularVelocities[0]= 1;
        expectedAngularVelocities[1]= 976;
        expectedAngularVelocities[2]= 287;
        expectedAngularVelocities[3]= 4567;


        int[] expectedVoltage= new int[MotorStatusId.values().length];
        expectedVoltage[0]= 165;
        expectedVoltage[1]= 376;
        expectedVoltage[2]= 187;
        expectedVoltage[3]= 34;

        //Ejercitamos el SUT
        defaultRob.robStatus(statusMessage);

        List<MotorStatus> statusMotorsList = defaultRob.getLastStatusMotors();

        for (int i=0; i< statusMotorsList.size(); i++){

            MotorStatus motorStatus= statusMotorsList.get(i);

            //assertTrue(expectedVariationAngle[i]/10000== motorStatus.getVariationAngle());

            assertTrue(expectedAngularVelocities[i]== motorStatus.getAngularVelocity());

            assertTrue(expectedVoltage[i]== motorStatus.getVoltage());

        }
    }

    @Test
    public void testLEDColor() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

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

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);


        LEDsModeEnum mode= LEDsModeEnum.INFRARED_AND_DETECT_FALL;

        defaultRob.setLEDsMode(mode);

        RobSetLEDsModeMessage setLedColorMessage= new RobSetLEDsModeMessage(mode.code);

        verify(communicationChannel).send(setLedColorMessage);


    }


    @Test
    public void testMoveMTAngles() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        byte mode = MoveMTMode.FORWARD_FORWARD.getMode();
        byte angVel1=101;
        short angle1=66;
        byte angVel2=127;
        short angle2= 45;

        defaultRob.moveMT(MoveMTMode.FORWARD_FORWARD, angVel1, angle1, angVel2, angle2);

        MoveMTMessage moveMTMessage= new MoveMTMessage(mode, angVel1, angle1*10000, angVel2, angle2*10000, 0);

        verify(communicationChannel).send(moveMTMessage);

    }

    @Test
    public void testMoveMTTimes() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        byte mode = MoveMTMode.FORWARD_FORWARD.getMode();
        byte angVel1=101;
        byte angVel2=50;
        long time=60;

        defaultRob.moveMT(MoveMTMode.FORWARD_FORWARD, angVel1, angVel2, time);

        MoveMTMessage moveMTMessage= new MoveMTMessage(mode, angVel1, 0, angVel2, 0, time);

        verify(communicationChannel).send(moveMTMessage);

    }





    @Test
    @Ignore
    public void testMovePanAngles() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        short angVel=120;
        short angle=76;

        defaultRob.movePan(angVel, angle);

        OldMovePanTiltMessage movePanTiltMessage= new OldMovePanTiltMessage((byte)0, angVel, angle, 0);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    @Ignore
    public void testMoveTiltAngles() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        short angVel=120;
        short angle=76;

        defaultRob.moveTilt(angVel, angle);

        OldMovePanTiltMessage movePanTiltMessage= new OldMovePanTiltMessage((byte)1, angVel, angle*10000, 0);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    @Ignore
    public void testMovePanTime() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        short angVel=10;
        long time=45;

        //defaultRob.movePan(angVel, time);

        OldMovePanTiltMessage movePanTiltMessage= new OldMovePanTiltMessage((byte)0, angVel, 0, (int) time);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    @Ignore
    public void testMoveTiltTime() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        short angVel=16;
        long time=45;

        //defaultRob.moveTilt(angVel, time);

        OldMovePanTiltMessage movePanTiltMessage= new OldMovePanTiltMessage((byte)1, angVel, 0, (int) time);

        verify(communicationChannel).send(movePanTiltMessage);

    }

    @Test
    public void testResetPanTiltOffset() throws Exception{

        IBasicCommunicationChannel communicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRoboCom = new SmpRobComm(communicationChannel, new RoboCommandFactory());

        DefaultRob defaultRob= new DefaultRob(smpRoboCom);

        defaultRob.resetPanTiltOffset();

        verify(communicationChannel).send(any(ResetPanTiltOffsetMessage.class));

    }



}
