package com.mytechia.robobo.rob;

/**
 * Used to select motors in the encoder reset message
 */
public enum RobMotorEnum {

    ALL_MOTOR((byte) 2),LEFT_MOTOR((byte) 1),RIGHT_MOTOR((byte) 2);

    RobMotorEnum(byte code) {
        this.code = code;
    }

    public final byte code;
}
