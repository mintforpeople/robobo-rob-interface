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

import com.mytechia.robobo.rob.GapStatus.GapStatusId;

public class GapStatus extends RobDeviceStatus<GapStatusId> {

    private boolean gap;

    public GapStatus(GapStatusId id) {
        super(id);
    }

    public boolean isGap() {
        return gap;
    }

    void setGap(boolean gap) {
        this.gap = gap;
    }
    
    public static enum GapStatusId{
        Gap1, Gap2, Gap3, Gap4;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GapStatus [getId()=");
        builder.append(getId());
        builder.append(", getLastUpdate()=");
        builder.append(getLastUpdate());
        builder.append(", gap=");
        builder.append(gap);
        builder.append("]");
        return builder.toString();
    }

}
