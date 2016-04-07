package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class MovePanTiltMessageTest {

    @Test
    public void testCodeMessageData() throws Exception {

        MovePanTiltMessage originalMessage =
                new MovePanTiltMessage((byte)3, 4, 60, 15000);

        final byte[] messageData = originalMessage.codeMessage();

        MovePanTiltMessage decodedMessage = new MovePanTiltMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}