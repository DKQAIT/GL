package com.jtjt.qtgl.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jtjt.qtgl.R;
import com.jtjt.qtgl.util.ComUtil;

import java.util.List;

public class MyRecyclerVAdapter extends RecyclerView.Adapter<MyRecyclerVAdapter.ViewHolder> {

    private List<String> list;

    public MyRecyclerVAdapter(List<String> selectList1) {
        list=selectList1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_vitem, parent, false);
        MyRecyclerVAdapter.ViewHolder viewHolder = new MyRecyclerVAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.mText.setText(list.get(position));

        Log.e("获取没有图片的状态","获取没有图片的 状态"+list.get(position));
        if (list.get(position) == null ||list.get(position).equals("") ) {
            holder.imageView.setVisibility(View.GONE);
        }else {
            ComUtil.display(holder.imageView, list.get(position), R.mipmap.default_1);
        }

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
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
