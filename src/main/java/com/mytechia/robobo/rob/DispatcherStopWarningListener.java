/*******************************************************************************
 *
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Luis Llamas <luis.llamas@mytechia.com>
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

import com.mytechia.robobo.rob.comm.StopWarningMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage instances of StopWarningListener. And it
 * implements methods to fire events of StopWarningListener.
 *
 * @author Luis Felipe Llamas Luaces
 */
public class DispatcherStopWarningListener {

    private final List<IStopWarningListener> stopWarningListeners = new ArrayList<IStopWarningListener>();


    public void subscribetoStopWarnings(IStopWarningListener swListener) {
        if (swListener == null) {
            return;
        }

        this.stopWarningListeners.add(swListener);
    }

    void unsubscribeFromStopWarnings(IStopWarningListener swListener) {
        this.stopWarningListeners.remove(swListener);
    }


    void fireStatusBattery(StopWarningMessage swmsg) {

        for (IStopWarningListener swListener : stopWarningListeners) {
            swListener.stopWarning(swmsg.getMessage());

        }
    }
}
