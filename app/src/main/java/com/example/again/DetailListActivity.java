package com.example.again;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
        tv=findViewById(R.id.tv_tit);
        input=findViewById(R.id.et_comment);
        btn=findViewById(R.id.bt_commentinput);
        lvDetail=findViewById(R.id.lv_comment);
    }
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
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_writer);
                viewHolder.tvContentHolder= convertView.findViewById(R.id.tv_comment);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }

            viewHolder.tvWriterHolder.setText(arr.get(position).mid);
            viewHolder.tvContentHolder.setText(arr.get(position).content);
            viewHolder.tvDateHolder.setText(arr.get(position).date);

            return convertView;
        }
    }
}