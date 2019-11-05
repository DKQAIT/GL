package com.jtjt.qtgl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luck.picture.lib.entity.LocalMedia;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.util.ComUtil;

import java.util.List;

public class MyRecyclerViewAdapter  extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> list;

    public MyRecyclerViewAdapter(List<String> selectList1) {
        list=selectList1;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        MyRecyclerViewAdapter.ViewHolder viewHolder = new MyRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.mText.setText(list.get(position));
        ComUtil.display(holder.imageView, list.get(position), R.mipmap.default_1);

        holder.yc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public List<String> yichu(int position){
        list.remove(position);

        return list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;
       ImageView yc;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            yc = (ImageView) itemView.findViewById(R.id.yichu);
        }
    }
}
