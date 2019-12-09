package com.yidao.project.heathproject.Beans;

import java.util.List;

public class ActiveBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : [{"title":"活动准备","value":[{"Q":"医生是否曾经告诉过你患有心脏病并且只能参加医生推荐的体力活动？","A":0},{"Q":"当你参加体力活动时，是否感觉胸痛？","A":0},{"Q":"自上个月以来，你是否在没有参加体力活动时发生过胸痛？","A":0},{"Q":"你是否因头晕跌倒或曾失去知觉？","A":0},{"Q":"你是否有因体力活动变化而加重的骨或者关节疾病（如腰背痛、膝关节或髋部）？","A":0},{"Q":"最近医生是否因为你的血压或心脏问题给你开药？（如片剂或水剂）","A":0},{"Q":"你是否知道一些你不能进行体力活动的其他原因？","A":0}]},{"title":"病史","value":[{"Q":"一次心脏病发作","A":0},{"Q":"心脏手术","A":0}]}]
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
         * title : 活动准备
         * value : [{"Q":"医生是否曾经告诉过你患有心脏病并且只能参加医生推荐的体力活动？","A":0},{"Q":"当你参加体力活动时，是否感觉胸痛？","A":0},{"Q":"自上个月以来，你是否在没有参加体力活动时发生过胸痛？","A":0},{"Q":"你是否因头晕跌倒或曾失去知觉？","A":0},{"Q":"你是否有因体力活动变化而加重的骨或者关节疾病（如腰背痛、膝关节或髋部）？","A":0},{"Q":"最近医生是否因为你的血压或心脏问题给你开药？（如片剂或水剂）","A":0},{"Q":"你是否知道一些你不能进行体力活动的其他原因？","A":0}]
         */

        private String title;
        private List<ValueBean> value;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * Q : 医生是否曾经告诉过你患有心脏病并且只能参加医生推荐的体力活动？
             * A : 0
             */

            private String Q;
            private int A;

            public String getQ() {
                return Q;
            }

            public void setQ(String Q) {
                this.Q = Q;
            }

            public int getA() {
                return A;
            }

            public void setA(int A) {
                this.A = A;
            }
        }
    }
}
