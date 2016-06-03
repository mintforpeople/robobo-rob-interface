package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.channel.IAddress;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Victor Sonora Pombo
 */
public class DummyCommunicationChannelTest {


    @Test
    public void testSend() throws Exception {

        DummyCommunicationChannel channelReceiver
                = new DummyCommunicationChannel();

        channelReceiver.initMessageFactory();

        DummyCommunicationChannel channelSender
                = new DummyCommunicationChannel(channelReceiver);

        SetLEDColorMessage originalMessage =
                new SetLEDColorMessage(
                        (byte)7, (short)1, (short)5, (short)4);

        channelSender.send(new FakeAddress(), originalMessage);

        final Command receivedMessage = channelReceiver.receiveMessage();

        assertEquals(originalMessage, receivedMessage);

    }


    class FakeAddress implements IAddress {

        public String getId() {
            return "id5";
        }

        public String getName() {
            return "name";
        }

        public boolean equals(IAddress iAddress) {
            return true;
        }

    }


}