/*******************************************************************************
 *
 *   Copyright 2017 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2017 Julio Gómez <julio.gomez@mytechia.com>
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
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;

import java.util.ArrayList;
import java.util.List;


public class DispatcherRobCommErrorListener {

    private  final List<IRobCommErrorListener> robErrorListeners = new ArrayList<IRobCommErrorListener>();

    public void subscribeToRobCommError(IRobCommErrorListener robCommStatusListener) {
        if (robCommStatusListener == null) {
            return;
        }

        this.robErrorListeners.add(robCommStatusListener);
    }

    void unsubscribeFromRobCommError(IRobCommErrorListener robCommStatusListener) {
        this.robErrorListeners.remove(robCommStatusListener);
    }

    void fireRobCommError(CommunicationException ex) {

        for (IRobCommErrorListener robCommErrorListener : robErrorListeners) {
            robCommErrorListener.robError(ex);
        }

    }
}
