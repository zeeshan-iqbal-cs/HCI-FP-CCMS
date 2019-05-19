package com.example.haf.myfreeze;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TimeTempAdapter extends ArrayAdapter<TimeTemp> {

    private Activity context;
    private List<TimeTemp> timetempList;

    TimeTempAdapter(Activity context, List<TimeTemp> timetempList){
        super(context,R.layout.list_layout,timetempList);
        this.context =context;
        this.timetempList = timetempList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.deviceNameTextView);
        TextView textViewTemp = (TextView) listViewItem.findViewById(R.id.deviceTempTextView);

        TimeTemp data= timetempList.get(position);
        textViewName.setText(data.getTime());
        textViewTemp.setText(data.getTemp()+ (char) 0x00B0 + "C");



        return listViewItem;
    }

}
