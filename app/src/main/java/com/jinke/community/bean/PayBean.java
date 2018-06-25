package com.jinke.community.bean;

import java.io.Serializable;

/**
 * Created by root on 17-8-18.
 */

public class PayBean implements Serializable {

    /**
     * app_alipay : alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2017032306361494&amp;biz_content=%7B%22body%22%3A%22%E7%89%A9%E4%B8%9A%E7%BC%B4%E8%B4%B9%22%2C%22subject%22%3A+%22%E7%89%A9%E4%B8%9A%E7%BC%B4%E8%B4%B9%22%2C%22out_trade_no%22%3A+%22wx20170818204636905447%22%2C%22timeout_express%22%3A+%22%22%2C%22total_amount%22%3A+%220.03%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=http%3A%2F%2Fdev.tq-service.com%2Fjkpros%2FApi%2FPayCenter%2FpaymentAliPayNotify&amp;sign_type=RSA2&amp;timestamp=2017-08-18+20%3A46%3A36&amp;version=1.0&amp;sign=Dg8Tgr3KTVkGP4rLcBhsIhyA5Sqsys6jaDHGX8%2F7mQvVBPhxiiBG43E0CBRN6fvfzo%2BxZnQt3txT0b2ECdCekfNLNkSjajlXkuISFAra55rvVtlKirS4RfBSFdUuZoAsLlmncAumX7d1BBB70xSd%2BUeolKyk5xuAxCvnksfJ2f3oYFnUHiyQw9AGhqC56pqf%2BYMvCw%2BWXUBlo0tC3bQtHjjNBl6ukr0tzn%2Fq9mOXytqV8FoQF%2FLkQlUS1LdjMw75reh%2F%2BJ2KyVNcI8gMvB%2B90%2BIlPHDitJul9FSUyGoNAzLrJMc%2FsCS5Q6JUKx%2FBEVFbkgtUGSTfyBGYGk53EUnt5w%3D%3D
     * app_wxpay :
     * out_trade_no : wx20170818204636905447
     */

    private String app_alipay;
    private String app_wxpay;
    private String out_trade_no;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApp_alipay() {
        return app_alipay;
    }

    public void setApp_alipay(String app_alipay) {
        this.app_alipay = app_alipay;
    }

    public String getApp_wxpay() {
        return app_wxpay;
    }

    public void setApp_wxpay(String app_wxpay) {
        this.app_wxpay = app_wxpay;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
