package com.hillnerds.yellow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MicrobitCommsService extends Service {
    Thread t;
    private final String TAG = "MicrobitCommsService";
    private final LocalBinder mBinder = new LocalBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startCode) {
        Log.i(TAG, "before t");
        t = new Thread() {
            @Override
            public void run() {
                Log.i("onStartCommand", "in onStartCommand");
                while (true) {
                    Broadcasts.sendFinger(MicrobitCommsService.this, 0, true);
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        Log.i("testBroadcasts", e.toString());
                    }
                    Broadcasts.sendFinger(MicrobitCommsService.this, 0, false);
                    try {
                        sleep(50);
                    } catch (Exception e) {
                        Log.i("testBroadcasts", e.toString());
                    }
                    Broadcasts.sendFinger(MicrobitCommsService.this, 1, true);
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        Log.i("testBroadcasts", e.toString());
                    }
                    Broadcasts.sendFinger(MicrobitCommsService.this, 1, false);
                    try {
                        sleep(50);
                    } catch (Exception e) {
                        Log.i("testBroadcasts", e.toString());
                    }
                }
            }
        };
        t.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            t.join(100);
        } catch (InterruptedException ex) {
            Log.w(TAG, "t.join was interrupted");
        }
    }

    public class LocalBinder extends Binder {
        public MicrobitCommsService getService() {
            return MicrobitCommsService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
