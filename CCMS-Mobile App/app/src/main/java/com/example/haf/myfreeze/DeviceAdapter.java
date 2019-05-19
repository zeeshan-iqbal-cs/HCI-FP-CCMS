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



public class DeviceAdapter extends ArrayAdapter<DeviceData> {
    private Activity context;
    private List<DeviceData> DeviceList;

    DeviceAdapter(Activity context, List<DeviceData> deviceList){
        super(context,R.layout.list_layout,deviceList);
        this.context =context;
        this.DeviceList = deviceList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.deviceNameTextView);
        TextView textViewTemp = (TextView) listViewItem.findViewById(R.id.deviceTempTextView);

        DeviceData data= DeviceList.get(position);
        textViewName.setText(data.getName());
        textViewTemp.setText(Integer.toString(data.getTemp())+ (char) 0x00B0 + "C");



        return listViewItem;
    }

}
