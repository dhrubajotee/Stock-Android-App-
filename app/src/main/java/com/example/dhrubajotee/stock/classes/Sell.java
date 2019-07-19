package com.example.dhrubajotee.stock.classes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dhrubajotee.stock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sell extends AppCompatActivity {
    EditText itemId;
    EditText sell;
    EditText branch;
    Button post;
    DatabaseReference databaseItems;
    DatabaseReference databaseCredit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        itemId = (EditText) findViewById(R.id.sellId);
        branch = (EditText) findViewById(R.id.branchId);
        sell = (EditText) findViewById(R.id.editSell);
        post = (Button) findViewById(R.id.sell);

        databaseItems = FirebaseDatabase.getInstance().getReference("items");
        databaseCredit = FirebaseDatabase.getInstance().getReference("debit");


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseItems.child(itemId.getText().toString()).child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int balance= Integer.parseInt((String)dataSnapshot.getValue());
                        int newBalance= balance-Integer.parseInt(sell.getText().toString());
                        databaseItems.child(itemId.getText().toString()).child("balance").setValue(Integer.toString(newBalance));
                        Date date=new Date();
                        String currentDate=new SimpleDateFormat("dd-MM-yyyy").format(date).toString();

                        DatabaseReference databaseReference=databaseCredit.push();
                        databaseReference.child("Id").setValue(itemId.getText().toString());
                        databaseReference.child("BranchId").setValue(branch.getText().toString());
                        databaseReference.child("Date").setValue(currentDate);
                        databaseReference.child("Debit").setValue("0");
                        databaseReference.child("Credit").setValue(sell.getText().toString());
                        databaseReference.child("Balance").setValue(Integer.toString(newBalance));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



    }
}
