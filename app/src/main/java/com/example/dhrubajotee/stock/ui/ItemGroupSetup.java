package com.example.dhrubajotee.stock.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dhrubajotee.stock.R;
import com.example.dhrubajotee.stock.arrayAdapters.ItemGroupArrayAdapter;
import com.example.dhrubajotee.stock.classes.Item;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemGroupSetup extends AppCompatActivity {
    EditText name;
    Button add;
    ListView list;
    DatabaseReference databaseItems;

    ArrayList<Item> itemList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_group_setup);


        name = (EditText) findViewById(R.id.editText);
        add = (Button) findViewById(R.id.add);

        list = (ListView) findViewById(R.id.list);

        databaseItems = FirebaseDatabase.getInstance().getReference("group");



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        itemList=new ArrayList<>();


        setList();

    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    String itemName = itemSnapshot.getValue(String.class);
                    Item item=new Item();
                    item.setId(itemList.size()+1);
                    item.setItemName(itemName);
                    itemList.add(item);
                }
                setList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void setList()
    {
        ItemGroupArrayAdapter itemGroupArrayAdapter=new ItemGroupArrayAdapter(this,itemList);
        list.setAdapter(itemGroupArrayAdapter);

    }


    private void addItem(){
        String groupName = name.getText().toString().trim();

        if(!TextUtils.isEmpty(groupName)){
            String id =  databaseItems.push().getKey();
            databaseItems.child(id).setValue(groupName);
            Toast.makeText(this,"Group added",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Please Enter A Text",Toast.LENGTH_LONG).show();
    }
}
