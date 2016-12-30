package com.mytechia.robobo.rob.comm;

import com.mytechia.robobo.rob.IStopWarningListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage instances of DispatcherStopWarningListener. And it
 * implements methods to fire events of DispatcherStopWarningListener.
 *
 * @author Luis Felipe Llamas Luaces
 */
public class DispatcherStopWarningListener {

    private  final List<IStopWarningListener> stopWarningListeners = new ArrayList<IStopWarningListener>();

    public void subscribeToStopWarning(IStopWarningListener listener){
        if (listener == null){
            return;
        }

        this.stopWarningListeners.add(listener);
    }

    public void unsuscribeFromStopWarning(IStopWarningListener listener){
        this.stopWarningListeners.remove(listener);
    }

    void fireReceivedStopWarning(StopWarningMessage sw){
        for (IStopWarningListener stopWarningListener:stopWarningListeners){
            stopWarningListener.stopWarning(sw);
        }
    }
}
