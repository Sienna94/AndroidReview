package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailListActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<ItemCommentData> arr = new ArrayList<>();
    TextView tv;
    EditText input;
    Button btn;
    ListView lvDetail;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        tv=findViewById(R.id.tv_tit);
        input=findViewById(R.id.et_comment);
        btn=findViewById(R.id.bt_commentinput);
        lvDetail=findViewById(R.id.lv_comment);

        btn.setOnClickListener(this);

        requestForData();
    };
    //댓글 삭제하기
    private void deleteComment(final String cidx){
        Response.ErrorListener errorListener3 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kkk", "댓글 삭제 실패");
            }
        };
        Response.Listener<String> successListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kkk", "댓글 삭제 성공" + response);
                adapter.notifyDataSetChanged();
            }
        };
        //삭제 버튼 클릭시 댓글 삭제하기 delete
        Log.d("bbb", "onClick : 댓글 삭제 try");
        /** post **/
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://172.20.10.4:8180/oop/androidCommentDelete.do";
        /*192.168.7.26 학원 ip*/

        StringRequest myReq2 = new StringRequest(Request.Method.POST, url,
                successListener3, errorListener3) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cidx", cidx);
                return params;
            }
        };
        myReq2.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq2);
        adapter = new MyAdapter(this);
        lvDetail.setAdapter(adapter);
    }

    //댓글 불러오기
    private void requestForData(){
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("commentlist", "댓글 리스트 실패");
            }
        };
        Response.Listener<String> successListener = new Response.Listener<String>() {
            //가져온 jsonArray 리스트뷰로 나타내기
            @Override
            public void onResponse(String response) {
                Log.d("commentlist", "JSON시작");
                try {
                    JSONArray proArr = new JSONArray(response);

                    for (int i = 0; i < proArr.length(); i++) {
                        JSONObject proObj = proArr.getJSONObject(i);
                        String cIDX = proObj.getString("cidx");
                        String pID = proObj.getString("pid");
                        String mID = proObj.getString("mid");
                        String cDATE = proObj.getString("cdate");
                        String cCONTENT = proObj.getString("ccontent");
                        String cISMINE = proObj.getString("cismine");
                        String cREPLY = proObj.getString("creply");
                        //리스트에 보여줄 어레이에 추가
                        arr.add(i, new ItemCommentData(cIDX,pID,mID,cDATE,cCONTENT,cISMINE,cREPLY));
                        Log.d("ccc", arr.get(i).idx);
                        Log.d("ccc", arr.get(i).pid);
                        Log.d("ccc", arr.get(i).mid);
                        Log.d("ccc", arr.get(i).date);
                        Log.d("ccc", arr.get(i).content);
                        Log.d("ccc", arr.get(i).isMine);
                        Log.d("ccc", arr.get(i).reply);

                    }
                    //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //post방식으로 받아오기//
        Intent intent = getIntent(); //전 페이지에서 데이터 가져오기
        final String pid = intent.getExtras().getString("pid");
        Log.d("commentlist", pid);

        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://172.20.10.4:8180/oop/androidCommentList.do";
//        http://192.168.7.26
//        http://172.20.10.4
        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pid", pid);
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
        adapter = new MyAdapter(this);
        lvDetail.setAdapter(adapter);
    }

    //댓글 입력하기
    private void commentInsert(){
        Response.ErrorListener errorListener2 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kkk", "댓글등록 실패");
            }
        };

        Response.Listener<String> successListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kkk", "댓글등록 성공" + response);
                adapter.notifyDataSetChanged();
            }
        };
        //입력 버튼 클릭시 댓글 등록하기create
        Log.d("bbb", "onClick : 댓글등록 try");
        final String ccontent = input.getText().toString().trim();
        /** post **/
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://172.20.10.4:8180/oop/androidCommentInsert.do";
        /*192.168.7.26 학원 ip*/

        StringRequest myReq2 = new StringRequest(Request.Method.POST, url,
                successListener2, errorListener2) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pid", getIntent().getExtras().getString("pid"));
                params.put("mid", getIntent().getExtras().getString("mid"));
                params.put("ccontent", ccontent);
                return params;
            }

        };
        myReq2.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq2);
        adapter = new MyAdapter(this);
        lvDetail.setAdapter(adapter);
        requestForData();
    }
    //comment 버튼 클릭
    @Override
    public void onClick(View v) {
        commentInsert();
    }

    //리스트에 출력될 아이템들 (버튼 포함)
    class ItemHolder{
        TextView tvWriterHolder;
        TextView tvContentHolder;
        TextView tvDateHolder;
        Button btn_del;
        Button btn_edit;
    }

    class MyAdapter extends ArrayAdapter{
        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context, R.layout.item_comment, arr);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemHolder viewHolder;
            if(convertView == null){
                convertView = lnf.inflate(R.layout.item_comment, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_writer);
                viewHolder.tvContentHolder= convertView.findViewById(R.id.tv_comment);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);
                //수정 삭제 버튼
                viewHolder.btn_del = convertView.findViewById(R.id.btn_delete);
                viewHolder.btn_edit =convertView.findViewById(R.id.btn_edit);

                viewHolder.btn_del.setTag(position);
                viewHolder.btn_edit.setTag(position);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }

            viewHolder.tvWriterHolder.setText(arr.get(position).mid);
            viewHolder.tvContentHolder.setText(arr.get(position).content);
            viewHolder.tvDateHolder.setText(arr.get(position).date);

            //삭제, 수정 버튼 클릭
            viewHolder.btn_del.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String mid = getIntent().getExtras().getString("mid");
                    if(arr.get(position).mid == mid){
                        String idx = arr.get(position).idx;
                        deleteComment(idx);
                    }else{
                        // 왜 토스트가 안돼?... 대체 왜?
                    }
                }
            });
            viewHolder.btn_edit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String mid = getIntent().getExtras().getString("mid");
                    if(arr.get(position).mid == mid){
                        String idx = arr.get(position).idx;

                    }else{

                    }
                }
            });

            return convertView;
        }
    }
}