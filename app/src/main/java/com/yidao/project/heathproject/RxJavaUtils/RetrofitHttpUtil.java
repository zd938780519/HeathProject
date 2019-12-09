package com.yidao.project.heathproject.RxJavaUtils;


public class RetrofitHttpUtil {
    public static ApiService getApiService() {
        return RetrofitHelper.getRetrofit().create(ApiService.class);
    }
//    public static ApiService getApiServiceShare() {
//        return RetrofitHelper.getShareRetrofit().create(ApiService.class);
//    }
}
