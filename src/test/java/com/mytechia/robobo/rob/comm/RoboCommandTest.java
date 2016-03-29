/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 *
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class RoboCommandTest {


    public static final int NUM_TRANSMISSIONS = 10;
    public static final int WAITING_TIME = 100;

    @Test
    public void returnTrueIfExceededWaitingTimeAck(){

        RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
        roboCommand.setWaitingTimeAck(WAITING_TIME);

        long lastTransmissionTime= System.currentTimeMillis()-roboCommand.getWaitingTimeAck()-1;

        roboCommand.setLastTransmissionTime(lastTransmissionTime);

        boolean exceededWaitingTimeAck=roboCommand.exceededWaitingTimeAck();

        Assert.assertTrue(exceededWaitingTimeAck);

    }


    @Test
    public void returnFalseIfNotExceededWaitingTimeAck(){

        RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
        roboCommand.setWaitingTimeAck(WAITING_TIME);

        long lastTransmissionTime= System.currentTimeMillis()-roboCommand.getWaitingTimeAck()+1;

        roboCommand.setLastTransmissionTime(lastTransmissionTime);

        boolean exceededWaitingTimeAck=roboCommand.exceededWaitingTimeAck();

        Assert.assertFalse(exceededWaitingTimeAck);
    }

    @Test
    public void returnTrueIfReachedMaximunNumberTransmissions(){

        RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
        roboCommand.setMaxNumTransmissions(NUM_TRANSMISSIONS);

        for (int i = 0; i <= NUM_TRANSMISSIONS; i++) {
            roboCommand.increaseNumTransmissions();
        }

        boolean reachedMaximunNumberTransmissions= roboCommand.reachedMaximunNumberTransmissions();

        Assert.assertTrue(reachedMaximunNumberTransmissions);

    }

    @Test
    public void returnFalseIfReachedMaximunNumberTransmissions(){

        RoboCommand roboCommand= Mockito.mock(RoboCommand.class, Mockito.CALLS_REAL_METHODS);
        roboCommand.setMaxNumTransmissions(NUM_TRANSMISSIONS);

        for (int i = 0; i < NUM_TRANSMISSIONS; i++) {
            roboCommand.increaseNumTransmissions();
        }

        boolean reachedMaximunNumberTransmissions= roboCommand.reachedMaximunNumberTransmissions();

        roboCommand.setLastTransmissionTime(System.currentTimeMillis());

        Assert.assertTrue(reachedMaximunNumberTransmissions);

    }


}
