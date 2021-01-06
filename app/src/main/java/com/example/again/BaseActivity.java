package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {
    Map<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void request(String url, Response.Listener<String> successListener){
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        url = "http://192.168.7.4:8180/oop/" + url;
//        http://192.168.7.4 (학원)
//        http://172.20.10.4 (집)

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("ErrorResponse", "통신 실패");
        }
    };
}