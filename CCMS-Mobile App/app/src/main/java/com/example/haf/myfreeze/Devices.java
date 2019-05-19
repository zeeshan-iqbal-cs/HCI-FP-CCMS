package com.example.haf.myfreeze;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Devices extends AppCompatActivity {

    private Button AddBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    private ListView device_list_view;
    private List<DeviceData> DeviceDataList;

    private TextView userID_TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        AddBtn = (Button)findViewById(R.id.AddDeviceBtn);
        userID_TV = (TextView)findViewById(R.id.UserIdTextView);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DeviceDataList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Users/"+user.getUid()); //Users/user_id/device_id/Temp
        device_list_view = (ListView)findViewById(R.id.DevicesListView);


        try {
            if( user != null){
                String id = user.getUid().substring(0,5);
                userID_TV.setText(id);



            }
            else
            {
                userID_TV.setText("Yet Not Registerd");
                userID_TV.setTextColor(Color.RED);

//                startActivity(new Intent(Devices.this,MainActivity.class));
            }


        }
        catch (Exception e)
        {
            userID_TV.setText("something went wrongd");
            //
        }

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Devices.this,AddDevice.class));
                finish();

            }
        });
        device_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(Devices.this,FreezerTempratures.class);
                i.putExtra("user_id",user.getUid());
                i.putExtra("freezer_name",DeviceDataList.get(position).getName());
                i.putExtra("freezer_ctemp",Integer.toString(DeviceDataList.get(position).getTemp()));
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DeviceDataList.clear();
                for(DataSnapshot device :dataSnapshot.getChildren()) {
                    String name = device.child("name").getValue(String.class);
                    Integer Temp = device.child("Temp").getValue(Integer.class);
                    DeviceData d = new DeviceData(Temp,name);
//                    DeviceData d = device.getValue(DeviceData.class);
                    DeviceDataList.add(d);
                }
                DeviceAdapter adapter = new DeviceAdapter(Devices.this,DeviceDataList);
                device_list_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
