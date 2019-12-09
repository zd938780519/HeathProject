package com.yidao.project.heathproject.Beans;

import java.util.List;

public class QByTitleBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : [{"id":6,"parent_id":1,"content":"医生是否曾经告诉过你患有心脏病并且只能参加医生推荐的体力活动？","type":1,"status":0},{"id":7,"parent_id":1,"content":"当你参加体力活动时，是否感觉胸痛？","type":1,"status":0},{"id":8,"parent_id":1,"content":"自上个月以来，你是否在没有参加体力活动时发生过胸痛？","type":1,"status":0},{"id":9,"parent_id":1,"content":"你是否因头晕跌倒或曾失去知觉？","type":1,"status":0},{"id":10,"parent_id":1,"content":"你是否有因体力活动变化而加重的骨或者关节疾病（如腰背痛、膝关节或髋部）？","type":1,"status":0},{"id":11,"parent_id":1,"content":"最近医生是否因为你的血压或心脏问题给你开药？（如片剂或水剂）","type":1,"status":0},{"id":12,"parent_id":1,"content":"你是否知道一些你不能进行体力活动的其他原因？","type":1,"status":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * parent_id : 1
         * content : 医生是否曾经告诉过你患有心脏病并且只能参加医生推荐的体力活动？
         * type : 1
         * status : 0
         */

        private int id;
        private int parent_id;
        private String content;
        private int type;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
