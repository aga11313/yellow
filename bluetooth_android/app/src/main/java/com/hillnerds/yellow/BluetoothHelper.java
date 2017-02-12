package com.hillnerds.yellow;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cameron on 11/02/17.
 */

class BluetoothHelper {
    private static final String TAG = "BluetoothHelper";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mScan;
    private Handler mHandler;

    private static final int SCAN_PERIOD = 5000;
    private static final String pinIOGATTUUID =
            "E95D127B-251D-470A-A062-FA1922DFA9A8";
    private boolean deviceFound = false;

    private List<ScanFilter> microBitFilter;
    private ScanSettings mScanSettings;

    private int count = 0;

    private Context mctx;

    BluetoothHelper(Context ctx) throws Exception {
        final BluetoothManager mManager =
                (BluetoothManager) ctx.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            throw new Exception("Bluetooth not enabled");
        }

        mHandler = new Handler();

        mScan = mBluetoothAdapter.getBluetoothLeScanner();
        microBitFilter = buildScanFilter();
        mScanSettings = buildScanSettings();
        Log.i(TAG, "BluetoothHelper initialised");

        // TODO: Remove this when possible
        this.mctx = ctx;
    }

    private List<ScanFilter> buildScanFilter() {
        ScanFilter.Builder build = new ScanFilter.Builder();
        //build.setServiceUuid(ParcelUuid.fromString(pinIOGATTUUID));
        build.setDeviceName("BBC micro:bit [tateg]");
        ArrayList<ScanFilter> lFilt = new ArrayList<>();
        lFilt.add(build.build());
        return lFilt;
    }

    private ScanSettings buildScanSettings() {
        ScanSettings.Builder build = new ScanSettings.Builder();
        build.setScanMode(ScanSettings.SCAN_MODE_BALANCED);
        build.setReportDelay(0);
        return build.build();
    }

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "GATT connected");
                Log.d(TAG, "Attempting to discover services: " + gatt.discoverServices());
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d(TAG, "GATT disconnected");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "Discovered services successfully");
                if (gatt.getService(UUID.fromString(pinIOGATTUUID)) == null) {
                    Log.w(TAG, "Device doesn't support pin IO");
                }
                gatt.connect();
            } else {
                Log.w(TAG, "Didn't discover services w/ code " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.i(TAG, "Read characteristic");
        }
    };

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            if (deviceFound) return;
            deviceFound = true;
            String dName = result.getScanRecord().getDeviceName();
            Log.i(TAG, MessageFormat.format("Result received {0}", dName));
            BluetoothDevice dev = result.getDevice();
            dev.connectGatt(mctx, false, mGattCallback);
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.w(TAG,
                    MessageFormat.format("Scan failed with code {0}", errorCode));
        }
    };

    public void scanLeDevice(final boolean enable) {
        Log.d(TAG, MessageFormat.format("Entered scanLeDevice {0}", count));
        count++;
        if (enable) {
            if (deviceFound) {
                return;
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScan.stopScan(mScanCallback);
                    Log.d(TAG, "Scan stopped");
                    scanLeDevice(true);
                }
            }, SCAN_PERIOD);

            Log.d(TAG, "Scan started");
            mScan.startScan(
                    microBitFilter,
                    mScanSettings,
                    mScanCallback);
        } else {
            mScan.stopScan(mScanCallback);
        }
    }
}
