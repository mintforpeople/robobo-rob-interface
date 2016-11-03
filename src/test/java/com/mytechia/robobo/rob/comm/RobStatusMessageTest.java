package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

/**
 * Created by Victor Sonora Pombo
 */
public class RobStatusMessageTest {


    @Test
   @Ignore
    public void testCodeMessageData() throws Exception {

    	/*
        RobStatusMessage originalMessage =
                new RobStatusMessage(
                        (byte)4,
                        (byte)2,
                        new short[] {1,2,3,4,5,6,7,8,9},
                        new short[] {1,2,3,4,5,6, 7},
                        new short[] {1,2,3,4},
                        new short[] {1,2,3,4},
                        new int[] {1,2,3,4},
                        new int[] {1,2,3,4}, 10, false);

        final byte[] messageData = originalMessage.codeMessage();

        RobStatusMessage decodedMessage = new RobStatusMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());
        */

    }


}