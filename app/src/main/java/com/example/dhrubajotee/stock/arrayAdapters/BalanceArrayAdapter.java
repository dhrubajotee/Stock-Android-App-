package com.example.dhrubajotee.stock.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dhrubajotee.stock.R;
import com.example.dhrubajotee.stock.classes.Balance;

import java.util.ArrayList;

/**
 * Created by Dhruba Jotee on 01-Dec-17.
 */

public class BalanceArrayAdapter extends ArrayAdapter<Balance> {

    public BalanceArrayAdapter(Context context, ArrayList<Balance> balances)
    {
        super(context, 0, balances);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Balance balance = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.balance_list_items, parent, false);
        }

        final TextView id= (TextView) convertView.findViewById(R.id.id);
        final TextView item= (TextView) convertView.findViewById(R.id.item);
        final TextView  bal= (TextView) convertView.findViewById(R.id.balance);


        id.setText(balance.getId());

        item.setText(balance.getItemName());
        bal.setText(balance.getBalance());

        return convertView;
    }
}