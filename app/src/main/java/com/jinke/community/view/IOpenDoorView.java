package com.jinke.community.view;

import java.sql.Time;
import java.util.Timer;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorView {
    /**
     * 更新主线程ui
     */
    void showMsg(String msg);

    /**
     * 更新主线程ui
     */
    void startDiffuseView();

    /**
     * 更新主线程ui
     */
    void finishActivity();

    /**
     * 更新主线程ui
     */
    void openFailHint(String content);

    /**
     * 更新主线程ui
     *
     * @param isTrue
     * @param timer
     */
    void onUpdateUI(Boolean isTrue, Timer timer);

    void getQrCodeNext(String info);
}
