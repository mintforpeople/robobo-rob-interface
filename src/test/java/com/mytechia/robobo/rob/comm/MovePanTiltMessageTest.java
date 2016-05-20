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

        OldMovePanTiltMessage originalMessage =
                new OldMovePanTiltMessage((byte)3, (short)4, 60, 15000);

        final byte[] messageData = originalMessage.codeMessage();

        OldMovePanTiltMessage decodedMessage = new OldMovePanTiltMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}