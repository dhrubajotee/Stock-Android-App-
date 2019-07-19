package com.example.dhrubajotee.stock.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dhrubajotee.stock.R;
import com.example.dhrubajotee.stock.classes.Balance;
import com.example.dhrubajotee.stock.classes.Ledger;

import java.util.ArrayList;

/**
 * Created by Dhruba Jotee on 01-Dec-17.
 */

public class LedgerArrayAdapter extends ArrayAdapter<Ledger> {

    public LedgerArrayAdapter(Context context, ArrayList<Ledger> ledgers)
    {
        super(context, 0, ledgers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ledger ledger = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ledger_list_items, parent, false);
        }

        final TextView sNo= (TextView) convertView.findViewById(R.id.sl);
        final TextView  id= (TextView) convertView.findViewById(R.id.id);
        final TextView  branchId= (TextView) convertView.findViewById(R.id.branchId);
        final TextView date= (TextView) convertView.findViewById(R.id.date);
        final TextView  debit= (TextView) convertView.findViewById(R.id.debit);
        final TextView credit= (TextView) convertView.findViewById(R.id.credit);
        final TextView  balance= (TextView) convertView.findViewById(R.id.balance);

        sNo.setText(ledger.getsNo());
        id.setText(ledger.getId());
        branchId.setText(ledger.getBranchId());
        date.setText(ledger.getDate());
        debit.setText(ledger.getDebit());
        credit.setText(ledger.getCredit());
        balance.setText(ledger.getBalance());


        return convertView;
    }
}