package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-6.
 */

public class TestBean {


    /**
     * body : {"listDatas":[{"OWNER_ID":"9af92a7b-0c0b-48af-8bab-be0db9bbe8fb","OWNER_NAME":"彭志荃","SEX":"男","BIRTH_DATE":"1900/1/1 0:00:00","NATIONALS":"","MARRIAGE":"","EDUCATION":"","CERTIFICATE_TYPE":"","CERTIFICATE_NO":"","CUSTOMER_LEVEL":"普通客户","CONTACT":"18580068622","OUT_FLAG":"1","HOUSE_ID":"f8f56f33-3a4c-49ca-ad1e-ede7754cdb0f","HOUSE_NO":"科技中心8-1-133","BUILD_NO":"科技中心楼栋"}],"total":1}
     * response : {"code":200,"msg":"成功"}
     */

    private BodyBean body;
    private ResponseBean response;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class BodyBean {
        /**
         * listDatas : [{"OWNER_ID":"9af92a7b-0c0b-48af-8bab-be0db9bbe8fb","OWNER_NAME":"彭志荃","SEX":"男","BIRTH_DATE":"1900/1/1 0:00:00","NATIONALS":"","MARRIAGE":"","EDUCATION":"","CERTIFICATE_TYPE":"","CERTIFICATE_NO":"","CUSTOMER_LEVEL":"普通客户","CONTACT":"18580068622","OUT_FLAG":"1","HOUSE_ID":"f8f56f33-3a4c-49ca-ad1e-ede7754cdb0f","HOUSE_NO":"科技中心8-1-133","BUILD_NO":"科技中心楼栋"}]
         * total : 1
         */

        private int total;
        private List<ListDatasBean> listDatas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListDatasBean> getListDatas() {
            return listDatas;
        }

        public void setListDatas(List<ListDatasBean> listDatas) {
            this.listDatas = listDatas;
        }

        public static class ListDatasBean {
            /**
             * OWNER_ID : 9af92a7b-0c0b-48af-8bab-be0db9bbe8fb
             * OWNER_NAME : 彭志荃
             * SEX : 男
             * BIRTH_DATE : 1900/1/1 0:00:00
             * NATIONALS :
             * MARRIAGE :
             * EDUCATION :
             * CERTIFICATE_TYPE :
             * CERTIFICATE_NO :
             * CUSTOMER_LEVEL : 普通客户
             * CONTACT : 18580068622
             * OUT_FLAG : 1
             * HOUSE_ID : f8f56f33-3a4c-49ca-ad1e-ede7754cdb0f
             * HOUSE_NO : 科技中心8-1-133
             * BUILD_NO : 科技中心楼栋
             */

            private String OWNER_ID;
            private String OWNER_NAME;
            private String SEX;
            private String BIRTH_DATE;
            private String NATIONALS;
            private String MARRIAGE;
            private String EDUCATION;
            private String CERTIFICATE_TYPE;
            private String CERTIFICATE_NO;
            private String CUSTOMER_LEVEL;
            private String CONTACT;
            private String OUT_FLAG;
            private String HOUSE_ID;
            private String HOUSE_NO;
            private String BUILD_NO;

            public String getOWNER_ID() {
                return OWNER_ID;
            }

            public void setOWNER_ID(String OWNER_ID) {
                this.OWNER_ID = OWNER_ID;
            }

            public String getOWNER_NAME() {
                return OWNER_NAME;
            }

            public void setOWNER_NAME(String OWNER_NAME) {
                this.OWNER_NAME = OWNER_NAME;
            }

            public String getSEX() {
                return SEX;
            }

            public void setSEX(String SEX) {
                this.SEX = SEX;
            }

            public String getBIRTH_DATE() {
                return BIRTH_DATE;
            }

            public void setBIRTH_DATE(String BIRTH_DATE) {
                this.BIRTH_DATE = BIRTH_DATE;
            }

            public String getNATIONALS() {
                return NATIONALS;
            }

            public void setNATIONALS(String NATIONALS) {
                this.NATIONALS = NATIONALS;
            }

            public String getMARRIAGE() {
                return MARRIAGE;
            }

            public void setMARRIAGE(String MARRIAGE) {
                this.MARRIAGE = MARRIAGE;
            }

            public String getEDUCATION() {
                return EDUCATION;
            }

            public void setEDUCATION(String EDUCATION) {
                this.EDUCATION = EDUCATION;
            }

            public String getCERTIFICATE_TYPE() {
                return CERTIFICATE_TYPE;
            }

            public void setCERTIFICATE_TYPE(String CERTIFICATE_TYPE) {
                this.CERTIFICATE_TYPE = CERTIFICATE_TYPE;
            }

            public String getCERTIFICATE_NO() {
                return CERTIFICATE_NO;
            }

            public void setCERTIFICATE_NO(String CERTIFICATE_NO) {
                this.CERTIFICATE_NO = CERTIFICATE_NO;
            }

            public String getCUSTOMER_LEVEL() {
                return CUSTOMER_LEVEL;
            }

            public void setCUSTOMER_LEVEL(String CUSTOMER_LEVEL) {
                this.CUSTOMER_LEVEL = CUSTOMER_LEVEL;
            }

            public String getCONTACT() {
                return CONTACT;
            }

            public void setCONTACT(String CONTACT) {
                this.CONTACT = CONTACT;
            }

            public String getOUT_FLAG() {
                return OUT_FLAG;
            }

            public void setOUT_FLAG(String OUT_FLAG) {
                this.OUT_FLAG = OUT_FLAG;
            }

            public String getHOUSE_ID() {
                return HOUSE_ID;
            }

            public void setHOUSE_ID(String HOUSE_ID) {
                this.HOUSE_ID = HOUSE_ID;
            }

            public String getHOUSE_NO() {
                return HOUSE_NO;
            }

            public void setHOUSE_NO(String HOUSE_NO) {
                this.HOUSE_NO = HOUSE_NO;
            }

            public String getBUILD_NO() {
                return BUILD_NO;
            }

            public void setBUILD_NO(String BUILD_NO) {
                this.BUILD_NO = BUILD_NO;
            }
        }
    }

    public static class ResponseBean {
        /**
         * code : 200
         * msg : 成功
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
