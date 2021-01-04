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

public class DetailListActivity extends AppCompatActivity {
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

        Intent intent = getIntent(); //전 페이지에서 데이터 가져오기
        final String pid = intent.getExtras().getString("pid");

        Log.d("commentlist", pid);

        tv=findViewById(R.id.tv_tit);
        input=findViewById(R.id.et_comment);
        btn=findViewById(R.id.bt_commentinput);
        lvDetail=findViewById(R.id.lv_comment);

        //post방식으로 받아오기//

        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://192.168.7.26:8180/oop/androidCommentList.do";
//        http://192.168.7.26
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

    //리스트에 출력될 아이템들
    class ItemHolder{
        TextView tvWriterHolder;
        TextView tvContentHolder;
        TextView tvDateHolder;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder viewHolder;
            if(convertView == null){
                convertView = lnf.inflate(R.layout.item_comment, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_writer);
                viewHolder.tvContentHolder= convertView.findViewById(R.id.tv_comment);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }

            Log.d("aa", "getView:"+position);
            Log.d("aa", "getView:"+arr);
            Log.d("aa", "getView:"+arr.get(position).mid);


            viewHolder.tvWriterHolder.setText(arr.get(position).mid);
            viewHolder.tvContentHolder.setText(arr.get(position).content);
            viewHolder.tvDateHolder.setText(arr.get(position).date);

            return convertView;
        }
    }
}