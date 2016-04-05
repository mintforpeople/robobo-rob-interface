/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.Endianness;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Robobo proyect specific commands. This class extends Command to add the fields
 * waitingTimeAck and lastTransmissionTime in order to know when a message was lost.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public abstract class RoboCommand extends  Command{

    public static final long DEFAULT_TIME_TO_WAIT=100;//20 ms

    public static final int DEFAULT_MAX_NUMBER_TRANSMISSIONS =3;

    protected long lastTransmissionTime;

    protected long waitingTimeAck =DEFAULT_TIME_TO_WAIT;

    protected int maxNumTransmissions=1;

    protected int numberTransmissions =0;

    private  final SimpleDateFormat TRANSMISSION_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");


    public RoboCommand() {
    }

    public RoboCommand(Endianness endianness) {
        super(endianness);
    }

    public RoboCommand(byte[] message) throws MessageFormatException {
        super(message);
    }

    public RoboCommand(Endianness endianness, byte[] message) throws MessageFormatException {
        super(endianness, message);
    }

    @Override
    public void setSequenceNumber(int sequenceNumber){
        super.setSequenceNumber(sequenceNumber);
    }

    public long getLastTransmissionTime() {
        return lastTransmissionTime;
    }

    public void setLastTransmissionTime(long timeLastTransmission) {
        this.lastTransmissionTime = timeLastTransmission;
    }

    public long getWaitingTimeAck() {
        return waitingTimeAck;
    }

    public void setWaitingTimeAck(long waitingTimeAck) {
        this.waitingTimeAck = waitingTimeAck;
    }

    public int getMaxNumTransmissions() {
        return maxNumTransmissions;
    }

    public void setMaxNumTransmissions(int maxNumTransmissions) {
        this.maxNumTransmissions = maxNumTransmissions;
    }

    public int getNumberTransmissions() {
        return numberTransmissions;
    }

    public void increaseNumTransmissions() {
        numberTransmissions++;
    }


    boolean exceededWaitingTimeAck() {

        if (waitingTimeAck == -1) {
            return false;
        }

        if (lastTransmissionTime == 0) {
            return false;
        }

        return (waitingTimeAck < (System.currentTimeMillis() - this.lastTransmissionTime));

    }

    public boolean reachedMaximunNumberTransmissions(){
        return (numberTransmissions >= maxNumTransmissions);
    }

    public String toSimpleString(){

        StringBuilder simpleStringBuilder= new StringBuilder();
        simpleStringBuilder.append(this.getClass().getSimpleName());
        simpleStringBuilder.append("[");
        simpleStringBuilder.append("type=").append(this.getCommandType());
        simpleStringBuilder.append(", sequenceNumer=").append(this.getSequenceNumber());
        simpleStringBuilder.append("]");

        return simpleStringBuilder.toString();
    }

    public String toTransmittingString(){
        StringBuilder simpleStringBuilder= new StringBuilder();
        simpleStringBuilder.append(this.getClass().getSimpleName());
        simpleStringBuilder.append("[");
        simpleStringBuilder.append("type=").append(this.getCommandType());
        simpleStringBuilder.append(", sequenceNumer=").append(this.getSequenceNumber());
        simpleStringBuilder.append(", numberRetransmisions=").append(this.getNumberTransmissions());

        String formattedTimeStamp= TRANSMISSION_TIME_FORMATTER.format(new Date(this.getLastTransmissionTime()));
        simpleStringBuilder.append(", lastTransmissionTime=").append(formattedTimeStamp);

        simpleStringBuilder.append("]");

        return simpleStringBuilder.toString();
    }


}
