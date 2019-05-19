package com.example.haf.myfreeze;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddDevice extends Activity {

    ListView listView;
    List<BluetoothDevice> s;
    BluetoothAdapter mBluetoothAdapter;
    Button ScanBtn;BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        listView = (ListView)findViewById(R.id.pairedListView);
        final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    startActivity(new Intent(AddDevice.this,RegisterDevice.class));
                    mmDevice = s.get(position);
                    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                    mmSocket.connect();
                    mmOutputStream = mmSocket.getOutputStream();
                    mmInputStream = mmSocket.getInputStream();
                    String msg = "New Makeistan,makershaker\0"; //myTextbox.getText().toString();
                    mmOutputStream.write(msg.getBytes());
                    ScanBtn.setText("Data Sent");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        ScanBtn = (Button)findViewById(R.id.ScanBtn);
        ScanBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(bReciever, filter);
                mBluetoothAdapter.startDiscovery();
            }
        });
        List<BluetoothDeviceEntry> s = new ArrayList<>();
        for(BluetoothDevice bt : pairedDevices){
            s.add(new BluetoothDeviceEntry(bt.getName(),bt.getAddress()));

        }

        BluetoothDeviceAdapter adapter = new BluetoothDeviceAdapter(AddDevice.this,s);
        listView.setAdapter(adapter);

    }
    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                s.add(device);
                mBluetoothAdapter.notify();

            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(bReciever);
    }
}
//
//
//import android.annotation.SuppressLint;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class AddDevice extends AppCompatActivity {
//
//
//    private static final int REQUEST_ENABLE_BT = 5;
//    private List<BluetoothDeviceEntry> bluetoothDeviceEntryList;
//    private Button ScanBtn;
//
//
//    private ListView bluetoothDevices;
//
//    @SuppressLint("HandlerLeak")
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_add_device);
//        bluetoothDeviceEntryList= new ArrayList<>();
//
//        bluetoothDevices = (ListView)findViewById(R.id.pairedListView);
//        ScanBtn = (Button)findViewById(R.id.ScanBtn);
//        ScanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent discoverableIntent =
//                        new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//                startActivity(discoverableIntent);
//
//            }
//        });
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (!bluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }
//
//        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//
//        if (pairedDevices.size() > 0) {
//            // There are paired devices. Get the name and address of each paired device.
//            for (BluetoothDevice device : pairedDevices) {
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//                bluetoothDeviceEntryList.add(new BluetoothDeviceEntry(deviceName,deviceHardwareAddress));
//            }
//        }
//
//
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(receiver, filter);
//
//
//
//
//
//    }
//    private final BroadcastReceiver receiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Discovery has found a device. Get the BluetoothDevice
//                // object and its info from the Intent.
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//            }
//        }
//    };
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        // Don't forget to unregister the ACTION_FOUND receiver.
//        unregisterReceiver(receiver);
//    }
//
//}

