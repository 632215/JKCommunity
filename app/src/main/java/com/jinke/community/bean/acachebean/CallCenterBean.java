package com.jinke.community.bean.acachebean;

import java.io.Serializable;

/**
 * Created by root on 17-8-14.
 * 呼叫中心
 */

public class CallCenterBean implements Serializable{


    /**
     * servicePhone : 400-846-1818
     * keeperName : 老万
     * keeperPhone : 15896548546
     * isKeeper : 1
     */

    private String servicePhone;
    private String keeperName;
    private String keeperPhone;
    private int isKeeper;

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getKeeperName() {
        return keeperName;
    }

    public void setKeeperName(String keeperName) {
        this.keeperName = keeperName;
    }

    public String getKeeperPhone() {
        return keeperPhone;
    }

    public void setKeeperPhone(String keeperPhone) {
        this.keeperPhone = keeperPhone;
    }

    public int getIsKeeper() {
        return isKeeper;
    }

    public void setIsKeeper(int isKeeper) {
        this.isKeeper = isKeeper;
    }
}
