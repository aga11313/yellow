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
