package com.hillnerds.yellow.trumpet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aga on 11/02/17.
 */

public class FingerReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    /**
     * Method called when a receiver .FingerReceiver is activated (specified in the manifest)
     * Called every time a FINGER_DOWN or FINGER_UP action is received
     */
    public void onReceive(Context context, Intent intent) {

        /*
         * Create an Observable Object with the intent as a value. The intent
         * contains the action name of the received event
         */
        ObservableObject.getInstance().updateValue(intent);
    }
}
