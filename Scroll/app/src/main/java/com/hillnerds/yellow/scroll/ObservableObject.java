package com.hillnerds.yellow.scroll;

import java.util.Observable;

/**
 * Created by aga on 12/02/17.
 */

public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    public static ObservableObject getInstance() {
        return instance;
    }

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
