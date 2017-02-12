package com.hillnerds.yellow;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by aga on 11/02/17.
 */

public class Broadcasts {
    public static void sendFinger(Context ctx, int finger, boolean down){
        String direction;
        if (down){
            direction = "DOWN";
        } else {
            direction = "UP";
        }

        Intent intent = new Intent();

        switch (finger){
            case (0):
                intent.setAction("com.hillnerds.yellow.FINGER0_" + direction);
                Log.i("sendFInger", MessageFormat.format("The finger 0 {0} message has been broadcasted", direction));
                break;
            case (1):
                Log.i("sendFInger", MessageFormat.format("The finger 1 {0} message has been broadcasted", direction));
                intent.setAction("com.hillnerds.yellow.FINGER1_" + direction);
                break;
            case (2):
                Log.i("sendFInger", MessageFormat.format("The finger 2 {0} message has been broadcasted", direction));
                intent.setAction("com.hillnerds.yellow.FINGER2_" + direction);
                break;
            default:
                break;
        }

        ctx.sendBroadcast(intent);
    }
}
