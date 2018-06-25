package com.jinke.community.bean;

/**
 * Created by root on 17-8-15.
 */

public class PostItListBean {


    /**
     * data : {"community_name":"深圳·金科智慧城","address":"广东省深圳南山区","cityCode":"","house_id":"e87bad81-7a48-4a1c-9d57-b19a8e1ae8a7","owners":[{"phone":"18315058038","ownerName":"万德应"}],"house_name":"科技中心8-1-126","community_id":"38e70023-9bb1-4874-855f-6f46ac61fff8","area_num":"105.0000"}
     * errcode : 200
     * errmsg : 操作成功
     * pageInfo : {"page":0,"rows":0,"total":0}
     */

    private PropertyListBean data;
    private int errcode;
    private String errmsg;
    private BaseDefaultHouseBean.PageInfoBean pageInfo;

    public PropertyListBean getData() {
        return data;
    }

    public void setData(PropertyListBean data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public BaseDefaultHouseBean.PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(BaseDefaultHouseBean.PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }


    public static class PageInfoBean {
        /**
         * page : 0
         * rows : 0
         * total : 0
         */

        private int page;
        private int rows;
        private int total;

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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
