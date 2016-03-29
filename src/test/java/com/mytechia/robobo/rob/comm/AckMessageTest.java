package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class AckMessageTest {

    @Test
    public void testCodeMessageData() throws Exception {


        AckMessage originalMessage = new AckMessage((byte)1);

        final byte[] messageData = originalMessage.codeMessage();

        AckMessage decodedMessage = new AckMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}