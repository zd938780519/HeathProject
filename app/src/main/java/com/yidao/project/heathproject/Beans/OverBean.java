package com.yidao.project.heathproject.Beans;

public class OverBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : {"title":"注意事项","content":"每次运动之前要求做充分的准备活动；\n应根据动作的难度、幅度等，循序渐进、量力而行；\n运动中注意正确的呼吸方式和节奏；\n训练中如出现不适,应中止活动,并及时与医生取得联系。\n初次参加运动的人，要遵循循序增强，遂步推进的原则，运动的时间要从短到长，运动强度要从弱到强"}
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
         * title : 注意事项
         * content : 每次运动之前要求做充分的准备活动；
         应根据动作的难度、幅度等，循序渐进、量力而行；
         运动中注意正确的呼吸方式和节奏；
         训练中如出现不适,应中止活动,并及时与医生取得联系。
         初次参加运动的人，要遵循循序增强，遂步推进的原则，运动的时间要从短到长，运动强度要从弱到强
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
