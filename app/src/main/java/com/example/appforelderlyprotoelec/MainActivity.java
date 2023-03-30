package com.example.appforelderlyprotoelec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    String[] weather={"cloudy","rain","sunny"};
    String curWeather,curLocation,curTemp;
    Button cloudyButton, sunnyButton,rainyButton;
    ImageButton but1, but2, but3, but4,but5,but6,but7,but8,daybut1,daybut2, daybut3, daybut4,daybut5,daybut6,daybut7,daybut8;
    ImageView currentWeather;
    TextView location, temperature, hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8,day1,day2,day3,day4,day5;
    HorizontalScrollView hs;
    ScrollView sv;

    TextView[] textViewArray,dayTextArray;
    ImageButton[] imageButtonArrayForecast,imageButtonArrayDaysForecast;
    private final String url ="https://api.openweathermap.org/data/2.5/weather";
    private final String urlForecast ="https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=44.34&lon=10.99&appid=b5649972911c2d2ed660bcbb9fe073de";
    private final String appid = "b5649972911c2d2ed660bcbb9fe073de";
    DecimalFormat df = new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Inflate the layout
        ConstraintLayout cl= findViewById(R.id.home);
        RelativeLayout rl= (RelativeLayout)cl.findViewById(R.id.relativeLayout);
        RelativeLayout rl2= (RelativeLayout)cl.findViewById(R.id.relativeLayout2);

        sv= (ScrollView)rl2.findViewById(R.id.scrollView);
        hs= (HorizontalScrollView) rl.findViewById(R.id.horizontalScrollView);
        cloudyButton=findViewById(R.id.cloudyButton);
        sunnyButton=findViewById(R.id.sunnyButton);
        rainyButton=findViewById(R.id.rainyButton);
        but1=findViewById(R.id.button);
        but2=findViewById(R.id.button1);
        but3=findViewById(R.id.button2);
        but4=findViewById(R.id.button3);
        but5=findViewById(R.id.button4);
        but6= findViewById(R.id.button5);
        but7=findViewById(R.id.button6);
        but8=findViewById(R.id.button7);
        daybut1=findViewById(R.id.dayButton);
        daybut2=findViewById(R.id.dayButton1);
        daybut3=findViewById(R.id.dayButton2);
        daybut4=findViewById(R.id.dayButton3);
        daybut5=findViewById(R.id.dayButton4);

        currentWeather=(ImageView) findViewById(R.id.currentWeatherView);
        location=findViewById(R.id.location);
        temperature=findViewById(R.id.currentTemp);
        curLocation= getMyCurrentLocation();
        location.setText(curLocation);
        getWeatherDetails();
        getHourlyForecast();
        get5DayForecast();
        hour1=(TextView) hs.findViewById(R.id.hour1);
        hour2=(TextView) hs.findViewById(R.id.hour2);
        hour3=(TextView) hs.findViewById(R.id.hour3);
        hour4=(TextView) hs.findViewById(R.id.hour4);
        hour5=(TextView) hs.findViewById(R.id.hour5);
        hour6=(TextView) hs.findViewById(R.id.hour6);
        hour7=(TextView) hs.findViewById(R.id.hour7);
        hour8=(TextView) hs.findViewById(R.id.hour8);

        day1=(TextView) sv.findViewById(R.id.day1text);
        day2=(TextView) sv.findViewById(R.id.day2text);
        day3=(TextView) sv.findViewById(R.id.day3text);
        day4=(TextView) sv.findViewById(R.id.day4text);
        day5=(TextView) sv.findViewById(R.id.day5text);



        dayTextArray= new TextView[]{day1,day2,day3,day4,day5};
        textViewArray= new TextView[]{hour1, hour2, hour3, hour4, hour5, hour6, hour7, hour8};
        imageButtonArrayForecast=new ImageButton[]{but1,but2,but3,but4,but5,but6,but7,but8};
        imageButtonArrayDaysForecast= new ImageButton[]{daybut1,daybut2,daybut3,daybut4,daybut5};
        setClickListener5DayForecast(imageButtonArrayDaysForecast);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime lt = LocalDateTime.now();
            ZonedDateTime zt= ZonedDateTime.now();
            System.out.println("LOCAL"+zt);
            hour1.setText("Now");
            makeDays(lt,dayTextArray);
            makeHours(lt,textViewArray);
        }
        //LocalDateTime lt = LocalDateTime.now();

        //replaceFragment(new cloudyFragment());
        rainyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                replaceFragment(new RainyFragment());
            }
        });
        cloudyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                replaceFragment(new cloudyFragment());
            }

        });
        sunnyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                replaceFragment(new sunnyFragment());
            }
        });

    }

    private void setClickListener5DayForecast(ImageButton[] imageButtonArrayDaysForecast) {
        for(int i =0;i<imageButtonArrayDaysForecast.length;i++){
            this.imageButtonArrayDaysForecast[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ImageButton) view).setBackground(getDrawable(R.drawable.cloudy));
                }
            });
        }
    }

    private void makeDays(LocalDateTime lt, TextView[] dayTextArray) {
        for(int i = 0; i< this.dayTextArray.length; i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                this.dayTextArray[i].setText(formatDay(lt.plusDays(i)));
            }
        }

    }

    private String  formatDay(LocalDateTime plusDays) {
        String strDate="";
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("E");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String formatDateTime = plusDays.format(formatter);
            strDate=formatDateTime;
        }

        return strDate;
    }


    private String getMyCurrentLocation() {
        curLocation="Galway,Ireland";
        return curLocation;
    }


    private void setCurrentWeatherImage(ImageView currentWeather,String iconCode) throws IOException {
        String url = "http://openweathermap.org/img/w/" + iconCode + ".png";
        DownloadImageTask task = new DownloadImageTask(currentWeather);
        task.execute(url);


    }

    private void setForecastWeatherImage(ImageButton currentWeather,String iconCode) throws IOException {
        String url = "http://openweathermap.org/img/w/" + iconCode + ".png";
        DownloadImageTask task = new DownloadImageTask(currentWeather);
        task.execute(url);


    }

    private void set5DayForecastWeatherImage(ImageButton currentWeather,String iconCode) throws IOException {
        String url = "http://openweathermap.org/img/w/" + iconCode + ".png";
        DownloadImageTask task = new DownloadImageTask(currentWeather);
        task.execute(url);


    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    public void makeHours(LocalDateTime lt, TextView[] textViewArray){
        for(int i = 1; i< this.textViewArray.length; i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.textViewArray[i].setText(formatDate(lt.plusHours(i)));
                System.out.println("HOURS" +formatDate(lt.plusHours(i)));
            }
        }
    }

    public String formatDate(LocalDateTime date){
        String strDate="";
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String formatDateTime = date.format(formatter);
            strDate=formatDateTime+":00";
        }
        return strDate;
    }

    private void get5DayForecast(){
        String tempurl = "";
        String cityCountry = getMyCurrentLocation();
        String[] cityCountryArray= cityCountry.split(",");
        String city =cityCountryArray[0];
        String country = cityCountryArray[1];
        tempurl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&units=metric&cnt=5&appid=" + appid; // update the URL to retrieve 5-day forecast
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("response",response);
                String output="";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("list");

                    // Get the forecast for the next 5 days
                    for (int i = 0; i < jsonArray.length(); i++) { // update the loop to retrieve all the forecast data for 5 days
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp");
                        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");
                        set5DayForecastWeatherImage(imageButtonArrayDaysForecast[i],icon);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void getHourlyForecast(){

        String tempurl = "";
        String cityCountry = getMyCurrentLocation();
        String[] cityCountryArray= cityCountry.split(",");
        String city =cityCountryArray[0];
        String country = cityCountryArray[1];
        tempurl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&units=metric&appid=" + appid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("response",response);
                String output="";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("list");

                    // Get the hourly forecast for the next 4 hours
                    for (int i = 0; i < imageButtonArrayForecast.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp");
                        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");

                        setForecastWeatherImage(imageButtonArrayForecast[i],icon);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getWeatherDetails(){
        String tempurl = "";
        String cityCountry = getMyCurrentLocation();
        String[] cityCountryArray= cityCountry.split(",");
        String city =cityCountryArray[0];
        String country = cityCountryArray[1];
        tempurl = url+"?q="+city+ ","+country+"&appid="+appid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("response",response);
                String output="";
                try {
                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray jsonArray= jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather=jsonArray.getJSONObject(0);
                    String description=jsonObjectWeather.getString("description");
                    String condition = jsonObjectWeather.getString("main");
                    String icon = jsonObjectWeather.getString("icon");
                    JSONObject jsonObjectMain= jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp")-273.15;
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    //String countryName = jsonObjectSys.getString("country");
                    String cityName=  jsonResponse.getString("name");
                    output =df.format(temp) +"°C";
                    temperature.setText(output);
                    setCurrentWeatherImage(currentWeather,icon);

                     if(condition.equalsIgnoreCase("clouds")||condition.equalsIgnoreCase("fog")){
                         replaceFragment(new cloudyFragment());
                     }

                     else if(condition.equalsIgnoreCase("rain")||condition.equalsIgnoreCase("snow")||condition.equalsIgnoreCase("drizzle")||condition.equalsIgnoreCase("thunderstorm")||curWeather.equalsIgnoreCase("mist")){
                         replaceFragment(new RainyFragment());
                     }

                     else if(condition.equalsIgnoreCase("clear")){
                         replaceFragment(new sunnyFragment());

                     }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private View view;

        public DownloadImageTask(View view) {
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(urls[0])
                    .build();
            okhttp3.Response response;
            try {
                response = client.newCall(request).execute();
                InputStream inputStream = response.body().byteStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(result);
            } else if (view instanceof ImageButton) {
                ((ImageButton) view).setImageBitmap(result);
            }
        }
    }



}

