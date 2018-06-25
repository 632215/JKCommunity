package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jinke.community.R;
import com.jinke.community.bean.NoteBean;

import java.util.List;

import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/9.
 */

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {
    private List<NoteBean.ListBean> mList = null;
    private Context mContext;
    private NoteRecyclerAdapterListener listener;

    public NoteRecyclerAdapter(List<NoteBean.ListBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setData(List<NoteBean.ListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setListener(NoteRecyclerAdapterListener listener) {
        this.listener = listener;
    }

    public List getData() {
        return mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoteBean.ListBean bean = mList.get(position);
        if (bean != null) {
            holder.cbNote.setText(bean.getLabel_name());
            holder.cbNote.setBackground(bean.getIsMark() == 1 ? mContext.getResources().getDrawable(R.drawable.shape_note_selected)
                    : mContext.getResources().getDrawable(R.drawable.shape_note_no_selected));
            holder.cbNote.setTextColor(bean.getIsMark() == 1 ? mContext.getResources().getColor(R.color.color_main)
                    : mContext.getResources().getColor(R.color.color_text));
            holder.cbNote.setChecked(bean.getIsMark() == 1 ? true : false);
            holder.cbNote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!compoundButton.isPressed())
                        return;
                    if (b == true) {
                        int count = 0;
                        for (NoteBean.ListBean listBean : mList) {
                            if (listBean.getIsMark() == 1)
                                count++;
                        }
                        if (count == 3) {
                            ToastUtils.showShort(mContext, "请选择1—3个，可随时调整");
                            return;
                        }
                    }
                    mList.get(position).setIsMark(b == true ? 1 : 0);
                    holder.cbNote.setBackground(b == true ? mContext.getResources().getDrawable(R.drawable.shape_note_selected)
                            : mContext.getResources().getDrawable(R.drawable.shape_note_no_selected));
                    holder.cbNote.setTextColor(b == true ? mContext.getResources().getColor(R.color.color_main)
                            : mContext.getResources().getColor(R.color.color_text));
                    listener.noteClick(mList);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbNote;

        public ViewHolder(View itemView) {
            super(itemView);
            cbNote = itemView.findViewById(R.id.cb_note);
        }
    }

    public interface NoteRecyclerAdapterListener {
        void noteClick(List<NoteBean.ListBean> mList);
    }
}
