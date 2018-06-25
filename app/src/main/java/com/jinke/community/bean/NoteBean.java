package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class NoteBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * label_name : 教育
         * label_code : edu
         * isMark : 0
         */

        private String label_name;
        private String label_code;
        private int isMark;

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        public String getLabel_code() {
            return label_code;
        }

        public void setLabel_code(String label_code) {
            this.label_code = label_code;
        }

        public int getIsMark() {
            return isMark;
        }

        public void setIsMark(int isMark) {
            this.isMark = isMark;
        }
    }
}
