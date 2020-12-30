package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity {
/*    1. item 위한 레이아웃 만들기
      2. item 위한 데이터클래스 만들기 / item xml

      3. 어레이 리스트 만들기
      5. ListView 만들기
      6. 아답터 클래스 복붙하기
      7. 아답터 빨간 부분 수정(대부분 대문자는 import 하면 됨 holder 제외)
      8. 이전과 동일*/
    ArrayList<ItemData> arr = new ArrayList<>();

    ListView lvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        lvMain = findViewById(R.id.lv1);
    }

    class ItemHolder{

    }
}