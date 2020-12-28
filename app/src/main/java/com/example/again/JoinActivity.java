package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class JoinActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;

    ImageView img;

    EditText etId;
    EditText etName;
    EditText etPhone;
    EditText etPw1;
    EditText etPw2;

    Button btSubmit;

    CheckBox chk1;
    CheckBox chk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        tv1=findViewById(R.id.tv_join);
        tv2=findViewById(R.id.tv_agr1);
        tv3=findViewById(R.id.tv_agr2);
        tv4=findViewById(R.id.tv_agrContent1);

        etId = findViewById(R.id.et_email);
        etName=findViewById(R.id.et_name);
        etPhone=findViewById(R.id.et_phone);
        etPw1=findViewById(R.id.et_pw);
        etPw2=findViewById(R.id.et_pwChk);

        btSubmit=findViewById(R.id.btn_join);

        chk1=findViewById(R.id.chk1);
        chk2=findViewById(R.id.chk2);

        img=findViewById(R.id.image_chart);

        btSubmit.setOnClickListener(this);
    }

    private boolean isValid() {
        boolean isValid = true;
        if (etId.getText().toString().trim().length() < 1) {
            isValid = false;
        } else if (etId.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (etPw1.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (etPw1.getText().toString().trim().length() < 1) {
            isValid = false;
        } else if (!etPw1.getText().toString().trim().equals(etPw2.getText().toString().trim())) {
            isValid = false;
        } else if (etName.getText().toString().trim().length() < 1) {
            isValid = false;
        } else if (etPhone.getText().toString().trim().length() < 1) {
            isValid = false;
        }
        return isValid;
    }
/*

    @Override
    public void onClick(View v) {
        boolean isValid = false;
        if(etId.getText().toString().trim().length() < 1){
            isValid = false;
        }else if(etId.getText().toString().trim().contains("  ")){
            isValid = false;
        }else if(etPw1.getText().toString().trim().contains("  ")){
            isValid = false;
        }else if(!etPw1.getText().toString().trim().equals(etPw2.getText().toString().trim())){
            isValid=false;
        }else if(etName.getText().toString().trim().length() < 1){
            isValid = false;
        }else if(etPhone.getText().toString().trim().length() <1){

        }

    }*/
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

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
        if (isValid()) {
            //회원가입 시도
            String id = etId.getText().toString().trim();
            String pw = etPw1.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            id= "id="+id;   //id=aaa
            pw = "join_pass="+pw; //pw=1234
            name = "name="+name;
            phone = "phone="+phone;
            String url = "http://172.20.10.4:8180/oop/join.do?"+id+"&"+pw+"&"+name+"&"+phone;
            //192.168.35.215:8080/gather/insertJoin.do?id=aaa&pw=1234&name=zzzz&phone01010101

            //172.20.10.4:8180/oop/contentList.do

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);
            requestQueue.add(myReq);
        } else {
            Toast.makeText(this, "데이터가 올바르지 않습니다 너처럼!", Toast.LENGTH_SHORT).show();
        }
    }
}