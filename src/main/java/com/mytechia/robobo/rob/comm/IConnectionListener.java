/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

import java.util.Collection;

/**
 * Description of the Interface
 *
 * @author Julio Alberto Gomez Fernandez
 */
public interface IConnectionListener {

    void roboCommandAcknowledged(RoboCommand roboCommand);
    
    void lostRoboCommands(Collection<RoboCommand> lostMessages);
    
}
