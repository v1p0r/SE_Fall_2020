package com.example.healthmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CovidMore extends AppCompatActivity {

    private String covidHost = "https://api.covidtracking.com/v1/states/";
    //private Context mContext;

    private TextView textCases;
    private TextView textIncrease;
    private TextView textHospitalized;
    private TextView textDeath;
    private TextView textLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CovidMore", "onCreate");
        setContentView(R.layout.activity_covid_more);
        textLocation = findViewById(R.id.text_covid_more_location);
        textCases = findViewById(R.id.text_covid_more_cases);
        textIncrease = findViewById(R.id.text_covid_more_increase);
        textHospitalized = findViewById(R.id.text_covid_more_hiospitalized);
        textDeath = findViewById(R.id.text_covid_death);

        textLocation.setText("New Jersey");

        setInfo("nj");
    }

    private void setInfo(String region){
        final Context mContext = CovidMore.this;
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String covid_cases_url = covidHost + region + "/current.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, covid_cases_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("AdviceFragment", "new cases: "+response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            textCases.setText(jsonObject.optString("positive"));
                            textIncrease.setText(jsonObject.optString("positiveIncrease"));
                            textHospitalized.setText(jsonObject.optString("hospitalizedCurrently"));
                            textDeath.setText(jsonObject.optString("deathConfirmed"));
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("CovidMore","getCurrentCovidCases error: "+error.toString());
            }
        });
        queue.add(stringRequest);
        return;
    }
}