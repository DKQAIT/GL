package com.jtjt.qtgl.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.TlistBean;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.ComUtil;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerVListAdapter extends RecyclerView.Adapter<MyRecyclerVListAdapter.ViewHolder> {

    private List<TlistBean> list;
    private Context context;
    private MyRecyclerVAdapter mAdapter;
    private JSONArray js;
    private List<String> aa;

    public MyRecyclerVListAdapter(List<TlistBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyRecyclerVListAdapter(List<TlistBean> selectList1) {
        list=selectList1;

        Log.e("Adapter","Adapter获取信息"+list.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclelist_item, parent, false);
        MyRecyclerVListAdapter.ViewHolder viewHolder = new MyRecyclerVListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.e("Adapter",list.size()+"Adapter获取信息"+list.get(position).getUsername());

        holder.t_name.setText("处理人："+list.get(position).getUsername());
        holder.t_content.setText("处理描述："+list.get(position).getContent());

        String start=    AppUtil.getDateTime(list.get(position).getCreate_time(),"yyyy-MM-dd HH:mm");
        holder.t_time.setText("处理时间："+start);


       holder. recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

try {

    aa=  new ArrayList<>();
                                try {
                                js= JSONArray.parseArray(list.get(position).getImg().toString());
                                for (int i = 0;i<js.size();i++){
                                    aa.add(js.get(i).toString());
                                }
                            }catch (Exception e){
                                Log.e("错过","错过"+e);

                            }

                            Log.e("获取AA图片的额长度","获取AA图片的的长度"+aa.size());

}catch (Exception e){
                            Log.e("获取出错","获取出错"+e);
}


    mAdapter = new MyRecyclerVAdapter(aa);
//设置添加或删除item时的动画，这里使用默认动画
    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
//设置适配器
    holder.recyclerView.setAdapter(mAdapter);


//        ComUtil.display(holder.imageView, list.get(position), R.mipmap.default_1);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       RecyclerView recyclerView;
        TextView t_name,t_time,t_content;
        public ViewHolder(View itemView) {
            super(itemView);
            t_name = (TextView) itemView.findViewById(R.id.t_name);
            t_time = (TextView) itemView.findViewById(R.id.t_time);
            t_content = (TextView) itemView.findViewById(R.id.t_content);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);


        }
        }
    }
