package com.jinke.community.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jinke.community.R;

import java.util.List;

/**
 * Created by root on 17-8-6.
 */

public class BrokeUpAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> dataList;
    LayoutInflater inflater;

    private BrokeUpAdapterlistener listener;

    public BrokeUpAdapter(@NonNull Context context, @NonNull List<String> dataList, BrokeUpAdapterlistener listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int o = position;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_broken_up, null);
            holderView = new HolderView();
            holderView.simpleDraweeView = (ImageView) convertView.findViewById(R.id.image_add);
            holderView.delete = (ImageView) convertView.findViewById(R.id.image_delete);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        if (position == dataList.size() - 1) {
            holderView.delete.setVisibility(View.GONE);
            holderView.simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext)
                    .load(R.drawable.icon_add_broken)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .skipMemoryCache(true)//跳过内存缓存
                    .placeholder(R.drawable.icon_fail_pic)
                    .error(R.drawable.icon_fail_pic)
                    .into(holderView.simpleDraweeView);
        } else {
            Glide.with(mContext)
                    .load(Uri.parse("file://" + dataList.get(position)))
                    .placeholder(R.drawable.icon_fail_pic)
                    .error(R.drawable.icon_fail_pic)
                    .into(holderView.simpleDraweeView);
            holderView.delete.setVisibility(View.VISIBLE);
            holderView.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listener.onBack(dataList.get(position));
                    listener.onDeletePicture(position);
                }
            });
        }

        return convertView;
    }

    HolderView holderView;

    class HolderView {
        //        SimpleDraweeView simpleDraweeView;
        ImageView simpleDraweeView;
        ImageView delete;
    }

    public interface BrokeUpAdapterlistener {
        void onDeletePicture(int position);
    }
}
