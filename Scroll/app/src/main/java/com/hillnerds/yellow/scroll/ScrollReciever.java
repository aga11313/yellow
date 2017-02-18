package com.hillnerds.yellow.scroll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by aga on 12/02/17.
 */

public class ScrollReciever extends BroadcastReceiver{
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Create an Observable Object with the intent as a value. The intent
         * contains the action name of the received event
         */
        ObservableObject.getInstance().updateValue(intent);
    }
}
