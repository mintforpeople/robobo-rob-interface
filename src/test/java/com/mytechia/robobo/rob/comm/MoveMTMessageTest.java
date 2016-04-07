package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MoveMTMessageTest {

    @Test
    public void testCodeMessageData() throws Exception {

        MoveMTMessage originalMessage =
                new MoveMTMessage(
                        2,
                        45,
                        1,
                        -30,
                        405);

        final byte[] messageData = originalMessage.codeMessage();

        MoveMTMessage decodedMessage = new MoveMTMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}