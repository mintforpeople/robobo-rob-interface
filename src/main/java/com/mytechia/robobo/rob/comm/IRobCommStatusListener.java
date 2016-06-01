/**
 * *****************************************************************************
 * <p>
 * Copyright (C) 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2016 Gervasio Varela <gervasio.varela@mytechia.com>
 * <p>
 * This file is part of Robobo RobComm Library.
 * ****************************************************************************
 */
package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;


/**
 * Callback interface to receive ROB-STATUS messages.
 */
public interface IRobCommStatusListener {

    void robStatus(RobStatusMessage rs);
    
    void robCommunicationError(CommunicationException ex);

}
