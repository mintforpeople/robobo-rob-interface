/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.channel.IBasicCommunicationChannel;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import static com.mytechia.robobo.rob.comm.RoboCommand.DEFAULT_MAX_NUMBER_TRANSMISSIONS;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 * @author Julio Alberto Gomez Fernandez
 */
public class SmpRobCommTest {


    @Test
    public void testSendCommand() throws CommunicationException{

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel, new RoboCommandFactory());

        RoboCommand roboCommand= spy(RoboCommand.class);

        smpRobComm.sendCommand(roboCommand);

        long currentTime=System.currentTimeMillis();

        long minCurrentTime= currentTime-100;

        assertTrue(minCurrentTime < roboCommand.getLastTransmissionTime());

        assertTrue(roboCommand.getNumberTransmissions()==1);

        assertTrue(smpRobComm.isRoboCommandPendingAck(roboCommand));


    }

    @Test
    @Ignore
    public void testProcessReceivedAck() throws Throwable{

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel, new RoboCommandFactory());
        
        //Sending command
        RoboCommand roboCommand= spy(RoboCommand.class);
        smpRobComm.sendCommand(roboCommand);
        int sequenceNumberSenTRoboCommand=roboCommand.getSequenceNumber();

        //receiving Ack message
        byte errorCode = (byte) 0;
        AckMessage ackMessage= new AckMessage();
        ackMessage.setSequenceNumber(sequenceNumberSenTRoboCommand);

        when(basicCommunicationChannel.receive()).thenReturn(ackMessage);

        smpRobComm.handleReceivedCommand();

        assertFalse(smpRobComm.isRoboCommandPendingAck(roboCommand));


    }

    @Test
    public void testCheckForRensendRoboCommands() throws CommunicationException {

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel, new RoboCommandFactory());

        int sequenceNumberSenTRoboCommand=34;

        //Creando command1
        RoboCommand roboCommand1= spy(RoboCommand.class);
        roboCommand1.setSequenceNumber(sequenceNumberSenTRoboCommand);
        //Envio del comand1
        smpRobComm.sendCommand(roboCommand1);
        //Le asignamos un tiempo de transmision muy antiguo. Asi el mensaje debe ser reenviado.
        roboCommand1.setLastTransmissionTime(500L);
        roboCommand1.setMaxNumTransmissions(DEFAULT_MAX_NUMBER_TRANSMISSIONS);

        //Creando command2
        RoboCommand roboCommand2= spy(RoboCommand.class);
        roboCommand2.setSequenceNumber(sequenceNumberSenTRoboCommand);
        roboCommand2.increaseNumTransmissions();
        //Envio del comand2
        smpRobComm.sendCommand(roboCommand2);
        //Le asignamos un tiempo de transmision muy antiguo. Asi el mensaje debe ser reenviado.
        roboCommand2.setLastTransmissionTime(1000L);
        roboCommand2.setMaxNumTransmissions(DEFAULT_MAX_NUMBER_TRANSMISSIONS);

        //Creando command3
        RoboCommand roboCommand3= spy(RoboCommand.class);
        roboCommand3.setSequenceNumber(sequenceNumberSenTRoboCommand);
        //Envio del comando 3
        smpRobComm.sendCommand(roboCommand3);
        //Le ponemos un tiempo de espera muy alto para que no sea reenviado
        roboCommand3.setLastTransmissionTime(Long.MAX_VALUE);
        roboCommand3.setMaxNumTransmissions(DEFAULT_MAX_NUMBER_TRANSMISSIONS);

        //Reseteamos el mock para no tener en cuenta las invocaciones
        //de los tres comandos
        Mockito.reset(basicCommunicationChannel);

        //Ejercitamos el SUT
        smpRobComm.checkForRensendRoboCommands();

        //Vereficamos que efectivamente el comando 1 fue reenviado
        verify(basicCommunicationChannel).send(roboCommand1);

        //Vereficamos que efectivamente el comando 2 fue reenviado
        verify(basicCommunicationChannel).send(roboCommand2);

        //Comprobamos que no se reenvia el command3 porque su tiempo de espera no ha acabado
        verify(basicCommunicationChannel, never()).send(roboCommand3);

    }


    
    /*
    @Test
    public void testCheckForLostRoboCommands(){

        IBasicCommunicationChannel basicCommunicationChannel= mock(IBasicCommunicationChannel.class);

        SmpRobComm smpRobComm= new SmpRobComm(basicCommunicationChannel);

        int sequenceNumberSenTRoboCommand=34;

    }
    */
    

}
