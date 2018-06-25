package com.jinke.community.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.ui.toast.DeleteRelationWindow;
import com.jinke.community.ui.toast.RelationshipWindow;
import com.jinke.community.ui.widget.ContainsEmojiEditText;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/9/29.
 */

public class AuthorAdaptr extends BaseAdapter {
    RelationshipWindow window;
    DeleteRelationWindow deleteRelationWindow;
    private Context context;
    public List<HouseListBean.ListBean.Grants> dataList;

    public AuthorAdaptr(Context context, List<HouseListBean.ListBean.Grants> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setDataList(List<HouseListBean.ListBean.Grants> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_authorization_listview, parent, false);
        TextView delete = (TextView) convertView.findViewById(R.id.tx_authorization_delete);
        TextView edit = (TextView) convertView.findViewById(R.id.tx_authorization_edit);
        final EditText phone = (EditText) convertView.findViewById(R.id.ed_owner_phone);
        final ContainsEmojiEditText name = (ContainsEmojiEditText) convertView.findViewById(R.id.ed_owner_name);
        final TextView relationship = (TextView) convertView.findViewById(R.id.tx_relationship);

        final HouseListBean.ListBean.Grants grants = dataList.get(position);

        name.addTextChangedListener(new AuthorAdaptr.EditChangedListener(name, grants, "name"));
        phone.addTextChangedListener(new AuthorAdaptr.EditChangedListener(phone, grants, "phone"));

        phone.setText(StringUtil.isEmpty(grants.getGrantPhone()) ? "" : grants.getGrantPhone());
        name.setText(StringUtil.isEmpty(grants.getGrantName()) ? "" : grants.getGrantName());
        relationship.setText(StringUtils.isEmpty(grants.getRelation()) ? "请选择" : getRelationship(grants.getRelation()));
        phone.setEnabled(false);
        name.setEnabled(false);
        relationship.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(grants);
            }
        });

        if (StringUtils.isEmpty(grants.getGrantPhone())) {
            phone.setEnabled(true);
            name.setEnabled(true);
            relationship.setEnabled(true);
            relationship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (window == null) {
                        window = new RelationshipWindow(context);
                    }
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
                if (deleteRelationWindow == null) {
                    deleteRelationWindow = new DeleteRelationWindow(context);
                }
                deleteRelationWindow.setListener(new DeleteRelationWindow.OnSureListener() {
                    @Override
                    public void sureDelete() {
                        listener.onDelete(grants, grants.getGrantId() + "");
                    }
                });
                deleteRelationWindow.showPopWindow(v);
            }
        });

        return convertView;
    }

    AuthorAdaptr.OnAuthorizationListener listener;

    public void setOnDeleteAuthorizationListener(AuthorAdaptr.OnAuthorizationListener listener) {
        this.listener = listener;
    }

    public List<HouseListBean.ListBean.Grants> getListData() {
        return dataList;
    }

    public interface OnAuthorizationListener {
        void onDelete(HouseListBean.ListBean.Grants bean, String deleteIds);

        void onEditClick(HouseListBean.ListBean.Grants grants);
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
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
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
