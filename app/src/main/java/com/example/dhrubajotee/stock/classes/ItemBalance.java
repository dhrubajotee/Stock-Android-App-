package com.example.dhrubajotee.stock.classes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dhrubajotee.stock.R;
import com.example.dhrubajotee.stock.arrayAdapters.BalanceArrayAdapter;
import com.example.dhrubajotee.stock.arrayAdapters.ItemGroupArrayAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ItemBalance extends AppCompatActivity {

    ListView list;
    ArrayList<Balance> balances;
    DatabaseReference databaseReference;
    DatabaseReference databaseBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_balance);

        list = (ListView) findViewById(R.id.list);
        balances=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("items");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                String idNo = dataSnapshot.getKey().toString();
                String itemName = String.valueOf(newPost.get("item"));
                String balanceAmount = String.valueOf(newPost.get("balance"));
                Balance balance=new Balance();
                balance.setId(idNo);
                balance.setItemName(itemName);
                balance.setBalance(balanceAmount);
                balances.add(balance);

                //Toast.makeText(ItemBalance.this, idNo, Toast.LENGTH_SHORT).show();
                //Toast.makeText(ItemBalance.this, balanceAmount, Toast.LENGTH_SHORT).show();
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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setList()
    {
        BalanceArrayAdapter balanceArrayAdapter=new BalanceArrayAdapter(this,balances);
        list.setAdapter(balanceArrayAdapter);
    }
}
