/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Gervasio Varela <gervasio.varela@mytechia.com>
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
import static com.mytechia.robobo.rob.comm.MessageType.StopWarning;

import java.io.Closeable;
import java.io.IOException;


/**
 * Implementation of the facade IRobComm using SMP. 
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

    private final DispatcherRobCommStopWarningListener dispatcherRobCommStopWarningListener = new DispatcherRobCommStopWarningListener();
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
    public void setLEDColor(int ledId, int r, int g, int b) throws CommunicationException {
        
        SetLEDColorMessage setLEDColorMessage = new SetLEDColorMessage((byte) ledId, (short)r, (short)g, (short)b);
        
        sendCommand(setLEDColorMessage);
    }

    @Override
    public void setLEDsMode(byte mode) throws CommunicationException {
        
        RobSetLEDsModeMessage robSetLEDsModeMessage= new RobSetLEDsModeMessage(mode);
        
        sendCommand(robSetLEDsModeMessage);
    }

    @Override
    public void moveMT( short angVel1, int angle1, short angVel2, int angle2) throws CommunicationException {
        
        MoveMTMessage moveMTMessage= new MoveMTMessage(angVel1, angle1, angVel2, angle2, 0);
        
        sendCommand(moveMTMessage);
        
    }

    @Override
    public void moveMT(short angVel1, short angVel2, long time) throws CommunicationException {
        
        MoveMTMessage moveMTMessage= new MoveMTMessage(angVel1, 0, angVel2, 0, time);
        
        sendCommand(moveMTMessage);
        
    }

    @Override
    public void movePan(int angVel, int angle) throws CommunicationException {
        
        
        
        MovePanMessage movePanMessage = new MovePanMessage(angVel, angle);
        
        sendCommand(movePanMessage);
    }



    @Override
    public void moveTilt(int angVel, int angle) throws CommunicationException{
        
    	MoveTiltMessage moveTiltMessage = new MoveTiltMessage(angVel, angle);
        
        sendCommand(moveTiltMessage);
        
    }


    @Override
    public void resetPanTiltOffset() throws CommunicationException{
        sendCommand(new ResetPanTiltOffsetMessage());
    }

    @Override
    public void setRobStatusPeriod(int period) throws CommunicationException{
        sendCommand(new SetRobStatusPeriodMessage(period));
    }


    @Override
	public void setOperationMode(byte operationMode) throws CommunicationException{
		sendCommand(new OperationModeMessage(operationMode));
	}


	@Override
	public void infraredConfiguration(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) throws CommunicationException{
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
			int tiltTime) throws CommunicationException{
		
		/*
		if(m1Tension<0){
			throw new IllegalArgumentException("The m1Tension= cannot be negative");
		}*/
		
		sendCommand(new MaxValueMotors(m1Tension, m1Time, m2Tension, m2Time, panTension, panTime, tiltTension, tiltTime));
		
	}

    @Override
    public void resetRob() throws CommunicationException {
        this.sendCommand(new ResetRobMessage());
    }

    @Override
    public void changeRobName(String name) throws CommunicationException {
        this.sendCommand(new ChangeNameMessage(name));
    }


    @Override
    public void addRobStatusListener(IRobCommStatusListener rsListener) {
        dispatcherRobCommStatusListener.subscribeToRobCommStatus(rsListener);
    }
    
    @Override
    public void removeRobStatusListener(IRobCommStatusListener rsListener){
        dispatcherRobCommStatusListener.unsubscribeFromRobCommStatus(rsListener);
    }

    @Override
    public void addStopWarningListener(IRobCommStopWarningListener swListener) {
        dispatcherRobCommStopWarningListener.subscribeToStopWarning(swListener);
    }

    @Override
    public void removeStopWarningListener(IRobCommStopWarningListener swListener) {
        dispatcherRobCommStopWarningListener.unsuscribeFromStopWarning(swListener);
    }


    void sendCommand(RoboCommand roboCommand) throws CommunicationException {

        if (roboCommand == null) {
            return;
        }

        try {

            roboCommand.setSequenceNumber(numberSequence);
            numberSequence++;
            
            communicationChannel.send(roboCommand);
            
        } catch (CommunicationException ex) {
            LOGGER.error("Error sending command", ex);
            throw ex;
        }

        roboCommand.setLastTransmissionTime(System.currentTimeMillis());

        roboCommand.increaseNumTransmissions();

        LOGGER.trace("Sent {}", roboCommand.toTransmittingString());

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
                    LOGGER.trace("Received Ack[sequenceNumber={}, error={}]", command.getSequenceNumber(), command.getErrorCode());
                }else{
                    LOGGER.trace("Received Ack[sequenceNumber={}]", command.getSequenceNumber());
                }
            }
            
            return;
        }

        if(command.getCommandType()== RobStatusMessage.commandType){
            //LOGGER.trace("Received RobStatusMessage[sequenceNumber={}].", command.getSequenceNumber());
            dispatcherRobCommStatusListener.fireReceivedStatusMotorsMT((RobStatusMessage)command);
            return;
        }
        if(command.getCommandType()== StopWarning.commandType){
            LOGGER.trace("Received StopWarning[sequenceNumber={}].", command.getSequenceNumber());
            dispatcherRobCommStopWarningListener.fireReceivedStopWarning((StopWarningMessage) command);
            return;
        }

        LOGGER.trace("Received Command[sequenceNumber={}]. This command is not processed.", command.getSequenceNumber());
        
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
                    dispatcherRobCommStatusListener.fireRobCommunicationError(ex);
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
            if (roboCommand == null){
                System.err.println("Null Command");
            }
            else {
                processReceivedCommand(roboCommand);
            }
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

            LOGGER.trace("Retransmitted {}", roboCommand.toTransmittingString());
        }
    }


    void checkForLostRoboCommands() {

        List<RoboCommand> lostRoboCommands=connectionRob.checkLostRoboCommands();

        for (RoboCommand roboCommand:lostRoboCommands) {
            LOGGER.error("Lost {}", roboCommand.toSimpleString());
        }
    }


}
