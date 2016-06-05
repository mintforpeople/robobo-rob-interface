/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gómez <julio.gomez@mytechia.com>
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
