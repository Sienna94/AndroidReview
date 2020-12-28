package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

        tv=findViewById(R.id.tv_user);
        tv2=findViewById(R.id.tv_pw);
        etUser = findViewById(R.id.et_user);
        etPw = findViewById(R.id.et_pw);
        btJoin = findViewById(R.id.btn_join);
        btLogin=findViewById(R.id.btn_login);

        btJoin.setOnClickListener(this);
        btLogin.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_join){
            Intent intent = new Intent(this, com.example.again.JoinActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_login){

        }
    }
}