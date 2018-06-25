package com.jinke.community.service.listener;

import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.NoteBean;

/**
 * Created by Administrator on 2018/3/23.
 */

public interface NoteListener {
    void onErrorMsg(String code, String msg);

    void getNoteNext(NoteBean info);

    void setNoteNext(EmptyObjectBean info);
}
