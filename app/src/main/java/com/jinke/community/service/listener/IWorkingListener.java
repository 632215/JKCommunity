package com.jinke.community.service.listener;

import com.jinke.community.bean.WorkingSortBean;

/**
 * Created by root on 17-8-17.
 */

public interface IWorkingListener {
    void onWorkingSort(WorkingSortBean bean);
    void onUpdate(WorkingSortBean bean);
    void onError(String code,String msg);

}
