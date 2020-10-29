package com.example.healthmonitor;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;

public class AdviceFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_NAME = "user_name";
    private static final String SESSION = "session";

    private String userName;
    private String session;
    private String covidHost = "https://covid19-api.org/api";

    private Context mContext;

    private TextView textUser;
    private TextView textCovidCases;
    private TextView textCovidAdvice;
    private TextView textTips;
    private ScrollView scrollWidgets;
    private RelativeLayout layoutWidgets;


    public static AdviceFragment newInstance(String user_name, String session) {
        AdviceFragment fragment = new AdviceFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, user_name);
        args.putString(SESSION, session);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AdviceFragment", "onCreate");
        if (getArguments() != null) {
            userName = getArguments().getString(USER_NAME);
            session = getArguments().getString(SESSION);
        }
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("AdviceFragment", "onCreateView");
        return inflater.inflate(R.layout.advice_page, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("AdviceFragment", "onViewCreated");
        scrollWidgets = view.findViewById(R.id.scrollView_widgets);
        layoutWidgets = view.findViewById(R.id.layout_widgets);
        textCovidCases = view.findViewById(R.id.text_covid_cases);
        textCovidAdvice = view.findViewById(R.id.text_covid_advice);
        textTips = view.findViewById((R.id.text_tips));
        refreshAll();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("AdviceFragment", "onAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("AdviceFragment", "onResume");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshAll(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String stringDate = simpleDateFormat.format(date);
        Log.d("date", stringDate);
        getCurrentCovidCases("US", stringDate);
        getDiffCovidCases("US", stringDate);
        getTips();
    }

    private void getCurrentCovidCases(String region, String date){
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String covid_cases_url = covidHost + "/status/" + region + "?date=" + date;
        Log.d("new_cases", covid_cases_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, covid_cases_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("new_cases", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            textCovidCases.setText(jsonObject.optString("cases"));
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("cases",error.toString());
            }
        });
        queue.add(stringRequest);
        return;
    }

    private void getDiffCovidCases(String region, String date){
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String covid_cases_url = covidHost + "/diff/" + region + "?date=" + date;
        Log.d("cases", covid_cases_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, covid_cases_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("cases", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String strNewCases = jsonObject.optString("new_cases");
                            int intNewCases = Integer.parseInt(strNewCases);
                            String covidAdvice = "There are " + strNewCases + " new cases yesterday.";
                            textCovidAdvice.setText(covidAdvice);
                            if (intNewCases>0 && intNewCases<=100){
                                covidAdvice += "\nPlease wear mask when getting out.";
                            } else if (intNewCases > 100) {
                                covidAdvice += "\nYou'd better stay at home!";
                            } else if (intNewCases == 0){
                                covidAdvice += "It is a nice day.";
                            }
                            textCovidAdvice.setText(covidAdvice);
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("cases",error.toString());
            }
        });
        queue.add(stringRequest);
        return;
    }

    private void getTips(){
        String[] listTips = {"Washing your hands actively can be effective in preventing viruses.",
                "When you are indoors, please always open the window to ventilate.",
                "Please wear a mask when you go out.",
                "When you cough or sneeze, cover your mouth and nose.",
                "When cooking, the boards that handle raw food should be separated from cooked food.",
                "When you are queuing at the supermarket cashier, keep at least 1 meter away from the customer in front of you.",
                "When you are taking the elevator, please keep your distance from others.",
                "Please do not take off your mask when taking public transportation, as this will increase the risk of infection."};
        int tipIndex = (int)(listTips.length * Math.random());
        textTips.setText(listTips[tipIndex]);
    }
}
