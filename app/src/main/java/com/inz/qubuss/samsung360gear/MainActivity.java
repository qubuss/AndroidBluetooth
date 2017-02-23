package com.inz.qubuss.samsung360gear;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView logiTV;
    BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    private Switch blueSwitch;
    Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        logiTV = (TextView) findViewById(R.id.logiTV);
        blueSwitch = (Switch) findViewById(R.id.onBlueswitch);

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Nie wsperam BT", Toast.LENGTH_SHORT).show();
        }

        blueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setupBlueTooth(getApplicationContext());
                }else{
                    mBluetoothAdapter.disable();
                }
            }
        });


    }

    private void setupBlueTooth(Context context){
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(context, "Włączyłem BT", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "BT jest już włączony", Toast.LENGTH_SHORT).show();
        }
    }

    public void znajdzUrzadzenia(View view) {
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                logiTV.append(deviceName+" "+deviceHardwareAddress+"\n");
            }
        }

    }
}
