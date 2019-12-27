package com.example.weather;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;//người dùng truyền vào
    ArrayList<Weather> arrayList;

    public CustomAdapter(Context context,ArrayList<Weather> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView  =inflater.inflate(R.layout.list_days,null);

        Weather weather=arrayList.get(position);

        TextView txt_days= convertView.findViewById(R.id.txt_days);
        TextView txtview_mintemp= convertView.findViewById(R.id.txtview_mintemp);
        TextView txtview_maxtemp= convertView.findViewById(R.id.txtview_maxtemp);
        ImageView img_days=convertView.findViewById(R.id.img_days);

        txt_days.setText(weather.day);
        txtview_mintemp.setText(weather.MinTemp+"°C");
        txtview_maxtemp.setText(weather.MaxTemp+"°C");
        Picasso.get().load("http://openweathermap.org/img/wn/"+weather.IMG+".png").into(img_days);

        Log.d("CustomAdapter getView()",String.valueOf(position));

        return convertView;
    }
}
