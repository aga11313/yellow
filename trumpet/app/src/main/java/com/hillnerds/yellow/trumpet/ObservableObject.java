package com.hillnerds.yellow.trumpet;

import java.util.Observable;

/**
 * Created by aga on 11/02/17.
 */

/**
 *
 */
public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    /**
     *
     * @return - returns an instance of the ObservableObject
     */
    public static ObservableObject getInstance() {
        return instance;
    }

    /**
     * Method called when a receives a broadcast.
     * @param data -
     */
    public void updateValue(Object data) {
        synchronized (this) {
            //marks this observable as changed
            setChanged();

            /*When the observable is changed all its observers are notified and the update
             *method is called for all observers of this observable.
             */
            notifyObservers(data);
        }
    }
}
