package com.jinke.community.view;

import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.KeepPropertyBean;

/**
 * Created by root on 17-8-11.
 */

public interface IBrokenDetailsView {
    /**
     * 爆料详情
     * @param bean
     */
    void onSuccess(BrokenDetailsBean bean);

    /**
     * 报事详情
     * @param bean
     */
    void onPropertyInfo(BrokenDetailsBean bean);

    void showMsg(String msg);

    void getKeeperDetailNext(KeepPropertyBean info);
}
