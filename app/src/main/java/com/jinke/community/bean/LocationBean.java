package com.jinke.community.bean;

/**
 * 获取高地图定位信息
 * Created by root on 17-7-27.
 */

public class LocationBean {
    /**
     * 获取当前定位结果来源，如网络定位结果，详见定位类型表
     */
    private String LocationType;
    /**
     * 获取纬度
     */
    private String Latitude;
    /**
     * 获取经度
     */
    private String Longitude;
    /**
     * 获取精度信息
     */
    private String Accuracy;
    /**
     * 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
     */
    private String Address;
    /**
     * 国家信息
     */
    private String Country;
    /**
     * 省信息
     */
    private String Province;
    /**
     * 城市信息
     */
    private String City;
    /**
     * 城区信息
     */
    private String District;
    /**
     * 街道信息
     */
    private String Street;
    /**
     * 街道门牌号信息
     */
    private String StreetNum;
    /**
     * 城市编码
     */
    private String CityCode;
    /**
     * 地区编码
     */
    private String AdCode;
    /**
     * 获取当前定位点的AOI信息
     */
    private String AoiName;
    /**
     * 获取当前室内定位的建筑物Id
     */
    private String BuildingId;
    /**
     * 获取当前室内定位的楼层
     */
    private String Floor;
    /**
     * 获取GPS的当前状态
     */
    private String GpsStatus;
    /**
     * 获取定位时间
     */
    private String LocationTime;

    /**
     * 错误码
     */
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getLocationType() {
        return LocationType;
    }

    public void setLocationType(String locationType) {
        LocationType = locationType;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getStreetNum() {
        return StreetNum;
    }

    public void setStreetNum(String streetNum) {
        StreetNum = streetNum;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public String getAdCode() {
        return AdCode;
    }

    public void setAdCode(String adCode) {
        AdCode = adCode;
    }

    public String getAoiName() {
        return AoiName;
    }

    public void setAoiName(String aoiName) {
        AoiName = aoiName;
    }

    public String getBuildingId() {
        return BuildingId;
    }

    public void setBuildingId(String buildingId) {
        BuildingId = buildingId;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getGpsStatus() {
        return GpsStatus;
    }

    public void setGpsStatus(String gpsStatus) {
        GpsStatus = gpsStatus;
    }

    public String getLocationTime() {
        return LocationTime;
    }

    public void setLocationTime(String locationTime) {
        LocationTime = locationTime;
    }
}
