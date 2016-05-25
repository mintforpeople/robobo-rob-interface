/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

public enum LEDsModeEnum {
    
    
    NONE((byte) 0), SHOW_FUNCTIONAL_INFRARED((byte)1), INFRARED((byte)2), DETECT_FALL((byte)4), INFRARED_AND_DETECT_FALL((byte)8), ON_SECUENCE_COLORS((byte)22);

    LEDsModeEnum(byte code) {
        this.code = code;
    }

    public final byte code;
    
    public static LEDsModeEnum toLEDMode(byte code){
        
        for (LEDsModeEnum value : values()) {
            if(value.code==code){
                return value;
            }
        }
        
        return null;
        
    }
     
     


}
