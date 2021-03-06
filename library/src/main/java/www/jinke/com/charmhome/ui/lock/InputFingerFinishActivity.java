package www.jinke.com.charmhome.ui.lock;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import www.jinke.com.charmhome.Base.BaseActivity;
import www.jinke.com.charmhome.R;
import www.jinke.com.charmhome.bean.FingerListBean;
import www.jinke.com.charmhome.presenter.lock.EditFingerPresenter;
import www.jinke.com.charmhome.ui.dialog.LockBluetoothDialog;
import www.jinke.com.charmhome.ui.dialog.LogoutDialog;
import www.jinke.com.charmhome.view.lock.IEditFingerView;

/**
 * Created by root on 17-12-13.
 * 编辑指纹
 */

public class InputFingerFinishActivity extends BaseActivity implements View.OnClickListener,
        IEditFingerView, LogoutDialog.onLogoutListener, LockBluetoothDialog.onIKnowListener {
    private FingerListBean listBean;
    private EditText tx_item_finger_name;
    private TextView tx_item_lock_finger_delete;
    private EditFingerPresenter presenter;
    private boolean isOver;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_input_finger_finish;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.charmHome_lock_device_input_finger_title));
        setRightVisibility(getString(R.string.charmHome_lock_add_finish_btn));
        setBackVisibility(false);
        tx_item_lock_finger_delete.setVisibility(View.GONE);
        presenter = new EditFingerPresenter(this);
    }

    @Override
    protected void onBackView(View view) {
        super.onBackView(view);
        finish();
    }

    @Override
    protected void onRightView(View view) {
        super.onRightView(view);
        if (!StringUtils.isEmpty(tx_item_finger_name.getText().toString().trim()) && listBean != null) {
            presenter.getUpdateFingerName(listBean.getFingerId() + "",
                    listBean.getFingerType() + "", tx_item_finger_name.getText().toString().trim());
        } else {
            showToast("请输入指纹名称!");
        }
    }

    @Override
    protected void findViewById() {
        listBean = (FingerListBean) getIntent().getSerializableExtra("listBean");
        isOver = getIntent().getBooleanExtra("isOver", false);
        tx_item_finger_name = findViewById(R.id.tx_item_finger_name);
        tx_item_lock_finger_delete = findViewById(R.id.tx_item_lock_finger_delete);
        tx_item_lock_finger_delete.setOnClickListener(this);
        if (listBean != null) {
            tx_item_finger_name.setText(listBean.getFingerName());
            tx_item_finger_name.setSelection(listBean.getFingerName().length());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tx_item_lock_finger_delete) {
            LogoutDialog dialog = new LogoutDialog(this);
            dialog.setOnLogoutListener(this);
            dialog.setLogOutContent("是否删除该指纹");
            dialog.show();
        }
    }

    @Override
    public void showLoading(String msg) {
        showDialog(msg);
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    public void onUpdateSuccess(String s) {
        if (isOver) {
            Intent intent = new Intent(InputFingerFinishActivity.this, DevicePassActivity.class);
            intent.putExtra("isOver", true);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showToast("请点击完成按钮，保存退出!");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showMsg(String s) {
        showToast(s);
    }

    @Override
    public void logout() {
        LockBluetoothDialog dialog = new LockBluetoothDialog(this);
        dialog.setOnIKnowListener(this);
        dialog.show();
    }

    @Override
    public void IKowBack() {
        if (listBean != null) {
            presenter.getFingerDelete(listBean.getLockSeq(), listBean.getFingerId() + "", listBean.getFingerLockId(), listBean.getFingerType() + "");
        }
    }
}
