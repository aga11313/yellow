package com.hillnerds.yellow.trumpet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aga on 11/02/17.
 */

public class FingerReciever extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("onRecieve", "in onRecive");
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        //Toast.makeText(context, log, Toast.LENGTH_LONG).show();
        ObservableObject.getInstance().updateValue(intent);
    }
}
