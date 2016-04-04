/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.channel.IBasicCommunicationChannel;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.mytechia.robobo.rob.comm.MessageType.AckMessage;
import static com.mytechia.robobo.rob.comm.MessageType.RobStatusMessage;


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

    public static final int TIME_CHECK_MESSAGE = 50;

    private final DispatcherRobCommStatusListener dispatcherRobCommStatusListener= new DispatcherRobCommStatusListener();

    protected final ConnectionRob connectionRob = new ConnectionRob();

    private final IBasicCommunicationChannel communicationChannel;

    protected Timer timer;

    protected  TimerTask timerTask= new ChecherLostMessages();

    protected final MessageProcessor messageProcessor= new MessageProcessor();

    private Thread messageProcessorThread;

    private int numberSequence=0;


    public void start(){

        this.timer= new Timer();

        this.timer.schedule(timerTask, TIME_CHECK_MESSAGE);

        messageProcessorThread=new Thread(messageProcessorThread);

        messageProcessorThread.start();

    }


    public void stop(){

        if(this.timer!=null) {
            this.timer.cancel();
        }

        if(communicationChannel!=null){

        }

        if(messageProcessor!=null){
            messageProcessor.interrupt();
        }

    }

    public SmpRobComm(IBasicCommunicationChannel communicationChannel){

        if (communicationChannel == null) {
            throw new NullPointerException("The parameter roboCom is required");
        }

        this.communicationChannel= communicationChannel;
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
    public void moveMT(double angVel1, double angle1, double angVel2, double angle2) {
        
        MoveMTMessage moveMTMessage= new MoveMTMessage(angVel1, angle1, angVel2, angle2, 0);
        
        sendCommand(moveMTMessage);
        
    }

    @Override
    public void moveMT(double angVel1, double angVel2, long time)  {
        
        MoveMTMessage moveMTMessage= new MoveMTMessage(angVel1, 0, angVel2, 0, time);
        
        sendCommand(moveMTMessage);
        
    }

    @Override
    public void movePan(double angVel, double angle) {
        
        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)0, angVel, angle, 0);
        
        sendCommand(movePanTiltMessage);
    }

    @Override
    public void movePan(double angVel, long time) {
        
        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)0, angVel, 0, time);
        
        sendCommand(movePanTiltMessage);
        
    }

    @Override
    public void moveTilt(double angVel, double angle) {
        
        MovePanTiltMessage movePanTiltMessage= new MovePanTiltMessage((byte)1, angVel, angle, 0);
        
        sendCommand(movePanTiltMessage);
        
    }

    @Override
    public void moveTilt(double angVel, long time) {
        
        MovePanTiltMessage movePanTiltMessage = new MovePanTiltMessage((byte) 1, angVel, 0, time);

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


    public void addRobStatusListener(IRobCommStatusListener rsListener) {
        dispatcherRobCommStatusListener.subscribeToRobCommStatus(rsListener);
    }
    
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
            this.connectionRob.receivedAck((AckMessage)command);
            return;
        }

        if(command.getCommandType()== RobStatusMessage.commandType){
            dispatcherRobCommStatusListener.fireReceivedStatusMotorsMT((RobStatusMessage)command);
        }

    }


    class MessageProcessor extends Thread{

        @Override
        public void run() {

            while (!this.isInterrupted()) {
                handleReceivedCommand();
            }
        }



    }

    void handleReceivedCommand() {

        Command command=null;

        try {
            command = communicationChannel.receive();
        } catch (CommunicationException e) {
            LOGGER.error("Error receiving command", e);
        }

        if(command!=null) {
            processReceivedCommand((RoboCommand)command);
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
