/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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

    protected final ArrayList<RoboCommand> sentRoboCommands = new ArrayList<RoboCommand>();

    
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

        List<RoboCommand> lostRoboCommands = new ArrayList<RoboCommand>();

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

        List<RoboCommand> resendRoboCommands = new ArrayList<RoboCommand>();

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
