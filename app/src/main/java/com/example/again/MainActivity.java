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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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

            Log.d("chk", "로그인 통신: start");
            params.clear();
            params.put("id",id);
            params.put("pass", pw);
            Log.d("loginchk", "id:"+id+"/pass:"+pw);
            request("androidLogin.do", successListener);

            Intent intent = new Intent(this, com.example.again.MyListActivity.class);
            intent.putExtra("mid", id);
            startActivity(intent);

        } else {
            Toast.makeText(this, "아이디 패스워드를 확인하세요 :(", Toast.LENGTH_SHORT).show();
        }
    }
}