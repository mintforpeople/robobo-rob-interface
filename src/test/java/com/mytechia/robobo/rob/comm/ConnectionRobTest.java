/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.mytechia.robobo.rob.comm.RoboCommand.DEFAULT_MAX_NUMBER_TRANSMISSIONS;
import static com.mytechia.robobo.rob.comm.RoboCommandTest.WAITING_TIME;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * This class checks if the send message were lost.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class ConnectionRobTest {


    @Test
    public void returnTrueIfThereIsCommandSameSeqNum(){

        ConnectionRob connectionRob= new ConnectionRob();

        for (int i = 0; i < 10; i++) {
            RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
            roboCommand.setSequenceNumber(i);
            connectionRob.addSentRoboCommand(roboCommand);
        }

        AckMessage ackMessage= new AckMessage();
        ackMessage.setSequenceNumber(4);

        boolean receivedAck=connectionRob.receivedAck(ackMessage);

        assertTrue(receivedAck);


    }

    @Test
    public void returnFalseIfThereIsNoCommandSameSeqNum(){

        ConnectionRob connectionRob= new ConnectionRob();

        for (int i = 0; i < 10; i++) {
            RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
            roboCommand.setSequenceNumber(i);
            connectionRob.addSentRoboCommand(roboCommand);
        }

        AckMessage ackMessage= new AckMessage();
        ackMessage.setSequenceNumber(20);

        boolean notReceivedAck=connectionRob.receivedAck(ackMessage);

        assertFalse(notReceivedAck);


    }

    @Test
    public void returnFalseIfAlreadyReceivedAck(){

        ConnectionRob connectionRob= new ConnectionRob();

        for (int i = 0; i < 10; i++) {
            RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
            roboCommand.setSequenceNumber(i);
            connectionRob.addSentRoboCommand(roboCommand);
        }

        AckMessage ackMessage= new AckMessage();
        ackMessage.setSequenceNumber(5);

        connectionRob.receivedAck(ackMessage);

        boolean notReceivedAck=connectionRob.receivedAck(ackMessage);

        assertFalse(notReceivedAck);


    }


    @Test
    public void returnAllLostCommands(){

        ConnectionRob connectionRob= new ConnectionRob();

        /*
        for (int i = 0; i < 10; i++) {
            RoboCommand roboCommand= createRoboCommand(i);
        }*/

        List<RoboCommand> expectedLostRoboCommands= new ArrayList<RoboCommand>();
        for (int i = 0; i < 3; i++) {

            //Un numero de secuencia que no coincida con cualquiera de los numeros secuencia de los
            //mensajes anteriores
            RoboCommand lostRoboCommand= createRoboCommand((30+i));

            //se asigna un tiempo de transmission lo suficientemente antiguo para que el tiempo
            //de espera del mensaje sea superado
            lostRoboCommand.setLastTransmissionTime(generateExcededWaitingTime(lostRoboCommand.getWaitingTimeAck()));

            //Simulamos que hemos alcanzado el numero de transmisiones maximo
            lostRoboCommand.numberTransmissions= DEFAULT_MAX_NUMBER_TRANSMISSIONS;

            connectionRob.addSentRoboCommand(lostRoboCommand);

            expectedLostRoboCommands.add(lostRoboCommand);
        }

        //Ejercemos el SUT
        List<RoboCommand> lostRoboCommands = connectionRob.checkLostRoboCommands();

        assertTrue(lostRoboCommands.size()==expectedLostRoboCommands.size());

        boolean returnAllLostCommands=expectedLostRoboCommands.containsAll(lostRoboCommands);

        assertTrue(returnAllLostCommands);

    }


    //@Test
    public void notReturnLostCommandIfNotExceededWaitingTimeAck() {

        ConnectionRob connectionRob = new ConnectionRob();

        for (int i = 0; i < 10; i++) {
            RoboCommand roboCommand = createRoboCommand(i);
            connectionRob.addSentRoboCommand(roboCommand);
        }


        for (int i = 0; i < 3; i++) {

            //Un numero de secuencia que no coincida con cualquiera de los numeros secuencia de los
            //mensajes anteriores
            RoboCommand lostRoboCommand = createRoboCommand((30+i));

            long lastTransmissionTime = generateNotExcededWaitingTime(lostRoboCommand.getWaitingTimeAck());
            lostRoboCommand.setLastTransmissionTime(lastTransmissionTime);

            //Simulamos que hemos alcanzado el numero maximo de transmisiones
            lostRoboCommand.numberTransmissions = lostRoboCommand.getMaxNumTransmissions();

            connectionRob.addSentRoboCommand(lostRoboCommand);

        }

        List<RoboCommand> lostRoboCommands = connectionRob.checkLostRoboCommands();

        assertTrue(lostRoboCommands.isEmpty());
    }


    @Test
    public void returnAllResendCommandsWhenExceededWaitingTimeAck(){

        ConnectionRob connectionRob = new ConnectionRob();

        for (int i = 0; i < 10; i++) {
            RoboCommand roboCommand = createRoboCommand(i);
            roboCommand.setMaxNumTransmissions(DEFAULT_MAX_NUMBER_TRANSMISSIONS);
            connectionRob.addSentRoboCommand(roboCommand);
        }

        int numResendCommands=DEFAULT_MAX_NUMBER_TRANSMISSIONS;

        for (int i = 0; i < numResendCommands; i++) {

            //Un numero de secuencia que no coincida con cualquiera de los numeros secuencia de los
            //mensajes anteriores
            RoboCommand lostRoboCommand = createRoboCommand((30 + i));
            lostRoboCommand.setMaxNumTransmissions(DEFAULT_MAX_NUMBER_TRANSMISSIONS);

            long lastTransmissionTime = generateExcededWaitingTime(lostRoboCommand.getWaitingTimeAck());
            lostRoboCommand.setLastTransmissionTime(lastTransmissionTime);

            //Simulamos que hemos alcanzado el numero maximo de transmisiones
            lostRoboCommand.numberTransmissions = (i % DEFAULT_MAX_NUMBER_TRANSMISSIONS);

            connectionRob.addSentRoboCommand(lostRoboCommand);

        }

        List<RoboCommand> resendRoboCommands = connectionRob.resendRoboCommands();

        assertTrue(resendRoboCommands.size()==numResendCommands);

        for (int i = 0; i < numResendCommands; i++) {
            RoboCommand resendRoboCommand=resendRoboCommands.get(i);
            assertTrue(resendRoboCommand.exceededWaitingTimeAck());
            assertFalse(resendRoboCommand.reachedMaximunNumberTransmissions());
        }

    }



    private long generateNotExcededWaitingTime(long waitingTime){
        long excededWaitingTime = System.currentTimeMillis() - waitingTime+10;
        return excededWaitingTime;
    }


    private long generateExcededWaitingTime(long waitingTime){
        long excededWaitingTime = System.currentTimeMillis() - waitingTime-10;
        return excededWaitingTime;
    }

    private RoboCommand createRoboCommand(int sequenceNumber){

        RoboCommand roboCommand = Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);

        roboCommand.setSequenceNumber(sequenceNumber);

        roboCommand.setWaitingTimeAck(WAITING_TIME);

        //Simplemente definimos el numero maximo de transmisiones que tiene el mensaje.
        //Este paso es necesario porque se esta usando un mock
        roboCommand.setMaxNumTransmissions(roboCommand.getMaxNumTransmissions());

        return roboCommand;

    }


}
