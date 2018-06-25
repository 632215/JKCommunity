package com.jinke.community.bean;

import com.adtech.sys.util.Encrypt;
import com.jinke.community.utils.FileNameUtil;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/4.
 */

public class JiaLeBean {
//    cno	Long	小区编号	12346578
//    bno	String	楼栋编号	01
//    ano	String	单元编号	01
//    hno	String	房号	101
//    account	Long	账号	10001
//    appid	String	商户	由佳乐云分配的appid
//    nonce_str	String	随机数	建议Timestamp+随机字符串保证不重复
//    sign	String	签名	945d6e7c3c6663113ac745d3df247b8d
    private Long cno;
    private String bno;
    private String ano;
    private String hno;
    private Long account;
    private String appid;
    private String nonce_str;
    private String sign;

    public JiaLeBean() {
        nonce_str = (new Date().getTime() + FileNameUtil.getrandom());
        sign = (Encrypt.md5("Llpq1ts51D28i5SCTRCGis" + nonce_str));
        appid = "78967886";
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = Long.valueOf(cno);
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getHno() {
        return hno;
    }

    public void setHno(String hno) {
        this.hno = hno;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = Long.valueOf(account);
    }
}
