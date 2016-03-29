/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;

/**
 * Concrete implementations of this interfaces containts the logic to
 * process different types of messages
 *
 * @author Julio Alberto Gomez Fernandez
 */
public interface ICommandHandler {
    
     void receivedAck(AckMessage msgAck) throws UnsupportedMessageTypeException;
    
}
