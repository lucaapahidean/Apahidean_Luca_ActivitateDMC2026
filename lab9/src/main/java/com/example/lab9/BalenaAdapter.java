package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BalenaAdapter extends ArrayAdapter<BalenaItem> {

    public BalenaAdapter(Context context, List<BalenaItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_balena, parent, false);
        }

        BalenaItem item = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageViewBalena);
        TextView  textView  = convertView.findViewById(R.id.textViewDescriere);

        textView.setText(item.getDescriere());

        if (item.getBitmap() != null) {
            imageView.setImageBitmap(item.getBitmap());
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        return convertView;
    }
}