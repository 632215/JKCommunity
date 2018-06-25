package com.jinke.community.ui.toast.banner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jinke.community.R;
import com.jinke.community.config.AppConfig;
import com.jinke.community.ui.activity.base.LoginActivity;


/**
 * author：luck
 * project：AppWhenThePage
 * package：com.luck.app.page.when_page
 * email：893855882@qq.com
 * data：2017/2/22
 */
public class PageFragment extends Fragment {
    private View rootView;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int index = args.getInt("index");
        int layoutId = args.getInt("layoutId");
        int count = args.getInt("count");
        rootView = inflater.inflate(layoutId, null);
        imageView = (ImageView) rootView.findViewById(R.id.image);

        Glide.with(this.getActivity())
                .load(AppConfig.image[index])
                .asGif()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        // 滑动到最后一页有点击事件
        if (index == count - 1) {
//            rootView.findViewById(R.id.id_ok).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }

        return rootView;
    }
}
