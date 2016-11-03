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

import static com.mytechia.robobo.rob.WallConnectionStatus.WallConnectionStatusId.WallConnectionStatusId1;

public class WallConnectionStatus extends RobDeviceStatus<WallConnectionStatus.WallConnectionStatusId> {

    private byte wallConnetion;

    public WallConnectionStatus() {
        super(WallConnectionStatusId1);
    }

    public byte getWallConnetion() {
        return wallConnetion;
    }

    public void setWallConnetion(byte wallConnetion) {
        this.wallConnetion = wallConnetion;
    }

    public enum WallConnectionStatusId {
        WallConnectionStatusId1;
    }

}
