package com.jinke.community.service.listener;

import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.KeepPropertyBean;

/**
 * Created by root on 17-8-11.
 */

public interface IBrokenDetailsListener {
    void onSuccess(BrokenDetailsBean bean);

    void onError(String code, String msg);

    void onPropertyInfo(BrokenDetailsBean bean);

    void getKeeperDetailNext(KeepPropertyBean info);
}
