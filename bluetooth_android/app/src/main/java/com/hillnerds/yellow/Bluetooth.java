package com.hillnerds.yellow;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by aga on 11/02/17.
 */

public class Bluetooth extends Activity {

    private BluetoothAdapter mBluetoothAdapter;
    //final public REQUEST_ENABLE_BT;

    private BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices;

    public void bluetoothInitilize (){
        //final BluetoothManager bluetoothManager =
        //        (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public boolean bluetoothEnabled (){
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        return true;
    }

    public void turnOn(){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void turnOff(){
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }


    public  void turnVisible(){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    public void listDevices() {
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices) list.add(bt.getName());
        Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
    }
}
