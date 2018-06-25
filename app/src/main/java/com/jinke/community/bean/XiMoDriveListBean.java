package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-21.
 */

public class XiMoDriveListBean {

    /**
     * listDate : [{"MAC_Addr":"df:21:09:90:e8:64","MAC_AddrIos":"df:21:09:90:e8:64","deviceId":"551016677","deviceName":"TQ-hug","deviceNum":"0160491620","deviceSecret":"3cbd5cdce6d34fc79b03f3db7efe7606000000000000000000000000000000001000","deviceType":"1","manufacturerId":"1","ownerPhone":"13667629670"}]
     * total : 0
     */

    private int total;
    private List<ListDateBean> listDate;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListDateBean> getListDate() {
        return listDate;
    }

    public void setListDate(List<ListDateBean> listDate) {
        this.listDate = listDate;
    }

    public static class ListDateBean {
        /**
         * MAC_Addr : df:21:09:90:e8:64
         * MAC_AddrIos : df:21:09:90:e8:64
         * deviceId : 551016677
         * deviceName : TQ-hug
         * deviceNum : 0160491620
         * deviceSecret : 3cbd5cdce6d34fc79b03f3db7efe7606000000000000000000000000000000001000
         * deviceType : 1
         * manufacturerId : 1
         * ownerPhone : 13667629670
         */

        private String MAC_Addr;
        private String MAC_AddrIos;
        private String deviceId;
        private String deviceName;
        private String deviceNum;
        private String deviceSecret;
        private String deviceType;
        private String manufacturerId;
        private String ownerPhone;

        public String getMAC_Addr() {
            return MAC_Addr;
        }

        public void setMAC_Addr(String MAC_Addr) {
            this.MAC_Addr = MAC_Addr;
        }

        public String getMAC_AddrIos() {
            return MAC_AddrIos;
        }

        public void setMAC_AddrIos(String MAC_AddrIos) {
            this.MAC_AddrIos = MAC_AddrIos;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceNum() {
            return deviceNum;
        }

        public void setDeviceNum(String deviceNum) {
            this.deviceNum = deviceNum;
        }

        public String getDeviceSecret() {
            return deviceSecret;
        }

        public void setDeviceSecret(String deviceSecret) {
            this.deviceSecret = deviceSecret;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getManufacturerId() {
            return manufacturerId;
        }

        public void setManufacturerId(String manufacturerId) {
            this.manufacturerId = manufacturerId;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }
    }
}
