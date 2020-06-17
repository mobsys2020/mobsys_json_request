package com.health.myhealthapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.health.myhealthapplication.adapter.MedsAdapter;
import com.health.myhealthapplication.models.MedPlan;
import com.health.myhealthapplication.models.Meds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedplanActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medplan2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Medikationsplan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }

    public void update_medplan(View v){
        SharedPreferences pref = getSharedPreferences("user_key", MODE_PRIVATE);
        String url = "https://mobsysbackend.herokuapp.com/request.json";
        //String url = "http://192.168.0.5/request/2.json";
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();
        params.put("uat", pref.getString("user_key","missing"));
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, parameters,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        TextView tvarzt = findViewById(R.id.textViewArzt);
                        TextView tvpatient = findViewById(R.id.textViewPatient);
                        JSONObject json = (JSONObject) response;
                        Gson g = new Gson();
                        MedPlan medplan = g.fromJson(json.toString(),MedPlan.class);
                        tvarzt.setText("Austellender Arzt: " + medplan.doctor);
                        tvpatient.setText("Ausgestellt für: " + medplan.patient);

                        ListView lv = findViewById(R.id._lv);
                        ArrayList<Meds> medlist = medplan.meds;
                        MedsAdapter adapter = new MedsAdapter(getApplicationContext(),medlist,20);
                        lv.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //TextView tv = findViewById(R.id.textViewtest);

                        //tv.setText(error.networkResponse.data.toString());

                    }
                }
        );
        requestQueue.add(jsonObjReq);

    }

}
