/*******************************************************************************
 *
 *   Copyright 2017 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2017 Luis Llamas <luis.llamas@mytechia.com>
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
 * Class representing the color of the Rob leds
 */
public class LedStatus extends RobDeviceStatus<LedStatus.LedStatusId> {

    private int[] color;

    public LedStatus(LedStatusId id) {
        super(id);
        color =new int[3];
    }

    /**
     * Sets the led color
     * @param r red channeñ
     * @param g green channel
     * @param b blue channel
     */
    public void setColor(int r, int g, int b){
        color[0] = r;
        color[1] = g;
        color[2] = b;
    }

    /**
     * Gets the led color
     * @return int array [red, green, blue]
     */
    public int[] getColor(){
        return color;
    };

    public enum LedStatusId{
        LedStatus1,LedStatus2,LedStatus3,
        LedStatus4,LedStatus5,LedStatus6,
        LedStatus7
    }
}
