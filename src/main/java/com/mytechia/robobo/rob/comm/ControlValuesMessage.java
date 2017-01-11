package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.MessageCoder;
import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

import static com.mytechia.robobo.rob.comm.MessageType.ControlValues;

/**
 * Created by luis on 11/01/17.
 */
public class ControlValuesMessage extends RoboCommand {

    private String MOTOR_ID = "MotorId";
    private String START_KI = "StartKi";
    private String PERTURBATIONS_KI = "PerturbationsKi";
    private String STOP_KI = "StopKi";
    private static final String COMMAND_CODE = "commandCode";

    private byte motorId;

    private int startki;
    private int perturbationski;
    private int stopki;

    private byte commandCode;

    public ControlValuesMessage(byte motorId, int startki, int perturbationski, int stopki) {
        super();
        super.setCommandType(MessageType.ControlValues.commandType);
        this.motorId = motorId;
        this.startki = startki;
        this.perturbationski = perturbationski;
        this.stopki = stopki;
    }

    public ControlValuesMessage(byte commandCode) {
        this.setCommandType(ControlValues.commandType);
        this.commandCode = commandCode;
    }
    public ControlValuesMessage(byte[] message) throws MessageFormatException {
        super(message);
        this.setCommandType(ControlValues.commandType);
    }

    @Override
    protected byte[] codeMessageData() throws MessageFormatException {
        MessageCoder messageCoder = this.getMessageCoder();

        messageCoder.writeByte(motorId, MOTOR_ID);

        messageCoder.writeUShort(startki,START_KI);
        messageCoder.writeUShort(perturbationski,PERTURBATIONS_KI);
        messageCoder.writeUShort(stopki,STOP_KI);
        return messageCoder.getBytes();
    }

    @Override
    protected int decodeMessageData(byte[] bytes, int i) throws MessageFormatException {
        MessageDecoder messageDecoder = this.getMessageDecoder();

        this.motorId = messageDecoder.readByte(MOTOR_ID);

        this.startki = messageDecoder.readUShort(START_KI);
        this.perturbationski = messageDecoder.readUShort(PERTURBATIONS_KI);
        this.stopki = messageDecoder.readUShort(STOP_KI);

        return this.getMessageDecoder().getArrayIndex();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ControlValuesMessage that = (ControlValuesMessage) o;

        if (motorId != that.motorId) return false;
        if (startki != that.startki) return false;
        if (perturbationski != that.perturbationski) return false;
        if (stopki != that.stopki) return false;
        return commandCode == that.commandCode;

    }

    @Override
    public int hashCode() {
        int result = (int) motorId;
        result = 31 * result + startki;
        result = 31 * result + perturbationski;
        result = 31 * result + stopki;
        result = 31 * result + (int) commandCode;
        return result;
    }
}
