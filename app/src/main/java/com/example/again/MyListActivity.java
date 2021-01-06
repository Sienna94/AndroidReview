package com.example.again;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
/*    1. item 위한 레이아웃 만들기
      2. item 위한 데이터클래스 만들기 / item xml
      3. 어레이 리스트 만들기
      5. ListView 만들기
      6. 아답터 클래스 복붙하기
      7. 아답터 빨간 부분 수정(대부분 대문자는 import 하면 됨 holder 제외)
      8. 이전과 동일*/
    ArrayList<ItemData> arr = new ArrayList<>();
    ImageView iv;
    ImageView pimg;
    ListView lvMain;
    MyAdapter adapter; //전역에서 안쓰면 못 불러옴.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        iv=findViewById(R.id.iv_title);
        pimg=findViewById(R.id.iv_pimage);
        lvMain = findViewById(R.id.lv1);

        requsetForData();
    }

    private void requsetForData(){
        //상속받은 부분
        Log.d("chk", "제품 리스트 requsetForData: start");
        params.clear();
        request("androidProductList.do", successListener);
        //어댑터에 적용
        adapter = new MyAdapter(this);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);
    }
    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray 리스트뷰로 나타내기
        @Override
        public void onResponse(String response) {
            try {
                JSONArray proArr = new JSONArray(response);

                for (int i = 0; i < 10; i++) {
                    JSONObject proObj = proArr.getJSONObject(i);
                    String pN = proObj.getString("pname");
                    String pI = proObj.getString("pimage1");
                    String pC = proObj.getString("pcontent");
                    String pID = proObj.getString("pid");
                    //리스트에 보여줄 어레이에 추가
                    arr.add(i, new ItemData(pN, pI, pC, pID));
                }
                //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //해당 제품 후기 페이지로 넘어가도록
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = getIntent();
        String mid = intent.getExtras().getString("mid");

        Intent intent2 = new Intent(this, com.example.again.DetailListActivity.class);
        intent2.putExtra("mid", mid);
        Log.d("commentlist", mid);
        intent2.putExtra("pid", arr.get(position).pID);
        Log.d("commentlist", arr.get(position).pID);
        startActivity(intent2);
    }

    //리스트 관련
    class ItemHolder {
        TextView tvPnameHolder;
        ImageView ivPimage1Holder;
        TextView tvPcontentHolder;
    }
    class MyAdapter extends ArrayAdapter{
        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context, R.layout.item, arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder viewHolder;
            if(convertView == null){
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvPnameHolder = convertView.findViewById(R.id.tv_pname);
                viewHolder.tvPcontentHolder = convertView.findViewById(R.id.tv_pcontent);
                viewHolder.ivPimage1Holder = convertView.findViewById(R.id.iv_pimage);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }

            viewHolder.tvPnameHolder.setText(arr.get(position).pName);
            viewHolder.tvPcontentHolder.setText(arr.get(position).pContent);

            //제품 사진도 바꿔줌
            Glide.with(MyListActivity.this)
                    .load("http://172.20.10.4:8180/oop/img/shoes/"+arr.get(position).pImage1)
                    .into(viewHolder.ivPimage1Holder);
            Log.d("img", "http://172.20.10.4/oop/img/shoes/"+arr.get(position).pImage1);
//        http://192.168.7.26
//        http://172.20.10.4
            return convertView;
        }
    }
}