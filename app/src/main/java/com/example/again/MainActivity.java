package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    TextView tv;
    TextView tv2;
    EditText etUser;
    EditText etPw;
    Button btJoin;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=findViewById(R.id.iv_title);
        tv=findViewById(R.id.tv_user);
        tv2=findViewById(R.id.tv_pw);
        etUser = findViewById(R.id.et_user);
        etPw = findViewById(R.id.et_pw);
        btJoin = findViewById(R.id.btn_join);
        btLogin=findViewById(R.id.btn_login);
        btJoin.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }
    //ID PW 유효성 검사
    private boolean isValid() {
        boolean isValid = true;
        if (etUser.getText().toString().trim().length() < 1) {
            isValid = false;
        } else if (etUser.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (etPw.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (etPw.getText().toString().trim().length() < 1) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("kkk", "로그인 실패");
        }
    };

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("kkk", response);

        }
    };
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_join){
            Intent intent = new Intent(this, com.example.again.JoinActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_login){
            //로그인 버튼 누르면 1. 통신 2. 값 받아오기 3. alert 띄우기 4. 리스트뷰로 이동
            //로그인 시도
            final String id = etUser.getText().toString().trim();
            final String pw = etPw.getText().toString().trim();
            //post//
            RequestQueue stringRequest = Volley.newRequestQueue(this);
            String url = "http://172.20.10.4:8180/oop/androidLogin.do";
//        http://192.168.7.26
            StringRequest myReq = new StringRequest(Request.Method.POST, url,
                    successListener, errorListener) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id",id);
                    params.put("pass", pw);
                    return params;
                }
            };
            myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
            );
            stringRequest.add(myReq);

            Intent intent = new Intent(this, com.example.again.MyListActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "아이디 패스워드를 확인하세요 :(", Toast.LENGTH_SHORT).show();
        }
    }
}