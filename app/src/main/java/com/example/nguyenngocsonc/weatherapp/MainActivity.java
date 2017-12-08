package com.example.nguyenngocsonc.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewTemp;
    TextView mTextViewLocation;
    TextView mTextViewDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewTemp = (TextView) findViewById(R.id.temp);
        mTextViewLocation = (TextView) findViewById(R.id.location);
        mTextViewDes = (TextView) findViewById(R.id.des);

        String url = "http://openweathermap.org/data/2.5/weather?lat=21.02&lon=105.84&appid=b6907d289e10d714a6e88b30761fae22";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mainJSONObject = response.getJSONObject("main");
                            String temp = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp")));
                            mTextViewTemp.setText(temp);

                            String city = response.getString("name");
                            mTextViewLocation.setText(city);

                            JSONArray weatherJSONObject = response.getJSONArray("weather");
                            JSONObject desObject = weatherJSONObject.getJSONObject(0);
                            String des = desObject.getString("description");
                            mTextViewDes.setText(des);
                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        RequestQueue quote = Volley.newRequestQueue(this);
        quote.add(jsObjRequest);
    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
        String formattedDate = dateFormat.format(calendar.getTime());
        return formattedDate;
    }
}
