package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/11/28.
 * 代扣开通返回bean
 */

public class WithHoldOpenBean {
    /**
     * app_id :
     * app_alipayDaiKou : https://mapi.alipay.com/gateway.do?_input_charset=utf-8&access_info=%7B%22channel%22%3A%22ALIPAYAPP%22%7D&product_code=GENERAL_WITHHOLDING_P&scene=INDUSTRY%7CAPPSTORE&service=alipay.dut.customer.agreement.page.sign&sign=b11499fe60161591b8fdc0204b6a0e67&sign_type=MD5
     */

    private String app_id;
    private String app_alipayDaiKou;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_alipayDaiKou() {
        return app_alipayDaiKou;
    }

    public void setApp_alipayDaiKou(String app_alipayDaiKou) {
        this.app_alipayDaiKou = app_alipayDaiKou;
    }
}
