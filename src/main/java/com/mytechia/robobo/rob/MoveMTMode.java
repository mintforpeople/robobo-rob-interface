/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gómez <julio.gomez@mytechia.com>
 *   Copyright 2016 Gervasio Varela <gervasio.varela@mytechia.com>
 *
 *   This file is part of Robobo ROB Interface Library.
 *
 *   Robobo ROB Interface Library is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Robobo ROB Interface Library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Robobo ROB Interface Library.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/



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
