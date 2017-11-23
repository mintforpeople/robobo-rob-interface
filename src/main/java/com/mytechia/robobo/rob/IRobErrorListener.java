package com.mytechia.robobo.rob;

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;

public interface IRobErrorListener {

    void robError(CommunicationException ex);
}
