package com.hillnerds.yellow;

import android.content.Intent;

/**
 * Created by aga on 11/02/17.
 */

public class Broadcasts {
    public void sendBroadcast(){

        Intent intent = new Intent();
        intent.setAction("com.hillnerds.yellow.FINGER_0");
        intent.putExtra("data","Notice me senpai!");
        sendBroadcast(intent);

    }
}
