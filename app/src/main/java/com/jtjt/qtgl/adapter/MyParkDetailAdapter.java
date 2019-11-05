package com.jtjt.qtgl.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtjt.qtgl.R;
import com.jtjt.qtgl.activity.EquipmentActivity;
import com.jtjt.qtgl.bean.base.ParkDetailBean;
import com.jtjt.qtgl.util.ComUtil;

import java.util.List;

public class MyParkDetailAdapter extends RecyclerView.Adapter<MyParkDetailAdapter.ViewHolder> {

    private List<ParkDetailBean> lists;
    private Context context;


    public MyParkDetailAdapter(List<ParkDetailBean> lists) {
        this.lists = lists;
    }

    public MyParkDetailAdapter(List<ParkDetailBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parkdetail_item, parent, false);
        MyParkDetailAdapter.ViewHolder viewHolder = new MyParkDetailAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.mText.setText(list.get(position));

        Log.e("获取没有图片的状态","获取没有图片的 状态"+lists.get(position));
        if (lists.get(position) == null ||lists.get(position).equals("") ) {
//            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.t_name.setText("名称："+lists.get(position).getName()+" *"+lists.get(position).getNumber());
            holder.t_gg.setText("规格："+lists.get(position).getModel());
            holder.t_xh.setText("品牌："+lists.get(position).getBrand());


        }

        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EquipmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID",lists.get(position).getId()+"");
              intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t_name,t_gg,t_xh,ckxq;
        public ViewHolder(View itemView) {
            super(itemView);
            t_name = (TextView) itemView.findViewById(R.id.t_name);
            t_gg = (TextView) itemView.findViewById(R.id.t_gg);
            t_xh = (TextView) itemView.findViewById(R.id.t_xh);
            ckxq = (TextView) itemView.findViewById(R.id.ckxq);
        }
    }
}
