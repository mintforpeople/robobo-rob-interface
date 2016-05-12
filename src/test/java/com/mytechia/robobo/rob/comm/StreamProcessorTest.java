/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.collections.bytequeue.exception.FullByteQueueException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;


/**
 * 
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class StreamProcessorTest {
    
    @Before
    public void initTest(){
    }
    
    
        @Test
    public void dividedTwoPartsAckMessageMustBeReaded() throws MessageFormatException, FullByteQueueException{
        
        int sequenceNumber = 56;
        
        StreamProcessor arrayByteQueueProcessor= new StreamProcessor(new RoboCommandFactory());
        
        AckMessage ackMessage= new AckMessage();
        ackMessage.setSequenceNumber(sequenceNumber);
        
        byte[] codedAckMessage=ackMessage.codeMessage();
        
        arrayByteQueueProcessor.push(codedAckMessage, 0, 1);
        
        arrayByteQueueProcessor.push(codedAckMessage, 1, codedAckMessage.length-1);
        
        List<RoboCommand> roboCommands = arrayByteQueueProcessor.process();
        
        RoboCommand roboCommand = roboCommands.get(0);
        
        assertTrue(roboCommand instanceof AckMessage);
        
        assertTrue(ackMessage.getSequenceNumber()==sequenceNumber);

        
    }
    
    
    @Test
    public void ackMessageMustBeReaded() throws MessageFormatException, FullByteQueueException{
        
        int sequenceNumber = 56;
        
        StreamProcessor arrayByteQueueProcessor= new StreamProcessor(new RoboCommandFactory());
        
        AckMessage ackMessage= new AckMessage();
        ackMessage.setSequenceNumber(sequenceNumber);
        
        byte[] codedAckMessage=ackMessage.codeMessage();
        
        arrayByteQueueProcessor.push(codedAckMessage, 0, codedAckMessage.length);
        
        List<RoboCommand> roboCommands = arrayByteQueueProcessor.process();
        
        RoboCommand roboCommand = roboCommands.get(0);
        
        assertTrue(roboCommand instanceof AckMessage);
        assertTrue(ackMessage.getSequenceNumber()==sequenceNumber);
        
    }
    
    @Test
    public void discardIncorrectRobStatusMessage() throws MessageFormatException, FullByteQueueException{
        
        StreamProcessor bluetoothStreamProcessor = new StreamProcessor(new RoboCommandFactory());

        RobStatusMessage message1
                = new RobStatusMessage((byte) 4, (byte) 2,
                        new short[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                        new short[]{1, 2, 3, 4, 5, 6, 7},
                        new short[]{1, 2, 3, 4},
                        new short[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4}, 10,
                        false);
        
        
        byte[] codedRobStatusMessage1 = message1.codeMessage();
        
        bluetoothStreamProcessor.push(codedRobStatusMessage1, 0, 50);
        
        List<RoboCommand> commands = bluetoothStreamProcessor.process();
        
         assertTrue(commands.size() == 0);
        
    }
    
    @Test
    @Ignore
    public void severalRobStatusMessageMustReaded() throws MessageFormatException, FullByteQueueException{
        
       StreamProcessor bluetoothStreamProcessor = new StreamProcessor(new RoboCommandFactory());
        

        RobStatusMessage message1
                = new RobStatusMessage((byte) 4, (byte) 2,
                        new short[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                        new short[]{1, 2, 3, 4, 5, 6, 7},
                        new short[]{1, 2, 3, 4},
                        new short[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4}, 10,
                        false);

        byte[] codedRobStatusMessage1 = message1.codeMessage();
        bluetoothStreamProcessor.push(codedRobStatusMessage1, 0, codedRobStatusMessage1.length);
        
        
        bluetoothStreamProcessor.push(new byte[]{0,0,0}, 0, 3);
        
        
        RobStatusMessage message2
                = new RobStatusMessage((byte) 4, (byte) 2,
                        new short[]{1, 2, 37, 4, 5, 6, 7, 8, 9},
                        new short[]{10, 20, 30, 40, 57, 63, 7},
                        new short[]{1, 2, 3, 4},
                        new short[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4},
                        new int[]{11, 2, 13, 4}, 10,
                        true);
        
        byte[] codedRobStatusMessage2 = message2.codeMessage();
        bluetoothStreamProcessor.push(codedRobStatusMessage2, 0, codedRobStatusMessage2.length);
       
       
        List<RoboCommand> commands = bluetoothStreamProcessor.process();
        
        assertTrue(commands.size() == 2);
        assertTrue(message1.equals(commands.get(0)));
        assertTrue(message2.equals(commands.get(1)));
 
        
    }
    
    
    @Test
    @Ignore
    public void dividedRobStatusMessageMustReaded() throws MessageFormatException, FullByteQueueException {

        StreamProcessor arrayByteQueueProcessor = new StreamProcessor(new RoboCommandFactory());

        RobStatusMessage originalMessage
                = new RobStatusMessage((byte) 4, (byte) 2,
                        new short[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                        new short[]{1, 2, 3, 4, 5, 6, 7},
                        new short[]{1, 2, 3, 4},
                        new short[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4}, 10,
                        false);

        byte[] codedRobStatusMessage = originalMessage.codeMessage();

        int numBytesByPart = 4;
        int initPart = 0;
        int numParts=3;
        for (int i = 0; i < numParts; i++) {
            arrayByteQueueProcessor.push(codedRobStatusMessage, initPart, numBytesByPart);
            initPart = initPart + numBytesByPart;
            List<RoboCommand> commands = arrayByteQueueProcessor.process();
            assertTrue(commands.isEmpty());

        }

        arrayByteQueueProcessor.push(codedRobStatusMessage, initPart, (codedRobStatusMessage.length - (numBytesByPart* numParts)));

        List<RoboCommand> commands = arrayByteQueueProcessor.process();

        assertTrue(commands.size() == 1);
        assertTrue(originalMessage.equals(commands.get(0)));
    }
    

    

    
    
    
}
