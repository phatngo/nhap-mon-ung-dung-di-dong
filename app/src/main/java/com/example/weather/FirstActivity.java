package com.example.weather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FirstActivity extends AppCompatActivity {

    String City;


    String[] URL ={"https://dataimage.000webhostapp.com/image/after_noon.png",
            "https://dataimage.000webhostapp.com/image/clouds.png",
            "https://dataimage.000webhostapp.com/image/night.png",
            "https://dataimage.000webhostapp.com/image/pieace.jpeg",
            "https://dataimage.000webhostapp.com/image/rain.png",
            "https://dataimage.000webhostapp.com/image/storm.png",
            "https://dataimage.000webhostapp.com/image/cloud.png",
            "https://dataimage.000webhostapp.com/image/city.jpg",
            "https://dataimage.000webhostapp.com/image/snow.jpg"};

    ImageView background_Activity1;

    TextView view_city;
    TextView view_temp;
    TextView view_mains;

    ImageView view_weather;
    ImageView icon;
    AutoCompleteTextView view_search;
    Button btn_search;
    Button btn_xemthem;
    ImageView background_pic1;
    AlertDialog.Builder alertDialog;


    String main,temp,day,tempmin,tempmax,sunset,sunrise,pressure,humidity,wind,fells_like,lat, lon, dt;


    private String[] localtion={"An Giang"," Vũng Tàu","  Bạc Liêu","Bắc Kạn"," Bắc Giang","Bắc Ninh","Bến Tre","Bình Dương","Bình Định",
            "Bình Phước","Bình Thuận","Cà Mau","Cao Bằng","Cần Thơ","Đà Nẵng","Đắk Lắk","Đắk Nông","Đồng Nai","Đồng Tháp",
            "Điện Biên","Gia Lai","Hà Nam","Hà Nội","Hà Tĩnh","Hải Dương","Hải Phòng","Hòa Bình","Hậu Giang","Hứng Yên","Saigon",
            "Khánh Hòa","Kiên Giang,Bien Hoa"};



    private void Anhxa()
    {
        view_city=findViewById(R.id.txt_city);
        view_temp=findViewById(R.id.txt_temp);
        view_mains=findViewById(R.id.txt_desc);
        view_search=findViewById(R.id.view_search);
        icon=findViewById(R.id.imageView);

        btn_search=findViewById(R.id.btn_search);
        btn_xemthem=findViewById(R.id.btn_xemthem);

        background_Activity1=findViewById(R.id.ac1);
        background_pic1=findViewById(R.id.imageView);
        btn_xemthem.setVisibility(View.INVISIBLE);
        alertDialog=new AlertDialog.Builder(FirstActivity.this).setTitle("Notification").setMessage("Your city not found!");
    }



    //visible btn_xemthem
    public TextWatcher textWatcher=new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String city_name=view_search.getText().toString().trim();
            if(!city_name.isEmpty())
            {
                btn_xemthem.setEnabled(true);
                Log.d("true or false", String.valueOf(!city_name.isEmpty()));
            }
            else
            {
                btn_xemthem.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Anhxa();


        try {
            Picasso.get().load("https://img.freepik.com/free-photo/room-with-concrete-floor-smoke-background_9083-2991.jpg?size=626&ext=jpg").into(background_Activity1);
            Picasso.get().load(URL[7]).placeholder(R.drawable.town).error(R.drawable.town).into(icon);

            Picasso.get().load("https://images.unsplash.com/photo-1579546929518-9e396f3cc809?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMDk0fQ&w=1000&q=80").into(background_pic1);
            Picasso.get().load(URL[7]).placeholder(R.drawable.town).error(R.drawable.town).into(icon);
        }
        catch (Exception e)
        {

        }

        btn_xemthem.setEnabled(false);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,localtion);
        view_search.setAdapter(arrayAdapter);
        view_search.setThreshold(1);
        view_search.addTextChangedListener(textWatcher);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city=view_search.getText().toString();//Input
                if(city.equals(""))
                {
                    btn_xemthem.setEnabled(false);
                    City="Hanoi";
                    getDataFromURLTask getDataFromURLTask=new getDataFromURLTask();
                    getDataFromURLTask.execute(City);

                }
                else
                {
                    City=city;
                    getDataFromURLTask getDataFromURLTask=new getDataFromURLTask();
                    getDataFromURLTask.execute(City);
                }
            }
        });
        btn_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=view_search.getText().toString();
                if(city.equals(""))
                {
                    City="Hanoi";
                    Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                    intent.putExtra("name", city);
                    intent.putExtra("des",main);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lon", lon);
                    intent.putExtra("temp",temp);
                    intent.putExtra("day",day);
                    intent.putExtra("tempmin",tempmin);
                    intent.putExtra("tempmax",tempmax);
                    intent.putExtra("sunset",sunset);
                    intent.putExtra("sunrise",sunrise);
                    intent.putExtra("pressure",pressure);
                    intent.putExtra("humidity",humidity);
                    intent.putExtra("wind",wind);
                    intent.putExtra("fells_like",fells_like);
                    intent.putExtra("dt", dt);
                    startActivity(intent);
                }
                else
                {
                    City=city;
                    Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                    intent.putExtra("name", city);
                    intent.putExtra("temp",temp);
                    intent.putExtra("day",day);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lon", lon);
                    intent.putExtra("tempmin",tempmin);
                    intent.putExtra("tempmax",tempmax);
                    intent.putExtra("sunset",sunset);
                    intent.putExtra("sunrise",sunrise);
                    intent.putExtra("pressure",pressure);
                    intent.putExtra("humidity",humidity);
                    intent.putExtra("wind",wind);
                    intent.putExtra("fells_like",fells_like);
                    intent.putExtra("dt", dt);
                    startActivity(intent);
                }

            }
        });
    }
    class getDataFromURLTask extends AsyncTask<String, Void, Weather>{

        @Override
        protected Weather doInBackground(String... strings) {
            try{
                URL url=new URL("https://api.openweathermap.org/data/2.5/weather?q="+strings[0]+"&appid=a839789a1e16df061980a3c9966950da");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                connection.setRequestProperty("Accept", "*/*");
                InputStream is= connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is,"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                String line=br.readLine();
                StringBuilder builder=new StringBuilder();
                while (line!=null){
                    builder.append(line);
                    line=br.readLine();}
                String json=builder.toString();
                JSONObject jsonObject=new JSONObject(json);

                Log.d("0", String.valueOf(jsonObject));


                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                String status = weather.getString("description");
                Log.d("2", status);

                JSONObject main = jsonObject.getJSONObject("main");
                double temp = main.getDouble("temp");
                String tempmin = String.valueOf(main.getDouble("temp_min")-273.15);
                String tempmax = String.valueOf(main.getDouble("temp_max")-273.15);
                String pressure = String.valueOf(main.getInt("pressure"));
                String humidity = String.valueOf(main.getInt("humidity"));
                String fells_like = String.valueOf(main.getDouble("feels_like"));

                JSONObject sys = jsonObject.getJSONObject("sys");
                String sunset = String.valueOf(sys.getLong("sunset")-273.15);
                String sunrise = String.valueOf(sys.getLong("sunrise")-273.15);
                Log.d("3", String.valueOf(temp));

                JSONObject windObject = jsonObject.getJSONObject("wind");
                String wind = String.valueOf(windObject.getDouble("speed"));

                JSONObject coord = jsonObject.getJSONObject("coord");

                String city = jsonObject.getString("name");
                Log.d("4", String.valueOf(city));

                double lonn=coord.getDouble("lon");
                double latt=coord.getDouble("lat");
                long dtt=jsonObject.getLong("dt");



                return new Weather(status,temp,city, latt, lonn, dtt, tempmin, tempmax, sunset, sunrise, pressure, humidity, wind, fells_like);
            }catch (Exception ex) {

                Log.e("Retrieve Data Failed",ex.toString() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if(weather==null){
                alertDialog.show();
            }else{
            super.onPostExecute(weather);
                btn_xemthem.setVisibility(View.VISIBLE);
            view_city.setText(weather.getCityName());
            City=weather.getCityName();
            double tempe= weather.getTemp()-273.15;
            tempe=Math.round(tempe);
            view_temp.setText(String.valueOf(tempe + "°C"));

            temp=String.valueOf(tempe);

            view_mains.setText(weather.getStatus());
            main=weather.getStatus();

            lat=String.valueOf(weather.getLat());
            lon=String.valueOf(weather.getLon());

            Long l= Long.valueOf(weather.getDt());
            Date date=new Date(l*1000L);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
            day= String.valueOf(weather.getDt());

            dt=String.valueOf(weather.getDt());

            tempmin=weather.getTempmin();
            tempmax=weather.getTempmax();
            sunset=weather.getSunset();
            sunrise=weather.getSunrise();
            pressure=weather.getPressure();
            humidity=weather.getHumidity();
            wind=weather.getWind();
            fells_like=weather.getFells_like();
        }
        }
    }

}

