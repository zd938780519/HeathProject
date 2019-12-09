package com.yidao.project.heathproject.Beans;

public class AnswerBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : 请在进行体力活动前应咨询医生，告诉医生你回答了“是”的问题，并且告诉医生你希望参加的体力活动，听从医生的建议，参与一个安全而有益的运动计划。
     */

    private int code;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
