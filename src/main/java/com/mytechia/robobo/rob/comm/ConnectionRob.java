/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class checks if the send message were lost.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class ConnectionRob {

    private static final Logger LOGGER= LoggerFactory.getLogger(ConnectionRob.class);

    protected final ArrayList<RoboCommand> sentRoboCommands = new ArrayList<>();

    
    public ConnectionRob() {}

    
    public  void addSentRoboCommand(RoboCommand sentRoboCommand){
        
        if(sentRoboCommand==null){
            return;
        }
        
        synchronized(sentRoboCommands){
            sentRoboCommands.add(sentRoboCommand);
        }
    }


    public boolean receivedAck(AckMessage ackMessage){

        if(ackMessage==null){
            return false;
        }

        RoboCommand foundRoboCommand=null;

        synchronized(sentRoboCommands) {

            for (RoboCommand roboCommand : this.sentRoboCommands) {
                if (roboCommand.getSequenceNumber() == ackMessage.getSequenceNumber()) {
                    foundRoboCommand = roboCommand;
                    break;
                }
            }

            if (foundRoboCommand != null) {
                this.sentRoboCommands.remove(foundRoboCommand);
            }
        }

        return (foundRoboCommand!=null);

    }

    public  List<RoboCommand> checkLostRoboCommands(){

        List<RoboCommand> lostRoboCommands = new ArrayList<>();

        synchronized (sentRoboCommands) {

            for (RoboCommand roboCommand : sentRoboCommands) {

                if(roboCommand.reachedMaximunNumberTransmissions()) {
                    if(roboCommand.exceededWaitingTimeAck()) {
                        lostRoboCommands.add(roboCommand);
                    }
                }
            }

            sentRoboCommands.removeAll(lostRoboCommands);
        }

        return lostRoboCommands;
    }

    public List<RoboCommand> resendRoboCommands(){

        List<RoboCommand> resendRoboCommands = new ArrayList<>();

        synchronized (sentRoboCommands) {

            for(RoboCommand roboCommand: sentRoboCommands){

                if(roboCommand.exceededWaitingTimeAck()){
                    if(!roboCommand.reachedMaximunNumberTransmissions()) {
                        resendRoboCommands.add(roboCommand);
                    }
                }

            }

        }

        return resendRoboCommands;
    }


    boolean wasSentRoboCommand(RoboCommand roboCommand){
        synchronized (sentRoboCommands) {
            return sentRoboCommands.contains(roboCommand);
        }
    }


}
