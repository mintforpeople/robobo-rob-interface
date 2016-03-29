package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class RobSetLEDsModeMessageTest {

    private static final byte LEDsMODE = 5;


    @Test
    public void testCodeMessageData() throws Exception {

        RobSetLEDsModeMessage originalMessage = new RobSetLEDsModeMessage(LEDsMODE);

        final byte[] messageData = originalMessage.codeMessage();

        RobSetLEDsModeMessage decodedMessage = new RobSetLEDsModeMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }

}