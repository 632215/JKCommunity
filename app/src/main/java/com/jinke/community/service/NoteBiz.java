package com.jinke.community.service;

import com.jinke.community.presenter.NotePresenter;
import com.jinke.community.service.listener.NoteListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/23.
 */

public interface NoteBiz {
    void getNote(Map map, NoteListener listener);

    void setNote(Map map, NoteListener listener);

}
