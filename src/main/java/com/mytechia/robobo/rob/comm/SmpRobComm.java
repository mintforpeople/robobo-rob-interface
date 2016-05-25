/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.MessageFactory;
import com.mytechia.commons.framework.simplemessageprotocol.channel.IBasicCommunicationChannel;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.mytechia.robobo.rob.comm.MessageType.AckMessage;
import static com.mytechia.robobo.rob.comm.MessageType.RobStatusMessage;
import java.io.Closeable;
import java.io.IOException;


/**
 * Implementation of the facade IRobComm that using SMTP generically. 
 * This class communicates through a  message mechanism with the hardware. 
 * The commands to do something are sent through this class. 
 * Events that notify state changes are received through an object of this type.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class SmpRobComm implements IRobComm{

    private static final Logger LOGGER= LoggerFactory.getLogger(SmpRobComm.class);

    public static final int TIME_CHECK_MESSAGE = 1000;
    

    private final DispatcherRobCommStatusListener dispatcherRobCommStatusListener= new DispatcherRobCommStatusListener();

    protected final ConnectionRob connectionRob = new ConnectionRob();

    private final IBasicCommunicationChannel communicationChannel;

    protected Timer timer;

    protected  TimerTask timerTask= new ChecherLostMessages();

    private final MessageProcessor messageProcessor= new MessageProcessor();

    private int numberSequence=0;
    

    
    private final StreamProcessor bluetoothStreamProcessor;
    


    public void start(){

        this.timer= new Timer();

        this.timer.schedule(timerTask, TIME_CHECK_MESSAGE, TIME_CHECK_MESSAGE);

        messageProcessor.start();

    }


    public void stop(){

        if(this.timer!=null) {
            this.timer.cancel();
        }
        
        if(messageProcessor!=null){
            messageProcessor.interrupt();
        }

        if(communicationChannel!=null){
            if(communicationChannel instanceof Closeable){
                try { 
                    ((Closeable)communicationChannel).close();
                } catch (IOException ex) {
                    LOGGER.warn("Error try to close communication channel", ex);
                }
            }        
        }
    }

    public SmpRobComm(IBasicCommunicationChannel communicationChannel, MessageFactory messageFactory){

        if (communicationChannel == null) {
            throw new NullPointerException("The parameter roboCom is required");
        }

        if(messageFactory==null){
            throw new NullPointerException("The parameter messageFactory is required");
        }
        
        this.communicationChannel= communicationChannel;
        
        this.bluetoothStreamProcessor= new StreamProcessor(messageFactory);
    }
    
    
    
    

    @Override
    public void setLEDColor(int ledId, int r, int g, int b) {
        
        SetLEDColorMessage setLEDColorMessage = new SetLEDColorMessage((byte) ledId, (short)r, (short)g, (short)b);
        
        sendCommand(setLEDColorMessage);
    }

    @Override
    public void setLEDsMode(byte mode) {
        
        RobSetLEDsModeMessage robSetLEDsModeMessage= new RobSetLEDsModeMessage(mode);
        
        sendCommand(robSetLEDsModeMessage);
    }

    @Override
    public void moveMT(byte mode, short angVel1, int angle1, short angVel2, int angle2) {
        
        MoveMTMessage moveMTMessage= new MoveMTMessage(mode, angVel1, angle1, angVel2, angle2, 0);
        
        sendCommand(moveMTMessage);
        
    }

    @Override
    public void moveMT(byte mode, short angVel1, short angVel2, long time)  {
        
        MoveMTMessage moveMTMessage= new MoveMTMessage(mode, angVel1, 0, angVel2, 0, time);
        
        sendCommand(moveMTMessage);
        
    }

    @Override
    public void movePan(short angVel, int angle) {
        
        System.out.println("PAN: "+angVel+" - "+angle);
        
        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage(angVel, angle, (short) 0, 0);
        
        sendCommand(movePanTiltMessage);
    }



    @Override
    public void moveTilt(short angVel, int angle) {
        
        System.out.println("TILT: "+angVel+" - "+angle);
        
    	MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((short) 0, 0, angVel, angle);
        
        sendCommand(movePanTiltMessage);
        
    }



    @Override
    public void resetPanTiltOffset() {
        sendCommand(new ResetPanTiltOffsetMessage());
    }

    @Override
    public void setRobStatusPeriod(int period) {
        sendCommand(new SetRobStatusPeriodMessage(period));
    }


    @Override
	public void setOperationMode(byte operationMode) {
		sendCommand(new OperationModeMessage(operationMode));
	}


	@Override
	public void infraredConfiguration(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) {
		sendCommand(new InfraredConfigurationMessage(infraredId, commandCode, dataByteLow, dataByteHigh));
	
	}


	@Override
	public void maxValueMotors(int m1Tension, 
			int m1Time, 
			int m2Tension, 
			int m2Time, 
			int panTension, 
			int panTime,
			int tiltTension, 
			int tiltTime) {
		
		/*
		if(m1Tension<0){
			throw new IllegalArgumentException("The m1Tension= cannot be negative");
		}*/
		
		sendCommand(new MaxValueMotors(m1Tension, m1Time, m2Tension, m2Time, panTension, panTime, tiltTension, tiltTime));
		
	}


	@Override
    public void addRobStatusListener(IRobCommStatusListener rsListener) {
        dispatcherRobCommStatusListener.subscribeToRobCommStatus(rsListener);
    }
    
    @Override
    public void removeRobStatusListener(IRobCommStatusListener rsListener){
        dispatcherRobCommStatusListener.unsubscribeFromRobCommStatus(rsListener);
    }

    
    void sendCommand(RoboCommand roboCommand) {

        if (roboCommand == null) {
            return;
        }

        try {

            roboCommand.setSequenceNumber(numberSequence);
            numberSequence++;
            
            communicationChannel.send(roboCommand);
            
        } catch (CommunicationException ex) {
            LOGGER.error("Error sending command", ex);
        }

        roboCommand.setLastTransmissionTime(System.currentTimeMillis());

        roboCommand.increaseNumTransmissions();

        LOGGER.debug("Sent {}", roboCommand.toTransmittingString());

        connectionRob.addSentRoboCommand(roboCommand);
    }


    boolean isRoboCommandPendingAck(RoboCommand roboCommand){

        if (roboCommand == null) {
            return false;
        }

        return connectionRob.wasSentRoboCommand(roboCommand);
    }


    void processReceivedCommand(RoboCommand command){

        if(command.getCommandType()== AckMessage.commandType){
            boolean receivedAck=this.connectionRob.receivedAck((AckMessage)command);
            
            if(receivedAck){
                AckMessage ackMessage= (AckMessage) command;
                if(ackMessage.getErrorCode()!=0){
                    LOGGER.debug("Received Ack[sequenceNumber={}, error={}]", command.getSequenceNumber(), command.getErrorCode());
                }else{
                    LOGGER.debug("Received Ack[sequenceNumber={}]", command.getSequenceNumber());
                }
            }
            
            return;
        }

        if(command.getCommandType()== RobStatusMessage.commandType){
            LOGGER.debug("Received RobStatusMessage[sequenceNumber={}].", command.getSequenceNumber());
            dispatcherRobCommStatusListener.fireReceivedStatusMotorsMT((RobStatusMessage)command);
            return;
        }

        LOGGER.debug("Received Command[sequenceNumber={}]. This command is not processed.", command.getSequenceNumber());
        
    }


    class MessageProcessor extends Thread {

        @Override
        public void run() {

            while (!this.isInterrupted()) {
                
                try {
                    handleReceivedCommand();
                } catch(MessageFormatException ex){
                    LOGGER.error("Error format command", ex);
                }catch (CommunicationException ex) {
                    LOGGER.error("Error receiving command", ex);
                    return;
                }
            }
            
        }

    }

    void handleReceivedCommand() throws CommunicationException, MessageFormatException {

        byte[] buffer= new byte[Command.MAX_MESSAGE_SIZE];
        
        int readedBytes=communicationChannel.receive(buffer);
        
        this.bluetoothStreamProcessor.push(buffer, 0, readedBytes);
        
        List<RoboCommand> roboCommands = this.bluetoothStreamProcessor.process();
        
        if((roboCommands==null) || (roboCommands.isEmpty())){
            return;
        }
        
        for (RoboCommand roboCommand : roboCommands) {
            processReceivedCommand(roboCommand);
        }
        

    }


    class ChecherLostMessages extends TimerTask{

        @Override
        public void run(){

            checkForLostRoboCommands();

            checkForRensendRoboCommands();

        }

    }


    void checkForRensendRoboCommands() {
        List<RoboCommand> resendRoboCommands = connectionRob.resendRoboCommands();

        for (RoboCommand roboCommand: resendRoboCommands) {

            try {
                roboCommand.setLastTransmissionTime(System.currentTimeMillis());
                roboCommand.increaseNumTransmissions();
                communicationChannel.send(roboCommand);
            } catch (CommunicationException exception) {
                LOGGER.error("Error retransmitting {}", roboCommand.toTransmittingString(), exception);
            }

            LOGGER.debug("Retransmitted {}", roboCommand.toTransmittingString());
        }
    }


    void checkForLostRoboCommands() {

        List<RoboCommand> lostRoboCommands=connectionRob.checkLostRoboCommands();

        for (RoboCommand roboCommand:lostRoboCommands) {
            LOGGER.error("Lost {}", roboCommand.toSimpleString());
        }
    }


}
