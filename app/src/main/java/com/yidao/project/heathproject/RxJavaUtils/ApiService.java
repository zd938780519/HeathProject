package com.yidao.project.heathproject.RxJavaUtils;




import com.yidao.project.heathproject.Beans.ActiveBean;
import com.yidao.project.heathproject.Beans.AnswerBean;
import com.yidao.project.heathproject.Beans.ByTitleBean;
import com.yidao.project.heathproject.Beans.CeShiPlanBean;
import com.yidao.project.heathproject.Beans.ChageNameBean;
import com.yidao.project.heathproject.Beans.CheckCodeBean;
import com.yidao.project.heathproject.Beans.CodeBean;
import com.yidao.project.heathproject.Beans.FTJBean;
import com.yidao.project.heathproject.Beans.Health;
import com.yidao.project.heathproject.Beans.LoginBean;
import com.yidao.project.heathproject.Beans.OverBean;
import com.yidao.project.heathproject.Beans.PlanBean;
import com.yidao.project.heathproject.Beans.QByTitleBean;
import com.yidao.project.heathproject.Beans.RegisterBean;
import com.yidao.project.heathproject.Beans.RunBean;
import com.yidao.project.heathproject.Beans.SubhumanBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiService {

//    final String BASE_URL = "http://192.168.0.254:8080/chat-face/";

    final String BASE_URL = "http://103.197.71.92:8080/api/";
    final String SHARE_BASEURL ="http://103.197.71.92:8080";
//     String Base64 = StoreUtil.getBase64();

//    http://www.eo.com/server/index.php?g=Web&c=Mock&o=simple&projectID=15&uri=/users/help_list


//    http://192.168.252.111:88/index.php?app=api&mod=User&act=show



    /**
     * 账号登陆
     * @return
     */
//    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> getLoginInterface(@Query("phone") String phone,
                                            @Query("password") String password,
                                            @Header("Content-Type") String header);


    /**
     * 获取验证码
     * @return
     */
//    @FormUrlEncoded
    @POST("user/phoneMsg")
    Observable<CodeBean> getVerificationCode(@Query("phone") String phone,
                                             @Header("Content-Type") String header);


    /**
     * 验证验证码
     * @return
     */
//    @FormUrlEncoded
    @POST("user/checkCode")
    Observable<CheckCodeBean> getCheckCode(@Query("phone") String phone,
                                               @Query("code") String code,
                                               @Header("Content-Type") String header);


    /**
     * 注册账号
     * @return
     */
//    @FormUrlEncoded
    @POST("user/regist")
    Observable<RegisterBean> getRegisterCode(@Query("phone") String phone,
                                          @Query("password") String password,
                                             @Query("age") String age,
                                             @Query("sex") String sex,
                                          @Header("Content-Type") String header);


    /**
     * 修改密码
     * @return
     */
//    @FormUrlEncoded
    @POST("user/updatePassword")
    Observable<CheckCodeBean> getupdatePassword(@Query("phone") String phone,
                                                @Query("newPassword") String newPassword,
                                                @Query("oldPassword") String oldPassword,
                                                @Header("Content-Type") String header);



    /**
     * 风险量表
     * @return
     */
//    @FormUrlEncoded
    @POST("qa/getTitleOfPlan")
    Observable<PlanBean> getRiskData(@Query("userId") Integer userId,
                                     @Header("Content-Type") String header);



    /**
     * 问答题
     * @return
     */
//    @FormUrlEncoded
    @POST("qa/getQByTitle")
    Observable<QByTitleBean> getQByTitleData(@Query("titleId") Integer titleId,
                                             @Header("Content-Type") String header);


    /**
     * 问答题提交
     * @return
     */
//    @FormUrlEncoded
    @POST("qa/createAnswer")
    Observable<AnswerBean> getAnswerData(@Query("userId") Integer userId,
                                         @Query("answer") String answer,
                                         @Header("Content-Type") String header);


    /**
     * 测试类目
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/getTitleOfPlan")
    Observable<SubhumanBean> getSubhumanData(@Query("userId") Integer userId,
                                             @Header("Content-Type") String header);



    /**
     * 测试类目
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/getByTitleId")
    Observable<ByTitleBean> getByTitle(@Query("userId") Integer userId,
                                           @Query("titleId") Integer titleId,
                                           @Header("Content-Type") String header);


    /**
     * 形态测试
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/stTest")
    Observable<Health> getStTest(@Query("userId") Integer userId,
                                 @Query("health") String health,
                                 @Query("weight") String weight,
                                 @Header("Content-Type") String header);


    /**
     * 形态测试
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/xfTj")
    Observable<FTJBean> getXFTJ(@Query("userId") Integer userId,
                                @Query("score") String score,
                                @Header("Content-Type") String header);




    /**
     * 耐力测试：仰卧起坐（女）
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/nlSitUpTest")
    Observable<FTJBean> getNlSitUp(@Query("userId") Integer userId,
                                @Query("num") String num,
                                @Header("Content-Type") String header);



    /**
     * 耐力测试：俯卧撑（男）
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/nlPushUpTest")
    Observable<FTJBean> getNlPushUp(@Query("userId") Integer userId,
                                   @Query("num") String num,
                                   @Header("Content-Type") String header);


    /**
     *柔韧测试
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/rrTest")
    Observable<FTJBean> getRrTest(@Query("userId") Integer userId,
                                    @Query("cm") String cm,
                                    @Header("Content-Type") String header);


    /**
     *肌肉能力测试
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/llTest")
    Observable<FTJBean> getJIRouTest(@Query("userId") Integer userId,
                                  @Query("kg") String kg,
                                  @Header("Content-Type") String header);



    /**
     *平衡测试
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/phTest")
    Observable<FTJBean> getPhTest(@Query("userId") Integer userId,
                                     @Query("second") String second,
                                     @Header("Content-Type") String header);


    /**
     *平衡测试
     * @return
     */
//    @FormUrlEncoded
    @POST("user/updateNickname")
    Observable<ChageNameBean> getChangeUserName(@Query("id") Integer id,
                                        @Query("nickname") String nickname,
                                        @Header("Content-Type") String header);



    /**
     *测试记录
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/getPlan")
    Observable<CeShiPlanBean> getCeShiPlan(@Query("userId") Integer userId, @Header("Content-Type") String header);


    /**
     *问答记录
     * @return
     */
//    @FormUrlEncoded
    @POST("qa/getPlan")
    Observable<ActiveBean> getQaPlan(@Query("userId") Integer userId, @Header("Content-Type") String header);



    /**
     *运动指导
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/guidance")
    Observable<RunBean> getRun(@Query("userId") Integer userId, @Header("Content-Type") String header);


    /**
     *运动指导
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/matte")
    Observable<OverBean> getMatte(@Header("Content-Type") String header);


    /**
     *运动指导
     * @return
     */
//    @FormUrlEncoded
    @POST("testTitle/xfRunMeterTest")
    Observable<FTJBean> getRunMeterTest(
            @Query("userId") Integer userId,
            @Query("xfRunMeter") String xfRunMeter,
            @Header("Content-Type") String header);




    /**
     * 视频列表
     * @return
     */
//    @FormUrlEncoded
//    @POST("index.php?app=api&mod=Album&act=getCatalog")
//    Observable<VideoListBean> getVideoListData(@Field("id") int phoneNumber, @Field("uid") int userId);


    /**
     * 视频列表
     * @return
     */
//    @FormUrlEncoded
//    @POST("index.php?app=api&mod=Video&act=videoInfo")
//    Observable<VIdeoPalyerBean> getVideoData(@Field("id") String id);


    /**
     * 根据id 获得资料
     * @return
     */
//    @FormUrlEncoded
//    @POST("index.php?app=api&mod=User&act=show")
//    Observable<MySelfBean> getSelfData(@Field("uid") int id);



    /**
     * 头像上传
     * @return
     */
//    @Multipart
//    @POST("index.php?app=api&mod=User&act=upload_face")
//    Observable<HeadImageBean> upLoadHeadImage(@Header("uid") int uid, @Part MultipartBody.Part uploadFile);



}
