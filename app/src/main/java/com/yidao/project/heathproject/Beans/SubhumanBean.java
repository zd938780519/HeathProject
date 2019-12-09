package com.yidao.project.heathproject.Beans;

import java.util.List;

public class SubhumanBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : {"planNum":6,"totalNum":6,"infoList":[{"image":"/st.png","isPlan":1,"show":"身高、体重","id":1,"title":"身体形态"},{"image":"/xf.png","isPlan":1,"show":"12min跑、哈弗台阶实验","id":2,"title":"心肺机能"},{"image":"/ph.png","isPlan":1,"show":"闭眼单脚站立","id":3,"title":"平衡能力"},{"image":"/nl.png","isPlan":1,"show":"仰卧起坐（女）、俯卧撑（男）","id":4,"title":"肌肉耐力"},{"image":"/rr.png","isPlan":1,"show":"坐位体前屈","id":5,"title":"柔韧性"},{"image":"/ll.png","isPlan":1,"show":"握力","id":6,"title":"肌肉力量"}]}
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
         * planNum : 6
         * totalNum : 6
         * infoList : [{"image":"/st.png","isPlan":1,"show":"身高、体重","id":1,"title":"身体形态"},{"image":"/xf.png","isPlan":1,"show":"12min跑、哈弗台阶实验","id":2,"title":"心肺机能"},{"image":"/ph.png","isPlan":1,"show":"闭眼单脚站立","id":3,"title":"平衡能力"},{"image":"/nl.png","isPlan":1,"show":"仰卧起坐（女）、俯卧撑（男）","id":4,"title":"肌肉耐力"},{"image":"/rr.png","isPlan":1,"show":"坐位体前屈","id":5,"title":"柔韧性"},{"image":"/ll.png","isPlan":1,"show":"握力","id":6,"title":"肌肉力量"}]
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
             * image : /st.png
             * isPlan : 1
             * show : 身高、体重
             * id : 1
             * title : 身体形态
             */

            private String image;
            private int isPlan;
            private String show;
            private int id;
            private String title;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getIsPlan() {
                return isPlan;
            }

            public void setIsPlan(int isPlan) {
                this.isPlan = isPlan;
            }

            public String getShow() {
                return show;
            }

            public void setShow(String show) {
                this.show = show;
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
