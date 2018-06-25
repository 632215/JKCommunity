package com.jinke.community.bean;

/**
 * Created by liukun on 16/icon_cs3/5.
 */
public class UpdateUserInfoBean {

    private String errcode;
    private String errmsg;
    private PageInfo pageInfo;
    private HeadBean data;

    public HeadBean getData() {
        return data;
    }

    public void setData(HeadBean data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }


    public int getErrcode() {

        return Integer.parseInt(errcode);
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


    public class PageInfo {
        private int total;
        private int page;
        private int rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }
    }


    public class HeadBean {

        private String avatar;

        public HeadBean() {
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
