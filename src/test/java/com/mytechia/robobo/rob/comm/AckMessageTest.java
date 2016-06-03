package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

/**
 *
 * Created by Victor Sonora Pombo
 */
public class AckMessageTest {

    @Test
    @Ignore
    public void testCodeMessageData() throws Exception {


        AckMessage originalMessage = new AckMessage();

        final byte[] messageData = originalMessage.codeMessage();

        AckMessage decodedMessage = new AckMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}