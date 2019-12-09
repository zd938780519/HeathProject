package com.yidao.project.heathproject.Beans;

import java.util.List;

public class CeShiPlanBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : [{"title":"身体形态","list":[{"left":"身高（m）","right":"未完成"},{"left":"体重（kg）","right":"未完成"}]},{"title":"心肺机能","list":[{"left":"12min跑（m）","right":"未完成"},{"left":"哈佛台阶-脉搏指数","right":"未完成"}]},{"title":"平衡能力","list":[{"left":"闭眼单脚站立（秒）","right":"未完成"}]},{"title":"肌肉耐力","list":[{"left":"俯卧撑（个）","right":"未完成"}]},{"title":"柔韧性","list":[{"left":"坐位体前屈（cm）","right":"未完成"}]},{"title":"肌肉力量","list":[{"left":"握力（kg）","right":"未完成"}]}]
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
         * title : 身体形态
         * list : [{"left":"身高（m）","right":"未完成"},{"left":"体重（kg）","right":"未完成"}]
         */

        private String title;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * left : 身高（m）
             * right : 未完成
             */

            private String left;
            private String right;

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getRight() {
                return right;
            }

            public void setRight(String right) {
                this.right = right;
            }
        }
    }
}
