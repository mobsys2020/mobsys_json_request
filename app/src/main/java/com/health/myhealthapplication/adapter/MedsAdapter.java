package com.health.myhealthapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.health.myhealthapplication.R;

import java.util.ArrayList;
import java.util.List;
import com.health.myhealthapplication.models.Meds;

public class MedsAdapter extends ArrayAdapter<Meds> {
    private Context mContext;
    private List<Meds> medlist = new ArrayList<Meds>();
    private int textsize = 30;

    public MedsAdapter(@NonNull Context context, @NonNull List<Meds> objects, int _textsize) {
        super(context, 0,objects);
        mContext = context;
        medlist = objects;
        textsize = _textsize;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.med,parent,false);

        Meds med = medlist.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name);
        TextView quantity = (TextView) listItem.findViewById(R.id.quantity);
        TextView time = (TextView) listItem.findViewById(R.id.time);
        TextView days = (TextView) listItem.findViewById(R.id.days);

        name.setText(med.name);
        name.setTextSize(textsize);
        quantity.setText(med.quantity);
        quantity.setTextSize(textsize);
        time.setText(med.time);
        time.setTextSize(textsize);
        days.setText(med.days);
        days.setTextSize(textsize);
        return listItem;

    }
}
