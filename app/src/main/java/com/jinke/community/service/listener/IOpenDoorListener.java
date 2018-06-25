package com.jinke.community.service.listener;

import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.ListDateInfo;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorListener {
    void onListDateInfo(ListDateInfo info);

    void onDateTime(EmptyObjectBean bean);

    void onError(String code, String msg);

    void getQrCodeNext(String info);
}
