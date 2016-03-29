/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.channel.IBasicCommunicationChannel;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * This class checks if the send message were lost.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class SmpRobCommTest {


    @Test
    public void testSendCommand(){

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel);

        RoboCommand roboCommand= spy(RoboCommand.class);

        smpRobComm.sendCommand(roboCommand);

        long currentTime=System.currentTimeMillis();

        long minCurrentTime= currentTime-100;

        assertTrue(minCurrentTime < roboCommand.getLastTransmissionTime());

        assertTrue(roboCommand.getNumberTransmissions()==1);

        assertTrue(smpRobComm.isRoboCommandPendingAck(roboCommand));


    }

    @Test
    public void testProcessReceivedAck() throws Throwable{

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel);

        int sequenceNumberSenTRoboCommand=34;

        //Sending command
        RoboCommand roboCommand= spy(RoboCommand.class);
        roboCommand.setSequenceNumber(sequenceNumberSenTRoboCommand);
        smpRobComm.sendCommand(roboCommand);

        //receiving Ack message
        byte errorCode = (byte) 0;
        AckMessage ackMessage= new AckMessage(errorCode);
        ackMessage.setSequenceNumber(sequenceNumberSenTRoboCommand);

        when(basicCommunicationChannel.receive()).thenReturn(ackMessage);

        smpRobComm.handleReceivedCommand();

        assertFalse(smpRobComm.isRoboCommandPendingAck(roboCommand));


    }

    @Test
    public void testCheckForRensendRoboCommands() throws CommunicationException {

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel);

        int sequenceNumberSenTRoboCommand=34;

        //Sending command1
        RoboCommand roboCommand1= spy(RoboCommand.class);
        roboCommand1.setSequenceNumber(sequenceNumberSenTRoboCommand);
        //Envio del comand1
        smpRobComm.sendCommand(roboCommand1);
        //Le asignamos un tiempo de transmision muy antiguo. Asi el mensaje debe ser reenviado.
        roboCommand1.setLastTransmissionTime(500L);

        //Sending command2
        RoboCommand roboCommand2= spy(RoboCommand.class);
        roboCommand2.setSequenceNumber(sequenceNumberSenTRoboCommand);
        roboCommand2.increaseNumTransmissions();
        //Envio del comand2
        smpRobComm.sendCommand(roboCommand2);
        //Le asignamos un tiempo de transmision muy antiguo. Asi el mensaje debe ser reenviado.
        roboCommand2.setLastTransmissionTime(1000L);

        //Sending command3
        RoboCommand roboCommand3= spy(RoboCommand.class);
        roboCommand3.setSequenceNumber(sequenceNumberSenTRoboCommand);
        //Envio del comando 3
        smpRobComm.sendCommand(roboCommand3);
        //Le ponemos un tiempo de espera muy alto para que no sea reenviado
        roboCommand3.setLastTransmissionTime(Long.MAX_VALUE);

        //Reseteamos el mock para no tener en cuenta las invocaciones
        //de los tres comandos
        Mockito.reset(basicCommunicationChannel);

        //Ejercitamos el SUT
        smpRobComm.checkForRensendRoboCommands();

        verify(basicCommunicationChannel).send(roboCommand1);

        verify(basicCommunicationChannel).send(roboCommand2);

        //Comprobamos que no se reenvia el command3 porque su tiempo de espera no ha acabado
        verify(basicCommunicationChannel, never()).send(roboCommand3);

    }


    @Test
    public void testCheckForLostRoboCommands(){

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel);

        int sequenceNumberSenTRoboCommand=34;

    }

}
