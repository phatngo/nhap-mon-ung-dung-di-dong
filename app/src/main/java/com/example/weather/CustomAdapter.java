package com.example.weather;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    Activity context;
    int resource;
    List<Weather> objects;

    public CustomAdapter(@NonNull Activity context, int resource, @NonNull List objects)
    {
        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View row  = inflater.inflate(this.resource,null);

        Weather weather=objects.get(position);

        TextView txt_days= row.findViewById(R.id.txt_days);
        TextView txtview_mintemp = row.findViewById(R.id.txtview_mintemp);
        TextView txtview_maxtemp= row.findViewById(R.id.txtview_maxtemp);

        double mintemp=Double.parseDouble(weather.getTempmin())-273.15;
        double maxtemp=Double.parseDouble(weather.getTempmax())-273.15;
        txt_days.setText(weather.getDay());
        txtview_mintemp.setText(Math.round((mintemp*10)/10)+"°C");
        txtview_maxtemp.setText(Math.round((maxtemp*10)/10)+"°C");

        return row;
    }
}
