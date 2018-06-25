package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class ParkInfoBean implements Serializable{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * carType_No : 3652
         * park_Name : 十年城
         * parking_Key : 39adcf0111f1403c80f0c50038dc0092
         * carSpace_No : A00200
         * carType_Name : 月租车A
         * carTypeChargRules_Money : 350
         * carTypeChargRules_MthNum : 1
         * carSpace_BeginTime : 2017-10-21
         * carSpace_EndTime : 2017-11-21
         * carSpace_IusseTime : 2017-10-20 10:57:28
         * carSpace_CancelTime :
         * carSpace_Status : 1
         * carSpace_LastIssue : 1
         * carSpace_CarNum : 1
         * carSpace_UserName : Test002
         * carSpace_Phone : 13888888888
         * carSpace_HomeAddress : 骏华002
         */

        private String carType_No;
        private String park_Name;
        private String parking_Key;
        private String carSpace_No;
        private String carType_Name;
        private String carTypeChargRules_Money;
        private String carTypeChargRules_MthNum;
        private String carSpace_BeginTime;
        private String carSpace_EndTime;
        private String carSpace_IusseTime;
        private String carSpace_CancelTime;
        private String carSpace_Status;
        private String carSpace_LastIssue;
        private String carSpace_CarNum;
        private String carSpace_UserName;
        private String carSpace_Phone;
        private String carSpace_HomeAddress;

        public String getCarType_No() {
            return carType_No;
        }

        public void setCarType_No(String carType_No) {
            this.carType_No = carType_No;
        }

        public String getPark_Name() {
            return park_Name;
        }

        public void setPark_Name(String park_Name) {
            this.park_Name = park_Name;
        }

        public String getParking_Key() {
            return parking_Key;
        }

        public void setParking_Key(String parking_Key) {
            this.parking_Key = parking_Key;
        }

        public String getCarSpace_No() {
            return carSpace_No;
        }

        public void setCarSpace_No(String carSpace_No) {
            this.carSpace_No = carSpace_No;
        }

        public String getCarType_Name() {
            return carType_Name;
        }

        public void setCarType_Name(String carType_Name) {
            this.carType_Name = carType_Name;
        }

        public String getCarTypeChargRules_Money() {
            return carTypeChargRules_Money;
        }

        public void setCarTypeChargRules_Money(String carTypeChargRules_Money) {
            this.carTypeChargRules_Money = carTypeChargRules_Money;
        }

        public String getCarTypeChargRules_MthNum() {
            return carTypeChargRules_MthNum;
        }

        public void setCarTypeChargRules_MthNum(String carTypeChargRules_MthNum) {
            this.carTypeChargRules_MthNum = carTypeChargRules_MthNum;
        }

        public String getCarSpace_BeginTime() {
            return carSpace_BeginTime;
        }

        public void setCarSpace_BeginTime(String carSpace_BeginTime) {
            this.carSpace_BeginTime = carSpace_BeginTime;
        }

        public String getCarSpace_EndTime() {
            return carSpace_EndTime;
        }

        public void setCarSpace_EndTime(String carSpace_EndTime) {
            this.carSpace_EndTime = carSpace_EndTime;
        }

        public String getCarSpace_IusseTime() {
            return carSpace_IusseTime;
        }

        public void setCarSpace_IusseTime(String carSpace_IusseTime) {
            this.carSpace_IusseTime = carSpace_IusseTime;
        }

        public String getCarSpace_CancelTime() {
            return carSpace_CancelTime;
        }

        public void setCarSpace_CancelTime(String carSpace_CancelTime) {
            this.carSpace_CancelTime = carSpace_CancelTime;
        }

        public String getCarSpace_Status() {
            return carSpace_Status;
        }

        public void setCarSpace_Status(String carSpace_Status) {
            this.carSpace_Status = carSpace_Status;
        }

        public String getCarSpace_LastIssue() {
            return carSpace_LastIssue;
        }

        public void setCarSpace_LastIssue(String carSpace_LastIssue) {
            this.carSpace_LastIssue = carSpace_LastIssue;
        }

        public String getCarSpace_CarNum() {
            return carSpace_CarNum;
        }

        public void setCarSpace_CarNum(String carSpace_CarNum) {
            this.carSpace_CarNum = carSpace_CarNum;
        }

        public String getCarSpace_UserName() {
            return carSpace_UserName;
        }

        public void setCarSpace_UserName(String carSpace_UserName) {
            this.carSpace_UserName = carSpace_UserName;
        }

        public String getCarSpace_Phone() {
            return carSpace_Phone;
        }

        public void setCarSpace_Phone(String carSpace_Phone) {
            this.carSpace_Phone = carSpace_Phone;
        }

        public String getCarSpace_HomeAddress() {
            return carSpace_HomeAddress;
        }

        public void setCarSpace_HomeAddress(String carSpace_HomeAddress) {
            this.carSpace_HomeAddress = carSpace_HomeAddress;
        }
    }
}
