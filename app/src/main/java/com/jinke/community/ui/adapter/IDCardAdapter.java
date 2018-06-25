package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.bean.IdCardBean;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/04/25.
 * 身份验证Adapter
 */

public class IDCardAdapter extends RecyclerView.Adapter<IDCardAdapter.ViewHolder> {
    private Context context;
    private List<IdCardBean> list = new ArrayList<>();
    private IDCardListener listener;

    public IDCardAdapter(Context context, List<IdCardBean> propertyBrokenList) {
        this.context = context;
        this.list = propertyBrokenList;
    }

    public IDCardAdapter(Context context, List<IdCardBean> propertyBrokenList, IDCardListener listener) {
        this.context = context;
        this.list = propertyBrokenList;
        this.listener = listener;
    }

    public void setDataList(List<IdCardBean> propertyBrokenList) {
        this.list = propertyBrokenList;
        notifyDataSetChanged();
    }

    public void setDataList(List<IdCardBean> propertyBrokenList, int position) {
        this.list = propertyBrokenList;
        notifyItemChanged(position);
    }

    public void addData(List<IdCardBean> propertyBrokenList) {
        this.list = propertyBrokenList;
        notifyItemChanged(propertyBrokenList.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IDCardAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_idcard, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        IdCardBean bean = list.get(position);
        if (bean != null) {
            if (position == list.size() - 1)
                holder.edInputName.requestFocus();
            holder.txIndex.setText("业主" + (position + 1));
            holder.txHouseNumber.setText(context.getResources().getString(R.string.activity_authentication_house_number) + bean.getHouseId());
            addListener(holder.edInputPhone, position, "phone");
            addListener(holder.edInputNumber, position, "number");
            addListener(holder.edInputName, position, "name");

            if (!StringUtils.isEmpty(bean.getNational())) {
                Glide.with(context)
                        .load(bean.getNational())
                        .placeholder(R.mipmap.icon_idcard_nation)
                        .error(R.mipmap.icon_idcard_nation)
                        .into(holder.imgNationalCard);
                holder.imgNationalCamera.setVisibility(View.GONE);
            } else {
                Glide.with(context)
                        .load(R.mipmap.icon_idcard_nation)
                        .into(holder.imgCard);
                holder.imgNationalCamera.setVisibility(View.VISIBLE);
            }

            if (!StringUtils.isEmpty(bean.getPortrait())) {
                Glide.with(context)
                        .load(bean.getPortrait())
                        .placeholder(R.mipmap.icon_idcard)
                        .error(R.mipmap.icon_idcard)
                        .into(holder.imgCard);
                holder.imgCamera.setVisibility(View.GONE);
            } else {
                Glide.with(context)
                        .load(R.mipmap.icon_idcard)
                        .into(holder.imgCard);
                holder.imgCamera.setVisibility(View.VISIBLE);
            }
            holder.imgBg.setOnClickListener(new CameraListener(context, position));
            holder.imgNationalBg.setOnClickListener(new CameraListener(context, position));
            if (position == 0) {
                holder.imgDelete.setVisibility(View.INVISIBLE);
            } else {
                holder.imgDelete.setVisibility(View.VISIBLE);
                holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.delete(position);
                    }
                });
            }
        }
    }

    private void addListener(EditText et, final int position, final String string) {
        if (et.getTag() instanceof TextWatcher) {
            et.removeTextChangedListener(((TextWatcher) et.getTag()));
        }
        switch (string) {
            case "name":
                et.setText(list.get(position).getName());
                break;
            case "number":
                et.setText(list.get(position).getIdcard());
                break;
            case "phone":
                et.setText(list.get(position).getPhone());
                break;///
        }
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (string) {
                    case "name":
                        list.get(position).setName(s.toString());
                        break;
                    case "number":
                        list.get(position).setIdcard(s.toString());
                        break;
                    case "phone":
                        list.get(position).setPhone(s.toString());
                        break;///
                }
            }
        };
        et.addTextChangedListener(watcher);
        et.setTag(watcher);
    }

    private class CameraListener implements View.OnClickListener {
        private int position;

        public CameraListener(Context context, int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_bg:
                    listener.camera(0, position);
                    break;

                case R.id.img_national_bg:
                    listener.camera(1, position);
                    break;
            }
        }
    }

    public interface IDCardListener {
        //index辨识正方面  position表示列表节点
        void camera(int index, int position);

        void delete(int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txIndex;
        ImageView imgDelete;
        TextView txHouseNumber;
        EditText edInputName;
        EditText edInputNumber;
        EditText edInputPhone;
        RelativeLayout rlPortrait;
        RelativeLayout rlNational;
        ImageView imgBg;
        ImageView imgCard;
        ImageView imgCamera;
        ImageView imgNationalBg;
        ImageView imgNationalCard;
        ImageView imgNationalCamera;

        public ViewHolder(View itemView) {
            super(itemView);
            txIndex = itemView.findViewById(R.id.tx_index);
            imgDelete = itemView.findViewById(R.id.img_delete);
            txHouseNumber = itemView.findViewById(R.id.tx_house_number);
            edInputName = itemView.findViewById(R.id.ed_input_name);
            edInputNumber = itemView.findViewById(R.id.ed_input_number);
            edInputPhone = itemView.findViewById(R.id.ed_input_phone);
            rlPortrait = itemView.findViewById(R.id.rl_portrait);
            rlNational = itemView.findViewById(R.id.rl_national);
            imgBg = itemView.findViewById(R.id.img_bg);
            imgCard = itemView.findViewById(R.id.img_card);
            imgCamera = itemView.findViewById(R.id.img_camera);
            imgNationalBg = itemView.findViewById(R.id.img_national_bg);
            imgNationalCard = itemView.findViewById(R.id.img_national_card);
            imgNationalCamera = itemView.findViewById(R.id.img_national_camera);
        }
    }
}
