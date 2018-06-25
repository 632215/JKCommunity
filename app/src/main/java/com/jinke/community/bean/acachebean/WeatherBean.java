package com.jinke.community.bean.acachebean;

import java.io.Serializable;

/**
 * Created by root on 17-8-20.
 * 天气
 */

public class WeatherBean implements Serializable {


    /**
     * min : 30
     * icon_water_and_electricity : http://api-development.tq-service.com/tqUc/static/weatherIcon/Sunny_3x.png
     * max : 40
     * aqi : 41
     * pm25 : 11
     * qlty : 优
     * txt : 晴
     * city : 重庆
     */

    private String min;
    private String icon;
    private String max;
    private String aqi;
    private String pm25;
    private String qlty;
    private String txt;
    private String city;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
