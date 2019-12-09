package com.yidao.project.heathproject.Beans;

import java.util.List;

public class PlanBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : {"planNum":5,"totalNum":5,"infoList":[{"isPlan":1,"id":1,"title":"活动准备"},{"isPlan":1,"id":3,"title":"症状"},{"isPlan":1,"id":2,"title":"病史"},{"isPlan":1,"id":4,"title":"其他健康问题"},{"isPlan":1,"id":5,"title":"心血管危险因素"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * planNum : 5
         * totalNum : 5
         * infoList : [{"isPlan":1,"id":1,"title":"活动准备"},{"isPlan":1,"id":3,"title":"症状"},{"isPlan":1,"id":2,"title":"病史"},{"isPlan":1,"id":4,"title":"其他健康问题"},{"isPlan":1,"id":5,"title":"心血管危险因素"}]
         */

        private int planNum;
        private int totalNum;
        private List<InfoListBean> infoList;

        public int getPlanNum() {
            return planNum;
        }

        public void setPlanNum(int planNum) {
            this.planNum = planNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<InfoListBean> getInfoList() {
            return infoList;
        }

        public void setInfoList(List<InfoListBean> infoList) {
            this.infoList = infoList;
        }

        public static class InfoListBean {
            /**
             * isPlan : 1
             * id : 1
             * title : 活动准备
             */

            private int isPlan;
            private int id;
            private String title;

            public int getIsPlan() {
                return isPlan;
            }

            public void setIsPlan(int isPlan) {
                this.isPlan = isPlan;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
