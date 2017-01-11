package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by luis on 11/01/17.
 */
public class ControlValuesTest {
    @Test
    public void testCodeMessageData() throws Exception {

        ControlValuesMessage originalMessage =
                new ControlValuesMessage((byte) 1,11,22,33);

        final byte[] messageData = originalMessage.codeMessage();

        ControlValuesMessage decodedMessage = new ControlValuesMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }
}
