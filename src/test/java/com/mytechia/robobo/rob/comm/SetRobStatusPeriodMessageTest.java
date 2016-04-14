package com.mytechia.robobo.rob.comm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

/**
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class SetRobStatusPeriodMessageTest {


    @Test
    @Ignore
    public void testCodeMessageData() throws Exception {

        SetRobStatusPeriodMessage originalMessage = new SetRobStatusPeriodMessage(45);

        final byte[] messageData = originalMessage.codeMessage();

        SetRobStatusPeriodMessage decodedMessage = new SetRobStatusPeriodMessage(messageData);

        assertEquals(originalMessage, decodedMessage);

        assertTrue(!originalMessage.getCodingMessageInfo().isEmpty());

        assertTrue(!decodedMessage.getDecodingMessageInfo().isEmpty());

    }


}