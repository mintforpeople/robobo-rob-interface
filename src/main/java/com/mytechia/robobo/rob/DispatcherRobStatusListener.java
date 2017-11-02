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

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is used to manage instances of DispatcherRobStatusListener. And it
 * implements methods to fire events of DispatcherRobStatusListener.
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class DispatcherRobStatusListener {

    private final List<IRobStatusListener> robStatusListeners = new ArrayList<IRobStatusListener>();
    

    public void subscribetoContentChanges(IRobStatusListener robStatusListener) {
        if (robStatusListener == null) {
            return;
        }

        this.robStatusListeners.add(robStatusListener);
    }

    void unsubscribeFromContentChanges(IRobStatusListener contentChangesListener) {
        this.robStatusListeners.remove(contentChangesListener);
    }
    

    void fireStatusMotorsMT(MotorStatus left, MotorStatus right) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            
            robStatusListener.statusMotorsMT(left, right);
        }
    }

    void fireStatusMotorPan(MotorStatus status) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusMotorPan(status);
            
        }
    }

    void fireStatusMotorTilt(MotorStatus status) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusMotorTilt(status);
            
        }
    }

    void fireStatusGaps(Collection<GapStatus> gap) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusGaps(gap);
            
        }
    }
    
    void fireStatusIRSensorStatus(Collection<IRSensorStatus> irSensorStatus) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusIRSensorStatus(irSensorStatus);
            
        }
    }
    
    

    void fireStatusFalls(Collection<FallStatus> fall) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusFalls(fall);
            
        }
    }
    
    
    void fireStatusWallConnection(WallConnectionStatus battery) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusWallConnectionStatus(battery);
            
        }
    }


    void fireStatusBattery(BatteryStatus battery) {
        
        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusBattery(battery);
            
        }
    }
    


    void fireStatusLeds(LedStatus status) {

        for (IRobStatusListener robStatusListener : robStatusListeners) {
            robStatusListener.statusLeds(status);

        }
    }

}
