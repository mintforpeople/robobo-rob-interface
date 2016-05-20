/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import static com.mytechia.robobo.rob.comm.MessageType.MaxValueMotors;
import static com.mytechia.robobo.rob.comm.MessageType.OperationModeMessage;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.IMessageBuilder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageFactory;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

/**
 * This class know how to decode a received raw RoboCommand.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class RoboCommandFactory extends MessageFactory {

    public RoboCommandFactory() {

        registerAckMessageBuilder();

        registerRobStatusMessageBuilder();

        reguisterSetRobStatusPeriodMessageBuilder();

        registerSetLEDColorMessageBuilder();

        registerSetLEDsModeMessageBuilder();

        registerMoveMTMessageBuilder();

        registerMovePanTilMessageBuilder();

        registerResetPanTiltOffsetMessageBuilder();
        
        registerMaxValueMotorsBuilder();
        
        registerInfraredConfigurationMessageBuilder();
        
        registerOperationModeMessageBuilder();

    }
    

    private void registerResetPanTiltOffsetMessageBuilder() {
        registerMessageBuilder(
                new IMessageBuilder() {
            @Override
            public byte type() {
                return MessageType.ResetPanTiltOffsetMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new ResetPanTiltOffsetMessage(bytes);
            }
        }
        );
    }

    private void registerMovePanTilMessageBuilder() {
        registerMessageBuilder(
                new IMessageBuilder() {
            @Override
            public byte type() {
                return MessageType.MovePanTiltMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new MovePanTiltMessage(bytes);
            }
        }
        );
    }

    private void registerMoveMTMessageBuilder() {
        registerMessageBuilder(
                new IMessageBuilder() {
            @Override
            public byte type() {
                return MessageType.MoveMTMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new MoveMTMessage(bytes);
            }
        }
        );
    }

    private void registerSetLEDsModeMessageBuilder() {
        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.RobSetLEDsModeMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new RobSetLEDsModeMessage(bytes);
            }
        }
        );
    }

    private void registerSetLEDColorMessageBuilder() {
        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.SetLEDColorMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new SetLEDColorMessage(bytes);
            }
        }
        );
    }

    private void reguisterSetRobStatusPeriodMessageBuilder() {

        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.SetRobStatusPeriodMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new SetRobStatusPeriodMessage(bytes);
            }
        }
        );
    }

    private void registerRobStatusMessageBuilder() {

        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.RobStatusMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new RobStatusMessage(bytes);
            }
        }
        );
    }

    private void registerAckMessageBuilder() {

        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.AckMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new AckMessage(bytes);
            }
        }
        );

    }
    
    private void registerMaxValueMotorsBuilder() {

        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MaxValueMotors.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new MaxValueMotors(bytes);
            }
        }
        );

    }
    
    
    private void registerInfraredConfigurationMessageBuilder() {

        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.InfraredConfigurationMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new InfraredConfigurationMessage(bytes);
            }
        }
        );

    }
    
    
    
    
    private void registerOperationModeMessageBuilder() {

        registerMessageBuilder(
                new IMessageBuilder() {

            @Override
            public byte type() {
                return MessageType.OperationModeMessage.commandType;
            }

            @Override
            public Command buildMessage(byte[] bytes) throws MessageFormatException {
                return new OperationModeMessage(bytes);
            }
        }
        );

    }
    
    

}
