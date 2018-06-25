package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.NoteBean;

/**
 * Created by Administrator on 2018/3/23.
 */

public interface NoteView extends BaseView{
    void getNoteNext(NoteBean info);

    void onErrorMsg(String msg);

    void setNoteNext();
}
