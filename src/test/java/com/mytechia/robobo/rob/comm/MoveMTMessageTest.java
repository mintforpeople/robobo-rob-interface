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

        MoveMTMessage originalMessage =
                new MoveMTMessage(
                        (byte) 4,
                        (byte)2,
                        45,
                        (byte)1,
                        -30,
                        405);

        final byte[] messageData = originalMessage.codeMessage();

        MoveMTMessage decodedMessage = new MoveMTMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}