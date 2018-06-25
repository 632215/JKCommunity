package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.ui.widget.ExpandableTextView;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.utils.DecimalInputFilter;
import com.jinke.community.utils.DrawableUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/25.
 */

public class PrepaidRecAdapter extends RecyclerView.Adapter<PrepaidRecAdapter.ViewHolder> {
    private Context context;
    private List<PrepaidExpensesBean.ListBean> list;
    private PrepaidRecListener listener;

    public PrepaidRecAdapter(Context context, List<PrepaidExpensesBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public PrepaidRecAdapter(Context context, List<PrepaidExpensesBean.ListBean> list, PrepaidRecListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDataList(List<PrepaidExpensesBean.ListBean> breakList) {
        list = breakList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PrepaidRecAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expenses_list, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PrepaidExpensesBean.ListBean bean = list.get(position);
        holder.resetMoney.setText(StringUtils.isEmpty(bean.getMoney()) ? "" : bean.getMoney().toString().trim());
        holder.resetMoney.setFilters(new InputFilter[]{
                new DecimalInputFilter(5, 2)
        });
        holder.title.setText(bean.getPrepayName());
        holder.prepaidMoney.setText(DecimalFormatUtils.format(Double.parseDouble(bean.getAmount()), "0.00"));
        switch (bean.getPrepayName()) {
            case "物业服务费":
                DrawableUtils.setDrawableLeft(context, holder.title, R.mipmap.icon_property_costs);
                break;

            case "水电公摊费":
                DrawableUtils.setDrawableLeft(context, holder.title, R.mipmap.icon_water_and_electricity);
                break;

            case "车位管理费":
                DrawableUtils.setDrawableLeft(context, holder.title, R.mipmap.icon_parking_fee);
                break;
        }
        addListener(holder.resetMoney, position, bean.getPrepayName().trim());
    }

    private void addListener(final EditText et, final int position, final String string) {
        if (et.getTag() instanceof TextWatcher) {
            et.removeTextChangedListener(((TextWatcher) et.getTag()));
        }
        switch (string) {
            case "物业服务费":
                et.setText(StringUtils.isEmpty(list.get(position).getMoney()) ? "" : list.get(position).getMoney().toString().trim());
                break;
            case "水电公摊费":
                et.setText(StringUtils.isEmpty(list.get(position).getMoney()) ? "" : list.get(position).getMoney().toString().trim());
                break;
            case "车位管理费":
                et.setText(StringUtils.isEmpty(list.get(position).getMoney()) ? "" : list.get(position).getMoney().toString().trim());
                break;
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
                if (listener != null) {
                    if (StringUtils.equals(s, ".")) {
                        et.setText("0.");
                        et.setSelection(et.getText().length());
                    } else {
                        switch (string) {
                            case "物业服务费":
                                list.get(position).setMoney(s.toString());
                                break;
                            case "水电公摊费":
                                list.get(position).setMoney(s.toString());
                                break;
                            case "车位管理费":
                                list.get(position).setMoney(s.toString());
                                break;///
                        }
                        listener.inPut(list);
                    }
                }
            }
        };
        et.addTextChangedListener(watcher);
        et.setTag(watcher);
    }

    public interface PrepaidRecListener {
        void inPut(List<PrepaidExpensesBean.ListBean> list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView prepaidMoney;
        EditText resetMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.property_text);
            prepaidMoney =  itemView.findViewById(R.id.property_lave_money_text);
            resetMoney = itemView.findViewById(R.id.ed_reset_money);
        }
    }
}
