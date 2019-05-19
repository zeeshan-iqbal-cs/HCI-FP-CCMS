package com.example.haf.myfreeze;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDeviceEntry> {
    private Activity context;
    private List<BluetoothDeviceEntry> DeviceList;

    BluetoothDeviceAdapter(Activity context, List<BluetoothDeviceEntry> deviceList){
        super(context,R.layout.bluetooth_entry,deviceList);
        this.context =context;
        this.DeviceList = deviceList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View listViewItem = inflater.inflate(R.layout.bluetooth_entry, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.blueNameTextView);
        TextView textViewTemp = (TextView) listViewItem.findViewById(R.id.blueAddressTextView);

        BluetoothDeviceEntry data= DeviceList.get(position);
        textViewName.setText(data.getName());
        textViewTemp.setText(data.getMAc());



        return listViewItem;
    }

}
