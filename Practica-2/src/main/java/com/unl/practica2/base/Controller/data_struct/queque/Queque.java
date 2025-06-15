package com.unl.practica2.base.Controller.data_struct.queque;

public class Queque <E> {
    private QuequeImplementation<E> queque;
    public Queque(Integer top) {
        queque = new QuequeImplementation<>(top);
    }
    public Boolean queque(E data) {
        try {
            queque.queque(data);
            return true;
        } catch (Exception e) {
            return false;
            // TODO: handle exception
        }
    }

    public E dequeque() {
        try {
            return queque.dequeque();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public Boolean isFullQueque() {
        return queque.isFullQueque();
    }

    public Integer top() {
        return queque.getTop();
    }

    public Integer size() {
        return queque.getLength();
    }
}
