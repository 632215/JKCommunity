package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/10/23.
 */

public class PassCodeBean {

    /**
     * result : {"sdkPasscode":"aaa693546e734a01a53b469f5e746ad6"}
     * status : 1
     * msg : 成功
     */

    private ResultBean result;
    private int status;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean {
        /**
         * sdkPasscode : aaa693546e734a01a53b469f5e746ad6
         */

        private String sdkPasscode;

        public String getSdkPasscode() {
            return sdkPasscode;
        }

        public void setSdkPasscode(String sdkPasscode) {
            this.sdkPasscode = sdkPasscode;
        }
    }
}
