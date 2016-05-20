/*
 *   Copyright 2016 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2016 Julio Gomez <julio.gomez@mytechia.com>
 * 
 *  This file is part of robobo-rob-interface.
 */
package com.mytechia.robobo.rob;

import java.util.Date;



public class RobDeviceStatus<T extends Enum<T>> {

    private T id;

    private Date lastUpdate;

    public RobDeviceStatus(T id) {
        super();
        this.id = id;
    }

    public T getId() {
        return id;
    }



    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RobDeviceStatus<?> other = (RobDeviceStatus<?>) obj;
        
        return (this.id == other.id) || (this.id != null && this.id.equals(other.id));
        
    }



    
    
}
