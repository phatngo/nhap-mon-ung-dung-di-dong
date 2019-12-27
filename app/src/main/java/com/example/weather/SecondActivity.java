package com.example.weather;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    ImageButton btn_back;

    TextView txtview_name;
    TextView txtmin;
    TextView txtmax;
    TextView txtview_status;
    TextView txtview_temp;
    TextView txtview_day;
    TextView txtview_status_day;
    TextView txtview_tempmax;
    TextView txtview_tempmin;
    TextView txtview_descrip;
    TextView txtview_sunrise;
    TextView txtview_sunset;
    TextView txtview_pressure;
    TextView txtview_humidity;
    TextView txtview_wind;
    TextView fells_like;

    ArrayList<Weather> weatherArrayList;
    ListView listView;
    CustomAdapter customAdapter;

    private void Anhxa(){

        listView=(ListView)findViewById(R.id.listview);
        weatherArrayList=new ArrayList<Weather>();
        customAdapter=new CustomAdapter(SecondActivity.this,weatherArrayList);
        listView.setAdapter(customAdapter);

        txtview_name=findViewById(R.id.textview_name);//xong
        txtmin=findViewById(R.id.textview_mintemp);
        txtmax=findViewById(R.id.textview_maxtemp);

        txtview_tempmin=findViewById(R.id.txtview_mintemp);
        txtview_tempmax=findViewById(R.id.txtview_maxtemp);
        txtview_status=findViewById(R.id.textview_status);
        txtview_temp=findViewById(R.id.textview_temp);
        txtview_day=findViewById(R.id.textview_day);
        txtview_status_day=findViewById(R.id.textview_status_day);
        txtview_descrip=findViewById(R.id.txtview_description);
        txtview_sunrise=findViewById(R.id.textview_sunrise);
        txtview_sunset=findViewById(R.id.textview_sunset);
        txtview_pressure=findViewById(R.id.textview_pressure);
        txtview_humidity=findViewById(R.id.textview_humidity);
        txtview_wind=findViewById(R.id.textview_wind);
        fells_like=findViewById(R.id.textview_fell_temp);

        btn_back=findViewById(R.id.btn_back);

    }

    private void Get5days(String city){

        String URL="http://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&cnt=40&appid=6eb05489697e40534a222fae7558853f";//5d
        RequestQueue requestQueue= Volley.newRequestQueue(SecondActivity.this);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Kết quả JSON",response);
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObjectCity=jsonObject.getJSONObject("city");
                    String city=jsonObjectCity.getString("name");
                    txtview_name.setText(city);
                    Log.d("city",city);

                    JSONArray jsonArrayList=jsonObject.getJSONArray("list");
                    for(int i=0;i<jsonArrayList.length();i++)
                    {
                        JSONObject jsonObjectList=jsonArrayList.getJSONObject(i);//duyệt tới cuối mảng

                        //ngày
                        String day=jsonObjectList.getString("dt");//i=1
                        //ép kiểu
                        Long l= Long.valueOf(day);
                        Date date=new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
                        String Day= simpleDateFormat.format(date);//ngày
                        Log.d("Ngày",Day);
                        //nhiệt độ,
                        JSONObject jsonObjectMain=jsonObjectList.getJSONObject("main");
                        // nhiệt độ max
                        String max=jsonObjectMain.getString("temp_max");
                        Double a=Double.valueOf(max);
                        String maxtemp=String.valueOf(a.intValue());//maxtemp
                        //nhiệt độ min
                        String min=jsonObjectMain.getString("temp_min");
                        Double b=Double.valueOf(min);
                        String mintemp=String.valueOf(b.intValue());//mintemp

                        JSONArray jsonArrayWeather=jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                        String description=jsonObjectWeather.getString("description");
                        String icon=jsonObjectWeather.getString("icon");

                        Log.d("max",maxtemp);
                        Log.d("min",mintemp);
                        Log.d("description",description);
                        Log.d("icon",icon);


                        weatherArrayList.add(new Weather(Day,description,icon,mintemp,maxtemp));
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Anhxa();
        Intent intent=getIntent();

        String des=intent.getStringExtra("des");
        txtview_status.setText(des);
        Log.d("des:",des);
        String temp=intent.getStringExtra("temp");
        txtview_temp.setText(temp+"°C");
        Log.d("temp:",temp);
        String d=intent.getStringExtra("day");
        txtview_day.setText(d);
        Log.d("day:",d);
        String days=intent.getStringExtra("days");
        txtview_status_day.setText(days);
        Log.d("days:",days);
        String tempmin=intent.getStringExtra("tempmin");
        txtmin.setText(tempmin+"°C");
        Log.d("tempmin:",tempmin);
        String tempmax=intent.getStringExtra("tempmax");
        txtmax.setText(tempmax+"°C");
        String sunset=intent.getStringExtra("sunset");
        txtview_sunset.setText(sunset);
        String sunsire= intent.getStringExtra("sunrise");
        txtview_sunrise.setText(sunsire);
        String pressure= intent.getStringExtra("pressure");
        txtview_pressure.setText(pressure+"hPA");
        String humidity=intent.getStringExtra("humidity");
        txtview_humidity.setText(humidity+"%");
        String wind=intent.getStringExtra("wind");
        txtview_wind.setText(wind+"Km/h");
        String fell=intent.getStringExtra("fells_like");
        fells_like.setText(fell+"°C");

        txtview_descrip.setText("Today: The weather have maximun temp "+tempmax+"°C minimun temp "+tempmin+"°C");

        String city= intent.getStringExtra("name");
        Log.d("City",city);
        Get5days(city);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




}
