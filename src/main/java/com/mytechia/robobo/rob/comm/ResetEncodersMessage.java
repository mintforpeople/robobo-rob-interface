package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.robobo.rob.RobMotorEnum;

import static com.mytechia.robobo.rob.comm.MessageType.ResetEncodersMessage;

/**
 * Message to reset the wheel encoders of the robobo base
 */
public class ResetEncodersMessage extends RoboCommand {

    private byte motor;
    public ResetEncodersMessage(RobMotorEnum motor){
        super();
        this.setCommandType(ResetEncodersMessage.commandType);

        this.motor = motor.code;
    }
    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        return 0;
    }

    @Override
    protected byte[] codeMessageData() throws MessageFormatException {

        MessageCoder messageCoder = this.getMessageCoder();
        messageCoder.writeByte(motor, "MOTOR ID");



        return messageCoder.getBytes();    }
}
