package com.mytechia.robobo.rob.comm;

import com.mytechia.robobo.rob.IStopWarningListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage instances of DispatcherRobCommStopWarningListener. And it
 * implements methods to fire events of DispatcherRobCommStopWarningListener.
 *
 * @author Luis Felipe Llamas Luaces
 */
public class DispatcherRobCommStopWarningListener {

    private  final List<IRobCommStopWarningListener> stopWarningListeners = new ArrayList<IRobCommStopWarningListener>();

    public void subscribeToStopWarning(IRobCommStopWarningListener listener){
        if (listener == null){
            return;
        }

        this.stopWarningListeners.add(listener);
    }

    public void unsuscribeFromStopWarning(IRobCommStopWarningListener listener){
        this.stopWarningListeners.remove(listener);
    }

    void fireReceivedStopWarning(StopWarningMessage sw){
        for (IRobCommStopWarningListener stopWarningListener:stopWarningListeners){
            stopWarningListener.stopWarning(sw);
        }
    }
}
