package com.jinke.community.bean;

/**
 * Created by root on 17-8-21.
 */

public class GuestCountBean {
    private String name;
    private boolean isshow = false;
    private String id;

    public GuestCountBean(String name, boolean isshow, String id) {
        this.name = name;
        this.isshow = isshow;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }

}
