/**
 * *****************************************************************************
 * <p>
 * Copyright (C) 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2016 Victor Sonora Pombo <victor.pombo@mytechia.com>
 * <p>
 * This file is part of robobo-rob-interface.
 * ****************************************************************************
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.IMessageBuilder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageFactory;
import com.mytechia.commons.framework.simplemessageprotocol.channel.IAddress;
import com.mytechia.commons.framework.simplemessageprotocol.channel.INetworkBasicCommunicationChannel;
import com.mytechia.commons.framework.simplemessageprotocol.channel.ReceiveResult;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

/**
 *
 * Created by Victor Sonora Pombo <victor.pombo@mytechia.com>.
 */
public class DummyCommunicationChannel implements INetworkBasicCommunicationChannel {


    private DummyCommunicationChannel otherChannel;

    private byte[] messageData = null;

    private MessageFactory messageFactory = null;


    public DummyCommunicationChannel(DummyCommunicationChannel channel) {

        this.otherChannel = channel;

    }


    public DummyCommunicationChannel() { }



    public void initMessageFactory() {

        this.messageFactory = new MessageFactory();

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 0;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new AckMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 1;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new RobStatusMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 2;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new SetRobStatusPeriodMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 3;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new SetLEDColorMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 4;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new RobSetLEDsModeMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 5;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new MoveMTMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 6;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new OldMovePanTiltMessage(bytes);
                    }
                }
        );

        this.messageFactory.registerMessageBuilder(
                new IMessageBuilder() {
                    public byte type() {
                        return 7;
                    }

                    public Command buildMessage(byte[] bytes) throws MessageFormatException {
                        return new ResetPanTiltOffsetMessage(bytes);
                    }
                }
        );

    }



    public void setMessageData(byte[] messageData) {

        this.messageData = messageData;

    }


    public void send(IAddress iAddress, byte[] bytes, int i, int i1) throws CommunicationException {
        throw new CommunicationException("Not implemented");
    }

    public void send(IAddress iAddress, Command command) throws CommunicationException {

        if (null != this.otherChannel) {
            this.otherChannel.setMessageData(command.codeMessage());
        }

    }

    public ReceiveResult receive(byte[] bytes, int i, int i1, long l) throws CommunicationException {
        throw new CommunicationException("Not implemented");
    }

    public ReceiveResult receive(byte[] bytes, int i, int i1) throws CommunicationException {
        throw new CommunicationException("Not implemented");
    }

    public ReceiveResult receive(byte[] bytes) throws CommunicationException {
        throw new CommunicationException("Not implemented");
    }

    public ReceiveResult receive() throws CommunicationException {
        throw new CommunicationException("Not implemented");
    }


    public Command receiveMessage() throws CommunicationException {

        if (null != this.messageData && null != this.messageFactory) {

            return this.messageFactory.decodeMessage(this.messageData);

        }

        return null;
    }


    public void registerMessageFactory(MessageFactory messageFactory) {

        this.messageFactory = messageFactory;

    }


}
