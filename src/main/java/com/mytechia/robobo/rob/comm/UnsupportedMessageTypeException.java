/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob.comm;


/**
 *
 * @author Julio Alberto Gomez Fernandez
 */
public class UnsupportedMessageTypeException extends RuntimeException {

    public UnsupportedMessageTypeException(String message, Exception exception) {
        super(message, exception);
    }

    public UnsupportedMessageTypeException(String message) {
        super(message);
    }

    public UnsupportedMessageTypeException(Exception exception) {
        super(exception);
    }

}
