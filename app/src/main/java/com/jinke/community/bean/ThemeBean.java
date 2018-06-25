package com.jinke.community.bean;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ThemeBean {


//    public static String mainhousekeeper;//main管家
//    public static String mainhousekeeper_pressed;//main管家按压
//    public static String mainhouselife;//main生活
//    public static String mainhouselife_pressed;//main生活按压
//    public static String mainhousecircle;//main圈子
//    public static String mainhousecircle_pressed;//main圈子按压
//    public static String mainhouseperson;//main我
//    public static String mainhouseperson_pressed;//main我按压
//    public static String mainhouse_bg;//main管家背景
//
//    public static String mainhouse_payment;//main管家——费用
//    public static String mainhouse_door;//main管家——门禁
//    public static String mainhouse_call;//main管家——呼叫
//    public static String mainhouse_break;//main管家——爆料
//
//    public static String mainperson_bg;//main我背景
//    public static String mainperson_balance;//main我预存
//    public static String mainperson_integrate;//main我积分
//    public static String mainperson_card;//main我卡卷
    private String name;
    private String value;

    public ThemeBean() {
    }

    public ThemeBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
