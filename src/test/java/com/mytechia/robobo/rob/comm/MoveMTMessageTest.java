package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Victor Sonora Pombo
 */
public class MoveMTMessageTest {

    @Test
    public void testCodeMessageData() throws Exception {

        Integer vel1 = 2;
        Integer vel2 = 1;
        MoveMTMessage originalMessage =
                new MoveMTMessage((short)655 ,45,(short)1, -30, 405);


        final byte[] messageData = originalMessage.codeMessage();

        MoveMTMessage decodedMessage = new MoveMTMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}