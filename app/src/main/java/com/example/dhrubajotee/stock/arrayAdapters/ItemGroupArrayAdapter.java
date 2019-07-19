package com.example.dhrubajotee.stock.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dhrubajotee.stock.R;
import com.example.dhrubajotee.stock.classes.Item;

import java.util.ArrayList;

/**
 * Created by Dhruba Jotee on 10-Nov-17.
 */

public class ItemGroupArrayAdapter extends ArrayAdapter<Item> {

    public ItemGroupArrayAdapter(Context context, ArrayList<Item> items)
    {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_1, parent, false);
        }

        final TextView id= (TextView) convertView.findViewById(R.id.text1);
        final TextView  itemName= (TextView) convertView.findViewById(R.id.text2);

        id.setText(Integer.toString(item.getId()));
        itemName.setText(item.getItemName());

        return convertView;
    }
}