package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/10/18.
 */

public class LicenseBean {
    String text;
    boolean isSelected;

    public LicenseBean(String text, boolean isSelected) {
        this.text = text;
        this.isSelected = isSelected;
    }

    public LicenseBean() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
