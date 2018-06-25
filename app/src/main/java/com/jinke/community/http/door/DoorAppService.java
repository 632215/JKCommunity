package com.jinke.community.http.door;


import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ListDateInfo;
import com.jinke.community.bean.PasswordInfo;
import com.jinke.community.bean.ReasonBean;
import com.jinke.community.bean.XiMoDriveListBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by liukun on 16/icon_cs3/9.
 */
public interface DoorAppService {


    @FormUrlEncoded
    @POST("getDeviceList")
    Observable<HttpResult<XiMoDriveListBean>> getDeviceList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("getDateTime")
    Observable<HttpResult<ListDateInfo>> getDateTime(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("openLog")
    Observable<HttpResult<EmptyObjectBean>> getOpenLog(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("purposeList")
    Observable<HttpResult<ReasonBean>> getPurposeList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("getHouseList")
    Observable<HttpResult<HouseListInfo>> getHouseList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("saveCode")
    Observable<HttpResult<PasswordInfo>> getSaveCode(@FieldMap Map<String, String> map);
}
