package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BalenaAdapter extends BaseAdapter {

    private Context ctx;
    private List<Balena> balene;

    public BalenaAdapter(Context ctx, List<Balena> balene) {
        this.ctx = ctx;
        this.balene = balene;
    }

    @Override
    public int getCount() {
        return this.balene.size();
    }

    @Override
    public Object getItem(int position) {
        return this.balene.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.item_balena, parent, false);

        TextView textViewNume = v.findViewById(R.id.textViewNume);
        TextView textViewDetalii = v.findViewById(R.id.textViewDetalii);

        Balena b = (Balena) getItem(position);
        textViewNume.setText(b.getNume());
        textViewDetalii.setText(b.getSpecie() + " | " + b.getGreutate() + " tone | " + b.getVarsta() + " ani");

        return v;
    }
}
