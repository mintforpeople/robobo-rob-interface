/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import static com.mytechia.robobo.rob.comm.RoboCommand.decodeDataFieldSize;
import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.MessageFactory;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.collections.bytequeue.ArrayByteQueue;
import com.mytechia.commons.util.collections.bytequeue.exception.EmptyByteQueueException;
import com.mytechia.commons.util.collections.bytequeue.exception.FullByteQueueException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is responsible for processing the byte queue filled with 
 * the bytes sent by the bluetooth module by serial port.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class StreamProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamProcessor.class);

    private final ArrayByteQueue dataQueue;

    private static final int MAX_ACCEPTED_MSG_SIZE = 1000;
    
    private final MessageFactory messageFactory;
    
    
    public StreamProcessor(MessageFactory messageFactory) {
        
        if (messageFactory == null) {
            throw new NullPointerException("The parameter messageFactory is required");
        }

        this.dataQueue = new ArrayByteQueue(Command.MAX_MESSAGE_SIZE+500);
        
        this.messageFactory= messageFactory;
    }
    
    
    public void push(byte[] bytes, int offset, int count){
        
        try{
            this.dataQueue.push(bytes, offset, count);
        }catch(FullByteQueueException ex){
            LOGGER.error("Error  push bytes ", ex);
        }
    }
    

    public List<RoboCommand> process() {

        List<RoboCommand> roboCommands = new ArrayList<RoboCommand>();

        byte initByte = 0;

        while (!dataQueue.isEmpty()) {

            try {
                initByte = dataQueue.get();
            } catch (EmptyByteQueueException ex) {
                LOGGER.error("Error reading first byte", ex);
            }

            if (initByte != Command.INIT_BYTE) {
                dataQueue.discardBytes(1);
                LOGGER.warn("Discarting first byte");
                continue;
            }

            byte[] headerMessageData = new byte[Command.COMMAND_HEADER_SIZE];
            int numReadedHeaderBytes = dataQueue.get(headerMessageData, 0, Command.COMMAND_HEADER_SIZE);
            if (numReadedHeaderBytes < Command.COMMAND_HEADER_SIZE) {
                LOGGER.debug("Not enough data to read a header");
                return roboCommands;
            }

            int dataFieldSize=0;
            
            try {
                dataFieldSize= decodeDataFieldSize(headerMessageData);
            } catch (MessageFormatException ex) {
                dataQueue.discardBytes(1);
                LOGGER.warn("Malformed message incorrect header", ex);
                continue;
            }
            
            int fullMessageSize= Command.COMMAND_HEADER_SIZE+ dataFieldSize;
            
            if(fullMessageSize > MAX_ACCEPTED_MSG_SIZE){
                dataQueue.discardBytes(1);
                LOGGER.debug("Malformed message: too long payload");
                continue;
            }
            
            byte[] fullMessageData = new byte[fullMessageSize];
            
            int numReaderMessageBytes= dataQueue.get(fullMessageData, 0, fullMessageSize);
            
            if(dataFieldSize > numReaderMessageBytes){
                LOGGER.debug("Not enough data to read");
                return roboCommands;
            }
            
            try {
                //We have all bytes then try to decode the command
                RoboCommand command = (RoboCommand) messageFactory.decodeMessage(fullMessageData);
                
                roboCommands.add(command);
                
                //New command, discard all bytes of the command
                dataQueue.discardBytes(fullMessageSize);
                
            } catch (MessageFormatException ex) {
                //Discard a byte and try the next
                dataQueue.discardBytes(1);
                LOGGER.warn("Malformed message", ex);
            }

        }

        return roboCommands;
    }

}
