package com.example.weather;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    String[] URL ={"https://dataimage.000webhostapp.com/image/after_noon.png",
            "https://dataimage.000webhostapp.com/image/clouds.png",
            "https://dataimage.000webhostapp.com/image/night.png",
            "https://dataimage.000webhostapp.com/image/pieace.jpeg",
            "https://dataimage.000webhostapp.com/image/rain.png",
            "https://dataimage.000webhostapp.com/image/storm.png",
            "https://dataimage.000webhostapp.com/image/cloud.png",
            "https://dataimage.000webhostapp.com/image/city.jpg",
            "https://dataimage.000webhostapp.com/image/snow.jpg"};

    ImageButton btn_back;

    LinearLayout background_activity2;

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
    ProgressDialog progressDialog;

    ArrayList<Weather> weatherArrayList;
    ListView listView;
    CustomAdapter customAdapter;

    private void Anhxa(){

        listView=(ListView)findViewById(R.id.listview);
        weatherArrayList=new ArrayList<Weather>();
        customAdapter=new CustomAdapter(SecondActivity.this,R.layout.list_days,weatherArrayList);
        listView.setAdapter(customAdapter);

       background_activity2=findViewById(R.id.background_activity2);
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

        progressDialog=new ProgressDialog(SecondActivity.this);
        progressDialog.setTitle("Notification");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Processing...");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Anhxa();
        Intent intent=getIntent();


        String name=intent.getStringExtra("name");
        txtview_name.setText(name);
        String temp=intent.getStringExtra("temp");
        txtview_temp.setText(temp+"°C");
        Log.d("temp:",temp);
        String d=intent.getStringExtra("day");
        long unixSeconds=Long.parseLong(d);
        Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
// give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        txtview_day.setText(formattedDate);
        Log.d("day:",d);
        String tempmin=intent.getStringExtra("tempmin");
        txtmin.setText(tempmin+"°C");
        Log.d("tempmin:",tempmin);
        String tempmax=intent.getStringExtra("tempmax");
        txtmax.setText(tempmax+"°C");
        String sunset=intent.getStringExtra("sunset");
        double sunsett=Double.parseDouble(sunset);
        sunsett=Math.round((sunsett*100)/100);
        txtview_sunset.setText(sunsett+"");
        String sunrise= intent.getStringExtra("sunrise");
        double sunrises=Double.parseDouble(sunrise);
        sunrises=Math.round((sunrises*100)/100);
        txtview_sunrise.setText(sunrises+"");
        String pressure= intent.getStringExtra("pressure");
        txtview_pressure.setText(pressure+"hPA");
        String humidity=intent.getStringExtra("humidity");
        txtview_humidity.setText(humidity+"%");
        String wind=intent.getStringExtra("wind");
        txtview_wind.setText(wind+"Km/h");
        String fell=intent.getStringExtra("fells_like");
        fells_like.setText(fell+"°C");

        String lat=intent.getStringExtra("lat");
        String lon=intent.getStringExtra("lon");
        String dt = intent.getStringExtra("dt");

        txtview_descrip.setText("Today: The weather have maximun temp "+tempmax+"°C minimun temp "+tempmin+"°C");

        String city = intent.getStringExtra("name");
        Log.d("city", city );
        get5DaysTask Get5DaysTasks=new get5DaysTask();
        Get5DaysTasks.execute(city);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



class get5DaysTask extends AsyncTask<String, Void, ArrayList<Weather>>{

    @Override
    protected ArrayList<Weather> doInBackground(String... strings) {
        ArrayList<Weather>weatherArray=new ArrayList<>();
        try{
            URL url= new URL("https://api.openweathermap.org/data/2.5/forecast?q="+strings[0]+"&appid=a839789a1e16df061980a3c9966950da");
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
            JSONArray list = jsonObject.getJSONArray("list");

            for(int i=0;i<list.length();i++){
                JSONObject object=list.getJSONObject(i);
                //ngày
                //ép kiểu

                Long l= object.getLong("dt");
                Date date=new Date(l*1000L);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
                String Day= simpleDateFormat.format(date);//ngày
                Log.d("Ngày",Day);

                //nhiệt độ,
                JSONObject jsonObjectMain=object.getJSONObject("main");
                // nhiệt độ max
                String max=jsonObjectMain.getString("temp_max");
                Double a=Double.valueOf(max);
                String maxtemp=String.valueOf(a.intValue());//maxtemp
                //nhiệt độ min
                String min=jsonObjectMain.getString("temp_min");
                Double b=Double.valueOf(min);
                String mintemp=String.valueOf(b.intValue());//mintemp

                JSONArray jsonArrayWeather=object.getJSONArray("weather");
                JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                String description=jsonObjectWeather.getString("description");
                String icon=jsonObjectWeather.getString("icon");
                int flag=0;
                for(int j=0;j<weatherArray.size();j++){
                    if(weatherArray.get(j).getDay().equals(Day)){
                        flag=1;
                    }
                }
                if(flag==0){
                weatherArray.add(new Weather(Day,mintemp,maxtemp,description,icon));
                }
                flag=0;
            }
            return weatherArray;
        }catch (Exception e){
            Log.e("Retrieve Data Failed",e.toString() );
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        if(weathers.size()==0){
            Toast.makeText(SecondActivity.this,"No Data Found!",Toast.LENGTH_SHORT).show();
        }else{
        super.onPostExecute(weathers);
        customAdapter.clear();
        customAdapter.addAll(weathers);
        progressDialog.dismiss();}
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        customAdapter.clear();
        progressDialog.show();
    }
}
}
