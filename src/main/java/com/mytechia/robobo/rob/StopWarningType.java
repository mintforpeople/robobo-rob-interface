package com.mytechia.robobo.rob;

/**
 * Enumeration of StopWarning types.
 *
 * @author Gervasio Varela
 */
public enum StopWarningType {
    MOTOR_WARNING((byte) 1),
    IR_WARNING((byte) 2),
    BATTERY_WARNING((byte) 4),
    RANGE_WARNING((byte) 8),
    IR_CONFIG_WARNING((byte) 10);

    private byte type;

    StopWarningType(byte type){
        this.type = type;
    }

    public byte getWarningType(){ return  this.type;}

    public static StopWarningType toStopWarningType(byte type){
        for (StopWarningType stopWarningType: StopWarningType.values()){
            if(stopWarningType.getWarningType() == type){
                return stopWarningType;
            }
        }
        return null;
    }

}
