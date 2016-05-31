/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Gervasio Varela <gervasio.varela@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */


package com.mytechia.robobo.rob;

/**
 * Enumeration of movement modes for the MT motors.
 *
 * @author Gervasio Varela
 */
public enum MoveMTMode 
{

    STOP_STOP((byte) 0),
    REVERSE_REVERSE((byte) 1),
    FORWARD_FORWARD((byte) 2),
    FORWARD_REVERSE((byte) 4),
    REVERSE_FORWARD((byte) 8);
    
    
    MoveMTMode(byte mode) {
        this.mode = mode;
    }

    public byte getMode() {
        return this.mode;
    }
    
    public static MoveMTMode toMoveMTMode(byte mode){
        
        for (MoveMTMode moveMtMode : MoveMTMode.values()) {
            if(moveMtMode.getMode()== mode){
                return moveMtMode;
            }
        }
        
        return null;
    }
    
    private byte mode;
    
}
