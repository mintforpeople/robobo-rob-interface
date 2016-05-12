/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

public enum LEDsModeEnum {
    
    
    NONE((byte) 0), BATTERY((byte)1), IR((byte)2), PID((byte)3), FALL((byte)4), GAP((byte)5);

    LEDsModeEnum(byte code) {
        this.code = code;
    }

    final byte code;
     
     


}
