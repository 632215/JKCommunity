package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */

public class MsgBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 1
         * message : 你有包裹
         * type : POSTMAN_POST
         * isRead : 1
         * title : 取件通知
         * infoId : 915251648592081082812008
         * extras : [{"id":"255855ec-d9ac-412d-ae1b-d637023e6ad6","gui_id":"1082812","gui_name":"重庆天智慧启科技有限公司速递易C86","box_id":"37","box_type":"L","order_code":"915251648592081082812008","express_name":"圆通速递","postman":"罗先生","location":"重庆天智慧启科技有限公司 大门东侧C86","charge_begin_time":"1524844862","mail_no":"221305402014","getter_phone":"19923015971","post_phone":"19923015971","open_code":"25945E","extra":""},{"id":"961c683c-40a7-4193-a1e6-dcd872fe8ec5","gui_id":"1082812","gui_name":"重庆天智慧启科技有限公司速递易C86","box_id":"37","box_type":"S","order_code":"915251648592081082812008","express_name":"圆通速递","postman":"罗先生","location":"重庆天智慧启科技有限公司 大门东侧C86","charge_begin_time":"1524844862","mail_no":"221305402014","getter_phone":"19923015971","post_phone":"18323329457","open_code":"U4M357","extra":""}]
         * createTime : 1528353488
         */

        private int id;
        private String message;
        private String type;
        private String isRead;
        private String title;
        private String infoId;
        private int createTime;
        private List<ExtrasBean> extras;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfoId() {
            return infoId;
        }

        public void setInfoId(String infoId) {
            this.infoId = infoId;
        }

        public String getCreateTime() {
            return String.valueOf(createTime);
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public List<ExtrasBean> getExtras() {
            return extras;
        }

        public void setExtras(List<ExtrasBean> extras) {
            this.extras = extras;
        }

        public static class ExtrasBean implements Serializable {
            /**
             * id : 255855ec-d9ac-412d-ae1b-d637023e6ad6
             * gui_id : 1082812
             * gui_name : 重庆天智慧启科技有限公司速递易C86
             * box_id : 37
             * box_type : L
             * order_code : 915251648592081082812008
             * express_name : 圆通速递
             * postman : 罗先生
             * location : 重庆天智慧启科技有限公司 大门东侧C86
             * charge_begin_time : 1524844862
             * mail_no : 221305402014
             * getter_phone : 19923015971
             * post_phone : 19923015971
             * open_code : 25945E
             * extra :
             */

            private String id;
            private String gui_id;
            private String gui_name;
            private String box_id;
            private String box_type;
            private String order_code;
            private String express_name;
            private String postman;
            private String location;
            private String charge_begin_time;
            private String mail_no;
            private String getter_phone;
            private String post_phone;
            private String open_code;
            private String extra;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGui_id() {
                return gui_id;
            }

            public void setGui_id(String gui_id) {
                this.gui_id = gui_id;
            }

            public String getGui_name() {
                return gui_name;
            }

            public void setGui_name(String gui_name) {
                this.gui_name = gui_name;
            }

            public String getBox_id() {
                return box_id;
            }

            public void setBox_id(String box_id) {
                this.box_id = box_id;
            }

            public String getBox_type() {
                return box_type;
            }

            public void setBox_type(String box_type) {
                this.box_type = box_type;
            }

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }

            public String getExpress_name() {
                return express_name;
            }

            public void setExpress_name(String express_name) {
                this.express_name = express_name;
            }

            public String getPostman() {
                return postman;
            }

            public void setPostman(String postman) {
                this.postman = postman;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getCharge_begin_time() {
                return charge_begin_time;
            }

            public void setCharge_begin_time(String charge_begin_time) {
                this.charge_begin_time = charge_begin_time;
            }

            public String getMail_no() {
                return mail_no;
            }

            public void setMail_no(String mail_no) {
                this.mail_no = mail_no;
            }

            public String getGetter_phone() {
                return getter_phone;
            }

            public void setGetter_phone(String getter_phone) {
                this.getter_phone = getter_phone;
            }

            public String getPost_phone() {
                return post_phone;
            }

            public void setPost_phone(String post_phone) {
                this.post_phone = post_phone;
            }

            public String getOpen_code() {
                return open_code;
            }

            public void setOpen_code(String open_code) {
                this.open_code = open_code;
            }

            public String getExtra() {
                return extra;
            }

            public void setExtra(String extra) {
                this.extra = extra;
            }
        }
    }
}
