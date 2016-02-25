package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MoveMTMessageTest {


    @Test
    public void testCodeMessageData() throws Exception {


        MoveMTMessage originalMessage =
                new MoveMTMessage(
                        2.1,
                        45.7,
                        1.1,
                        -30.2,
                        405);

        final byte[] messageData = originalMessage.codeMessage();

        MoveMTMessage decodedMessage = new MoveMTMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}