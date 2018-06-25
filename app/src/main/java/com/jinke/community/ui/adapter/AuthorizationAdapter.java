package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.ui.toast.RelationshipWindow;
import com.jinke.community.ui.widget.ContainsEmojiEditText;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-2.
 */

public class AuthorizationAdapter extends CommonAdapter<HouseListBean.ListBean.Grants> {
    RelationshipWindow window;
    private Context context;

    public AuthorizationAdapter(@NonNull Context context, int layoutResId, @NonNull List<HouseListBean.ListBean.Grants> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
        window = new RelationshipWindow(context);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final HouseListBean.ListBean.Grants grants, int position) {
        TextView delete = (TextView) baseViewHolder.getViewByViewId(R.id.tx_authorization_delete);
        EditText phone = (EditText) baseViewHolder.getViewByViewId(R.id.ed_owner_phone);
        ContainsEmojiEditText name = (ContainsEmojiEditText) baseViewHolder.getViewByViewId(R.id.ed_owner_name);
        final TextView relationship = (TextView) baseViewHolder.getViewByViewId(R.id.tx_relationship);
        name.addTextChangedListener(new EditChangedListener(name, grants, "name"));
        phone.addTextChangedListener(new EditChangedListener(phone, grants, "phone"));

        phone.setText(StringUtil.isEmpty(grants.getGrantPhone())?"":grants.getGrantPhone());
        name.setText(StringUtil.isEmpty(grants.getGrantName())?"":grants.getGrantName());
        relationship.setText(StringUtils.isEmpty(grants.getRelation()) ? "请选择" : getRelationship(grants.getRelation()));
        if (StringUtils.isEmpty(grants.getGrantPhone())) {
            phone.setEnabled(true);
            name.setEnabled(true);
            relationship.setEnabled(true);
            relationship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    window.setOnRelationshipWindowListener(new RelationshipWindow.OnRelationshipWindowListener() {
                        @Override
                        public void relationship(String title, String rel) {
                            relationship.setText(title);
                            grants.setRelation(rel);
                        }
                    });
                    window.showPopWindow(v);
                }
            });
        } else {
            phone.setEnabled(false);
            name.setEnabled(false);
            relationship.setEnabled(false);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(grants, grants.getGrantId() + "");
            }
        });
    }

    onDeleteAuthorizationListener listener;

    public void setOnDeleteAuthorizationListener(onDeleteAuthorizationListener listener) {
        this.listener = listener;
    }

    public List<HouseListBean.ListBean.Grants> getListData() {
        return dataList;
    }

    public interface onDeleteAuthorizationListener {
        void onDelete(HouseListBean.ListBean.Grants bean, String deleteIds);
    }

    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private final int charMaxNum = 30;
        private EditText mEditTextMsg;
        private String name;
        private HouseListBean.ListBean.Grants grants;

        public EditChangedListener(EditText mEditTextMsg, HouseListBean.ListBean.Grants grants, String name) {
            this.mEditTextMsg = mEditTextMsg;
            this.grants = grants;
            this.name = name;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            LogUtils.i("输入文本之前的状态");
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            LogUtils.i("输入文字中的状态，count是一次性输入字符数" + "还能输入" + (charMaxNum - s.length()) + "字符");
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (name.equals("name")) {
                grants.setGrantName(s.toString());
            } else {
                grants.setGrantPhone(s.toString());
            }
        }
    }

    public String getRelationship(String relation) {
        switch (relation) {
            case "2":
                return "家人";
            case "3":
                return "朋友";

            case "4":
                return "租赁户";

            case "1":
                return "业主";
        }
        return "";
    }
}
