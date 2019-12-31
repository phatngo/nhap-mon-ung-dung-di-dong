package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FirstActivity extends AppCompatActivity {

    String City;


    TextView view_city;
    TextView view_temp;
    TextView view_mains;

    ImageView view_weather;
    AutoCompleteTextView view_search;
    Button btn_search;
    Button btn_xemthem;

    String main,temp,day,days,tempmin,tempmax,sunset,sunrise,pressure,humidity,wind,fells_like,city_name;

    private String[] localtion={"An Giang"," Vũng Tàu","  Bạc Liêu","Bắc Kạn"," Bắc Giang","Bắc Ninh","Bến Tre","Bình Dương","Bình Định",
            "Bình Phước","Bình Thuận","Cà Mau","Cao Bằng","Cần Thơ","Đà Nẵng","Đắk Lắk","Đắk Nông","Đồng Nai","Đồng Tháp",
            "Điện Biên","Gia Lai","Hà Nam","Hà Nội","Hà Tĩnh","Hải Dương","Hải Phòng","Hòa Bình","Hậu Giang","Hứng Yên","Saigon",
            "Khánh Hòa","Kiên Giang"};

    private void Anhxa()
    {
        view_city=findViewById(R.id.txt_city);
        view_temp=findViewById(R.id.txt_temp);
        view_mains=findViewById(R.id.txt_desc);
        view_search=findViewById(R.id.view_search);


        view_weather=findViewById(R.id.img_weather);
        btn_search=findViewById(R.id.btn_search);
        btn_xemthem=findViewById(R.id.btn_xemthem);

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
                    GetCurrentData(City);

                }
                else
                {
                    City=city;
                    GetCurrentData(City);
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
                    intent.putExtra("des",main);
                    intent.putExtra("temp",temp);
                    intent.putExtra("day",day);
                    intent.putExtra("days",days);
                    intent.putExtra("tempmin",tempmin);
                    intent.putExtra("tempmax",tempmax);
                    intent.putExtra("sunset",sunset);
                    intent.putExtra("sunrise",sunrise);
                    intent.putExtra("pressure",pressure);
                    intent.putExtra("humidity",humidity);
                    intent.putExtra("wind",wind);
                    intent.putExtra("fells_like",fells_like);

                    intent.putExtra("name",City);
                    startActivity(intent);
                }
                else
                {
                    City=city;
                    Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                    intent.putExtra("des",main);
                    intent.putExtra("temp",temp);
                    intent.putExtra("day",day);
                    intent.putExtra("days",days);
                    intent.putExtra("tempmin",tempmin);
                    intent.putExtra("tempmax",tempmax);
                    intent.putExtra("sunset",sunset);
                    intent.putExtra("sunrise",sunrise);
                    intent.putExtra("pressure",pressure);
                    intent.putExtra("humidity",humidity);
                    intent.putExtra("wind",wind);
                    intent.putExtra("fells_like",fells_like);

                    intent.putExtra("name",City);
                    startActivity(intent);
                }

            }
        });
    }
    public void GetCurrentData(String city)

    {
        //requestQueue thực thi những request gửi đi
        RequestQueue requestQueue= Volley.newRequestQueue(FirstActivity.this);
        //đọc dữ liệu đường dẫn
        String URL="https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=6eb05489697e40534a222fae7558853f";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Kết quả JSON",response);
                    JSONObject jsonObject=new JSONObject(response);
                    //tên thành phố
                    String city=jsonObject.getString("name");
                    view_city.setText(city);

                    //khối weather
                    JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                    String icon=jsonObjectWeather.getString("icon");
                    String mains=jsonObjectWeather.getString("main");//thông tin thời tiết
                    Picasso.get().load("http://openweathermap.org/img/wn/"+icon+".png").into(view_weather);
                    view_mains.setText(mains);

                    //khối main
                    JSONObject jsonObjectMain=jsonObject.getJSONObject("main");
                    String as=jsonObjectMain.getString("temp");
                    Double a=Double.valueOf(as);
                    String temper=String.valueOf(a.intValue());
                    view_temp.setText(temper+"°C");

                    String min = jsonObjectMain.getString("temp_min");
                    Double mina = Double.valueOf(min);
                    String min_ = String.valueOf(mina.intValue());

                    Log.d("Nhiệt độ nhỏ nhất ",min);
                    String max = jsonObjectMain.getString("temp_max");
                    Double maxa = Double.valueOf(max);
                    String max_ = String.valueOf(maxa.intValue());

                    String humi = jsonObjectMain.getString("humidity");
                    Double humia = Double.valueOf(humi);
                    String humidi = String.valueOf(humia.intValue());

                    String pres = jsonObjectMain.getString("pressure");
                    Double presa = Double.valueOf(pres);
                    String pressu = String.valueOf(presa.intValue());

                    String fells_ = jsonObjectMain.getString("feels_like");
                    Double aa = Double.valueOf(fells_);
                    String fells_temp = String.valueOf(aa.intValue());

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String speed = jsonObjectWind.getString("speed");



                    //ngày
                    String day1=jsonObject.getString("dt");//
                    //ép kiểu
                    Long l= Long.valueOf(day1);
                    Date date=new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
                    String Day= simpleDateFormat.format(date);//ngày

                    //MT mọc, lặn

                    JSONObject jsonObjectSys=jsonObject.getJSONObject("sys");
                    String sunrise1=jsonObjectSys.getString("sunrise");
                    Long rise=Long.valueOf(sunrise1);
                    Date dateRise=new Date(rise*1000L);
                    SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("hh:mm");
                    String SunRise=simpleDateFormat1.format(dateRise);


                    String sunset1=jsonObjectSys.getString("sunset");
                    Long set=Long.valueOf(sunset1);
                    Date dateSet=new Date(set*1000L);
                    String SunSet=simpleDateFormat1.format(dateSet);

                    city_name=city;
                    fells_like=fells_temp;
                    pressure=pressu;
                    wind=speed;
                    humidity=humidi;
                    sunset=SunSet;
                    sunrise=SunRise;
                    tempmin=min_;
                    tempmax=max_;
                    day=Day;
                    days="Today";
                    main=mains;
                    temp=temper;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String text="NOT FOND YOUR CITY, PLEASE TRY ANOTHER CITY";
                view_search.setText("");
                Toast.makeText(FirstActivity.this, text,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
