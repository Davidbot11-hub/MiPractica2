package com.unl.practica2.base.Controller.data_struct.queque;

import com.unl.practica2.base.Controller.data_struct.list.LinkedList;

public class QuequeImplementation<E> extends LinkedList<E> {
    private Integer top;

    public Integer getTop() {
        return this.top;
    }

    public QuequeImplementation(Integer top){
        this.top = top;
    }

    protected Boolean isFullQueque() {
        return this.top >= getLength();
    }

    protected void queque(E info) throws Exception {
        if(!isFullQueque()) {
            add(info);
        } else {
            throw new ArrayIndexOutOfBoundsException("Queque full");
        }
    }
    protected E dequeque() throws Exception {       
        return deleteFirst();
        
    }
}
