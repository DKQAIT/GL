package com.jtjt.qtgl.http;


import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiService {
//    String BASE_URL_IMG = "http://admin.qiantuebo.com";
//    String BASE_URL = "http://admin.qiantuebo.com/admin/api/";


     String BASE_URL_IMG = "http://jt.qiantuebo.com";
    String BASE_URL = "http://jt.qiantuebo.com/admin/api/";


//    http://ceshi.qiantuebo.com/uploads/20181228/f0afb14c3ce2b6cd5c59936ead005dc2.png


///http://admin.qiantuebo.com/uploads/20181129/589e7caf97b884d32ca7004e97bed91d.png

    /*** 登录*/
    @FormUrlEncoded
    @POST("login/login")
    Observable<BaseEntity<String>> Login(
            @FieldMap Map<String, String> map
//            @Field("username") String username,
//            @Field("password") String password
    );
/**
 * 待审核列表
 */
@FormUrlEncoded
@POST("User/index")
Observable<BaseEntity<String>> Dexamin(
        @Field("logintoken") String logintoken,
        @Field("authentication") int authentication,
        @Field("page") int page,
        @Field("page_size") int page_size
);
/**
 * 待审核列表
 */
@FormUrlEncoded
@POST("user/text")
Observable<BaseEntity<String>> Ceshi(
        @Field("logintoken") String logintoken
);

/**
 * 审核用户详情
 * User/detail
 * uid
 */
@FormUrlEncoded
@POST("User/detail")
Observable<BaseEntity<String>> sDetail(
        @Field("uid") String uid
);

    /**
     *
     * @param uid
     * 审核
     * authentication  0 未通过。 1  通过
     */
    @FormUrlEncoded
@POST("User/changeStatus")
Observable<BaseEntity<String>> ChangeStatus(
        @Field("uid") int uid,
        @Field("authentication") int authentication
);



    /**
     * 添加留言
     */
    @FormUrlEncoded
    @POST("user/remarkadd")
    Observable<BaseEntity<String>> Remarkadd(
            @Field("uid") String uid,
            @Field("content") String content
    );
    /**
     * 留言列表
     */
    @FormUrlEncoded
    @POST("user/remark")
    Observable<BaseEntity<String>> Remark(
            @Field("uid") String uid
    );

    /**
     * 停车场列表
     */
    @FormUrlEncoded
    @POST("Parking_lot/index")
    Observable<BaseEntity<String>> Parking(
            @Field("page") int page,
            @Field("page_size") int page_size
    );
    /**
     * 停车场用户列表
     */
    @FormUrlEncoded
    @POST("Parking_lot/ParkingLotUser")
    Observable<BaseEntity<String>> UseList(
            @Field("pid") int pid,
            @Field("keywords") String keywords,
            @Field("page") int page,
            @Field("page_size") int page_size
    );


    /*** 上传图片*/
    @Multipart
    @POST("Upload/upload")
    Observable<BaseEntity<String>> upLoder(
//            @Field("file[]") String page
            @Part() List<MultipartBody.Part> part
//            @PartMap() Map<String, RequestBody> map
    );

/**
 * 损坏设备上报
 * Damage/add
 *
 * pid	是	int	停车场id
 name	是	string	设备名称
 p_type	是	int	车场类型 1包月 2短期
 img	是	array	图片集合
 status	是	int	状态 1：待处理 2：处理中 3：已完成
 remarks
 */

//@FieldMap Map<String, List<String>> img,
@FormUrlEncoded
@POST("Damage/add")
Observable<BaseEntity<String>> AddSB(
        @Field("pid") String pid,
        @Field("name") String name,
        @Field("p_type") int p_type,
        @Field("img") String list,
        @Field("status") int status,
        @Field("remarks") String remarks

);
/**
 * 损坏设备列表
 * status状态 1：待处理 2：处理中 3：已完成
 */


@FormUrlEncoded
@POST("Damage/index")
Observable<BaseEntity<String>> IndexLB(
        @Field("pid") String pid,
        @Field("status") int status,
        @Field("p_type") int p_type,
        @Field("page") int page,
        @Field("page_size") int page_size
);


    /**
     * 设备详情
     * @param pid
     * @return
     */
    @FormUrlEncoded
@POST("Damage/edit")
Observable<BaseEntity<String>> DEdit(
        @Field("id") String pid

);

    /**
     * 设备修改
     * status状态 1：待处理 2：处理中 3：已完成
     */


//
//    id	是	int	损坏设备信息id
//    name	是	string	设备名称
//    img	是	string	json格式图片集合
//    status	是	int	状态 1：待处理 2：处理中 3：已完成
//            remarks

    @FormUrlEncoded
    @POST("Damage/up")
    Observable<BaseEntity<String>> UpDamage(
            @Field("id") String id,
            @Field("name") String name,
            @Field("img") String list,
            @Field("status") int status,
            @Field("remarks") String remarks
    );


/**
 * 添加任务
 */

//@FieldMap Map<String, List<String>> img,
@FormUrlEncoded
@POST("Task/add")
Observable<BaseEntity<String>> AddRW(
        @Field("pid") String pid,
        @Field("title") String title,
        @Field("p_type") int p_type,
        @Field("img") String list,
        @Field("status") int status,
        @Field("content") String content

);


    /**
     * 任务列表
     * status状态 1：待处理 2：处理中 3：已完成
     */


    @FormUrlEncoded
    @POST("Task/index")
    Observable<BaseEntity<String>> TaskLB(
            @Field("pid") String pid,
            @Field("status") int status,
            @Field("p_type") int p_type,
            @Field("page") int page,
            @Field("page_size") int page_size
    );



    /**
     * 任务详情
     * @param pid
     * @return
     */
    @FormUrlEncoded
    @POST("Task/edit")
    Observable<BaseEntity<String>> REdit(
            @Field("id") String pid

    );
    /**
     * 任务回复列表
     * @param tid
     * @return
     */
    @FormUrlEncoded
    @POST("Task/taskreplyedit")
    Observable<BaseEntity<String>> Taskedit(
            @Field("tid") String tid
    );

    /**
     * 处理任务
     * tid	是	int	任务信息id
     img	否	string	json格式图片集合
     status	是	int	状态 1：待处理 2：处理中 3：已完成
     content	否	string	备注说明
     */

//@FieldMap Map<String, List<String>> img,
    @FormUrlEncoded
    @POST("Task/replyadd")
    Observable<BaseEntity<String>> CLRW(
            @Field("tid") String tid,
            @Field("img") String list,
            @Field("status") int status,
            @Field("content") String content

    );


    /**
     * 长期停车场详情
     * Parking_lot/detail
     */

    @FormUrlEncoded
    @POST("Parking_lot/detail")
    Observable<BaseEntity<String>> PDetail(
            @Field("pid") String pid
    );

    /**
     * 设备信息列表
     *
     */
    @FormUrlEncoded
    @POST("Equipment/index")
    Observable<BaseEntity<String>> EquiList(
            @Field("pid") String pid,
            @Field("p_type") String p_type
    );




    /**
     * 设备信息详情
     * @param id
     */

    @FormUrlEncoded
    @POST("Equipment/detail")
    Observable<BaseEntity<String>> EquiDetail(
            @Field("id") String id
    );


    /**
     * 问题反馈列表
     */



    @FormUrlEncoded
    @POST("Opinion/index")
    Observable<BaseEntity<String>> OpinionLB(
            @Field("status") int status,
            @Field("page") int page,
            @Field("page_size") int page_size
    );


    /**
     * 反馈详情
     */
    @FormUrlEncoded
    @POST("Opinion/edit")
    Observable<BaseEntity<String>> OpDetail(
            @Field("id") String id
    );
    /**
     * 反馈处理
     */
    @FormUrlEncoded
    @POST("Opinion/up")
    Observable<BaseEntity<String>> OpinionUp(
            @Field("id") String id,
            @Field("result") String result
    );
    /**
     * 财务处理
     */
    @FormUrlEncoded
    @POST("Finance/count")
    Observable<BaseEntity<String>> Finance(
            @Field("id") String id

    );
    /**
     * 消费流水
     * page	否	int	页数
     page_size
     */
    @FormUrlEncoded
    @POST("Flow/index")
    Observable<BaseEntity<String>> FlowIndex(
            @Field("phone") String phone,
            @Field("page") int page,
            @Field("page_size") int page_size

    );


//    /*** 版本更新*/
//    @POST("Finance/count")
//    Observable<BaseEntity<String>> Finance();


}
