package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class SetLEDColorMessageTest {

    @Test
    public void testCodeMessageData() throws Exception {

        SetLEDColorMessage originalMessage =
                new SetLEDColorMessage(
                        (byte)7,
                        (short)1, (short)5, (short)4);

        final byte[] messageData = originalMessage.codeMessage();

        SetLEDColorMessage decodedMessage =
                new SetLEDColorMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }

}