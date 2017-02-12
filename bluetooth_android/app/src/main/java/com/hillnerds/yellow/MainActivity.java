package com.hillnerds.yellow;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.renderscript.ScriptGroup;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_ENABLE_BT = 1;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
    };
    final static int PERMISSIONS_REQUEST_CODE = 1;

    private MicrobitCommsService mBoundService;
    private boolean serviceBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((MicrobitCommsService.LocalBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermissions();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        doBindService();
        Log.i("onCreate","service Bind.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            BluetoothHelper bHelp = new BluetoothHelper(this);
            bHelp.scanLeDevice(true);
        } catch (Exception ex) {
            // TODO: also catch location enabled
            Log.e("BluetoothHelper", ex.getMessage());
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    void doBindService() {
        startService(new Intent(this,
                MicrobitCommsService.class));
    }

    private void askPermissions() {
        int permissionCheck;
        ArrayList<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            permissionCheck = ContextCompat.checkSelfPermission(this, p);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(
                    new String[listPermissionsNeeded.size()]), PERMISSIONS_REQUEST_CODE);

        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (grantResults.length > 0){
            switch (requestCode) {
                case PERMISSIONS_REQUEST_CODE:
                    for (int i = 0; i < grantResults.length; i++) {
                        switch (grantResults[i]) {
                            case (PackageManager.PERMISSION_GRANTED):
                                Log.i("onRequestPermission", "Permission Granted");
                                break;
                            case (PackageManager.PERMISSION_DENIED):
                                Log.i("onRequestPermission", "Permission Denied");
                                Toast.makeText(this,
                                        MessageFormat.format("Permission {0} not granted",
                                                permissions[i]), Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Log.wtf("onRequestPermission",
                                        "Permission neither granted nor denied");
                        }

                    }
                    break;
                default:
                    Log.wtf("MainActivity",
                            MessageFormat.format("Weird permissions request code returned {0}",
                                    requestCode));
                    break;
            }
        }
    }
}
