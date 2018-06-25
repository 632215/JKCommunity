package com.jinke.community.ui.toast;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OpenDoorDialog extends AlertDialog implements View.OnClickListener{
    private TextView title;
    private ImageView image;
    private RelativeLayout layoutResult;
    private Context mContext;

    public OpenDoorDialog(Context context) {
        super(context, R.style.OpenDoorDialog);
        this.mContext =context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_door);
        title = (TextView) findViewById(R.id.tx_open_success);
        image = (ImageView) findViewById(R.id.image_open_success);
        layoutResult = (RelativeLayout) findViewById(R.id.layout_open_result);
        layoutResult.setOnClickListener(this);
    }

    public void setTitle(String tx) {
        if (title != null) {
            title.setText(tx);
        }
    }

    @SuppressLint("ResourceType")
    public void setTitle(int images) {
        image.setImageDrawable(mContext.getResources().getDrawable(images));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_open_result:
                Log.i("32s","tx_open_success");
                dismiss();
                break;
        }
    }


    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
