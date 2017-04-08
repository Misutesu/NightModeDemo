package com.misutesu.nightmodedemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Misutesu on 2017/4/8.
 */

public class MyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mList;

    public MyAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.normal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewHolder = (NormalViewHolder) holder;
        viewHolder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private ImageView mImg;
        private TextView mTextView;

        private NormalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.item_card_view);
            mImg = (ImageView) itemView.findViewById(R.id.item_img);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
