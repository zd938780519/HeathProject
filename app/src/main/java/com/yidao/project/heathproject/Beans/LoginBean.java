package com.yidao.project.heathproject.Beans;

public class LoginBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : {"id":10,"phone":"18555211785","nickname":"叽叽叽叽","avatar":"/logo.png","type":0,"age":28,"sex":0,"account_state":0,"status":0}
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
         * id : 10
         * phone : 18555211785
         * nickname : 叽叽叽叽
         * avatar : /logo.png
         * type : 0
         * age : 28
         * sex : 0
         * account_state : 0
         * status : 0
         */

        private int id;
        private String phone;
        private String nickname;
        private String avatar;
        private int type;
        private int age;
        private int sex;
        private int account_state;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAccount_state() {
            return account_state;
        }

        public void setAccount_state(int account_state) {
            this.account_state = account_state;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
