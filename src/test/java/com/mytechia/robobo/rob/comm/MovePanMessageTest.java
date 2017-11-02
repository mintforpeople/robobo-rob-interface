package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Victor Sonora Pombo
 */
public class MovePanMessageTest {

    @Test
    public void testCodeMessageData() throws Exception {

        MovePanMessage originalMessage =
                new MovePanMessage(4, 60);

        final byte[] messageData = originalMessage.codeMessage();

        MovePanMessage decodedMessage = new MovePanMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}