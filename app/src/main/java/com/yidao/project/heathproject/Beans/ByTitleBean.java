package com.yidao.project.heathproject.Beans;

import java.util.List;

public class ByTitleBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : [{"isPlan":1,"id":1,"title":"身高、体重","content":"身高：测试时,受试者赤脚、呈立正姿势站在身高计的底板上(躯干挺直,上肢自然下垂,脚跟并拢,脚尖分开约60℃,脚跟、骶骨部及两肩胛间与身高计的立柱接触,头部正直,两眼平视前方,耳屏上缘与眼眶下缘最低点呈水平。记录以厘米为单位,保留小数点后1位。\\n体重：测试时，受试者自然站在体重秤中央，站稳后，读取数据，记录千克为单位，保留小数点后1位。"}]
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
         * isPlan : 1
         * id : 1
         * title : 身高、体重
         * content : 身高：测试时,受试者赤脚、呈立正姿势站在身高计的底板上(躯干挺直,上肢自然下垂,脚跟并拢,脚尖分开约60℃,脚跟、骶骨部及两肩胛间与身高计的立柱接触,头部正直,两眼平视前方,耳屏上缘与眼眶下缘最低点呈水平。记录以厘米为单位,保留小数点后1位。\n体重：测试时，受试者自然站在体重秤中央，站稳后，读取数据，记录千克为单位，保留小数点后1位。
         */

        private int isPlan;
        private int id;
        private String title;
        private String content;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
