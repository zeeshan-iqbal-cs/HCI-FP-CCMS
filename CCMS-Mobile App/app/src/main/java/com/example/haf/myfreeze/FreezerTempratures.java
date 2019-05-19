package com.example.haf.myfreeze;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FreezerTempratures extends AppCompatActivity {

    private ListView tempsList;
    private TextView Name_TV,Temp_TV;
    FirebaseDatabase database;
    DatabaseReference databaseRef;

    List<TimeTemp> tempList;

    String Temp;
    String Time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freezer_tempratures);
        Intent i = getIntent();
        String name = i.getStringExtra("freezer_name");
        String cTemp = i.getStringExtra("freezer_ctemp");
        Log.d("syapa", "Value is: " + cTemp);
        String userId = i.getStringExtra("user_id");

        tempsList = (ListView)findViewById(R.id.C_FreezerListView);
        Name_TV = (TextView)findViewById(R.id.FreezerNameTextView);
        Temp_TV = (TextView)findViewById(R.id.CurrentTempTextView);
        Name_TV.setText(name);
        Temp_TV.setText(cTemp+ (char) 0x00B0 + "C");

        tempList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        databaseRef = database.getReference("Users/"+userId+"/"+name+"/Time_temp");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
//                    Temp = data.getKey();
                    tempList.add(new TimeTemp(Integer.toString(data.getValue(Integer.class)),data.getKey()));
                }
                TimeTempAdapter adapter = new TimeTempAdapter(FreezerTempratures.this,tempList);
                tempsList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
