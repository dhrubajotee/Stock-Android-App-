package com.example.dhrubajotee.stock.classes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrubajotee.stock.R;
import com.example.dhrubajotee.stock.arrayAdapters.LedgerArrayAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static com.example.dhrubajotee.stock.R.id.branchId;
import static com.example.dhrubajotee.stock.R.id.editText;

public class SpecificLedger  extends AppCompatActivity {

    EditText getid;
    EditText branchid;
    EditText fromdate;
    EditText todate;
    Button submit;
    TextView fromDate;
    TextView toDate;

    ListView list;
    ArrayList<Ledger> ledgers;
    DatabaseReference databaseReference,databaseReferenceCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_ledger);
        getid = (EditText) findViewById(R.id.editID);
        branchid=(EditText) findViewById(R.id.branchId);
        fromdate = (EditText) findViewById(R.id.fromDate);
        todate = (EditText) findViewById(R.id.toDate);
        fromDate = (TextView) findViewById(R.id.from);
        toDate = (TextView) findViewById(R.id.to);
        submit = (Button) findViewById(R.id.sub);
        list = (ListView) findViewById(R.id.list);
        ledgers=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("debit");
        databaseReferenceCredit= FirebaseDatabase.getInstance().getReference("credit");
        databaseReference.orderByChild("date").startAt(R.id.fromDate).endAt(R.id.toDate);
        databaseReferenceCredit.orderByChild("date").startAt(R.id.fromDate).endAt(R.id.toDate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();

                        String sNo=Integer.toString(ledgers.size()+1);
                        String id=String.valueOf(newPost.get("Id"));
                        String branchId=String.valueOf(newPost.get("BranchId"));
                        String date=String.valueOf(newPost.get("Date"));
                        String debit=String.valueOf(newPost.get("Debit"));
                        String credit=String.valueOf(newPost.get("Credit"));
                        String balance=String.valueOf(newPost.get("Balance"));

                        Ledger ledger=new Ledger();

                        ledger.setsNo(sNo);
                        ledger.setId(id);
                        ledger.setBranchId(branchId);
                        ledger.setBalance(balance);
                        ledger.setCredit(credit);
                        ledger.setDate(date);
                        ledger.setDebit(debit);


                        if(ledger.getId().equals(getid.getText().toString()))
                        {
                            if(ledger.getBranchId().equals(branchid.getText().toString())) {
                                ledgers.add(ledger);
                            }

                        }
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
        });
    }

    public void setList()
    {
        LedgerArrayAdapter ledgerArrayAdapter=new LedgerArrayAdapter(this,ledgers);
        list.setAdapter(ledgerArrayAdapter);
    }
}