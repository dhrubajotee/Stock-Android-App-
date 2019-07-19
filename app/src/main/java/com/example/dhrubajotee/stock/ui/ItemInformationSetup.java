package com.example.dhrubajotee.stock.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dhrubajotee.stock.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemInformationSetup extends AppCompatActivity {

    EditText item;
    Button add;
    Spinner spinner;
    DatabaseReference databaseGroups;
    DatabaseReference databaseItems;


    List<String> list;
    int items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_information_setup);
        item = (EditText) findViewById(R.id.editText2);
        add = (Button) findViewById(R.id.addNew);
        spinner = (Spinner) findViewById(R.id.spinner);

        databaseGroups = FirebaseDatabase.getInstance().getReference("group");
        databaseItems = FirebaseDatabase.getInstance().getReference("items");

        list = new ArrayList<String>();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items=0;
                databaseItems.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        items++;
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                databaseItems.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        addItems();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseGroups.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    String itemName = itemSnapshot.getValue(String.class);
                    list.add(itemName);
                }
               // Toast.makeText(ItemInformationSetup.this, Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
                showSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void showSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    void addItems(){
        String itemName = item.getText().toString().trim();

        if(!TextUtils.isEmpty(itemName)){
            databaseItems.child(Integer.toString(items+1)).child("item").setValue(itemName);
            databaseItems.child(Integer.toString(items+1)).child("group").setValue(spinner.getSelectedItem().toString());
            databaseItems.child(Integer.toString(items+1)).child("balance").setValue("0");

            Toast.makeText(this,"Item added",Toast.LENGTH_LONG).show();
        }
    }
}
