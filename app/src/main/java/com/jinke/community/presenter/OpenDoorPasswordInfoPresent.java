package com.jinke.community.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PasswordInfo;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.service.impl.OpenDoorPasswordInfoImpl;
import com.jinke.community.service.listener.IOpenDoorPasswordInfoListener;
import com.jinke.community.view.IOpenDoorPasswordInfoView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.net.URLEncoder;
import java.util.Map;

import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorPasswordInfoPresent extends BasePresenter<IOpenDoorPasswordInfoView> implements IOpenDoorPasswordInfoListener, UMShareListener {
    private FragmentActivity mContext;
    OpenDoorPasswordInfoImpl passwordInfo;

    public OpenDoorPasswordInfoPresent(FragmentActivity mContext) {
        this.mContext = mContext;
        passwordInfo = new OpenDoorPasswordInfoImpl(mContext);
    }

    public void getPassWord(Map<String, String> map) {
        passwordInfo.getPassWordData(map, this);
    }

    @Override
    public void onPasswordInfo(PasswordInfo info) {
        if (mView != null) {
            mView.onPasswordInfo(info);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    public void shareWeChat(PasswordInfo passwordInfo, String sendMessage, String openDoorInfoAddressString, String purposeString, String showEndTime,String platform) {
        this.openDoorInfoAddressString = openDoorInfoAddressString;
        this.purposeString = purposeString;
        this.showEndTime = showEndTime;
        if (null != passwordInfo && null != passwordInfo.listDate) {
//            BaseUserBean userBean = MyApplication.getBaseUserBean();
            switch (platform) {
                case "QQ":
                    share(passwordInfo, sendMessage,SHARE_MEDIA.QQ);
//                    getQQShareData(passwordInfo, sendMessage);
                    break;

                case "WEIXIN":
                    share(passwordInfo, sendMessage,SHARE_MEDIA.WEIXIN);
//                    new ShareAction(mContext)
//                            .setPlatform(SHARE_MEDIA.WEIXIN)
//                            .withText(sendMessage)
//                            .setCallback(this).share();
                    break;
            }


        }

    }

    private void share(PasswordInfo passwordInfo, String sendMessage, SHARE_MEDIA shareMedia) {
        String serviceUrl = DriveHttpMethods.BASE_URL;
        try {
            serviceUrl += "passWordHtl?passWordList=";
            String context="[";
            for (int i = 0; i < passwordInfo.listDate.size(); i++) {
                context += "{\"code\":\"" + passwordInfo.listDate.get(i).getPassWord()
                        + "\",\"name\":\"" + passwordInfo.listDate.get(i).getDoorName();
                if (i < passwordInfo.listDate.size() - 1) {
                    context += "\"},";
                } else {
                    context += "\"}";
                }
            }
            context += "]";
            String serviceUrl2 ="&address=" + openDoorInfoAddressString +
                    "&purpose=" + purposeString +
                    "&dateTime=" +showEndTime +
                    "&num=" +"1次";
            String url=serviceUrl+ URLEncoder.encode(context, "utf-8")+serviceUrl2;
            new ShareAction(mContext)
                    .setPlatform(shareMedia)
                    .withTargetUrl(url)
                    .withTitle("门禁秘钥")
                    .withText(sendMessage)
                    .setCallback(this).share();
        } catch (Exception e) {
            e.printStackTrace();
        } }

    private String openDoorInfoAddressString;
    private String purposeString;//目的
    private String showEndTime = "";

    public void sendMessage(String MsgContent) {
        Uri smsToUri = Uri.parse("smsto:");// 联系人地址
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", MsgContent);// 短信内容
        mContext.startActivity(mIntent);
    }


    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.d("plat", "platform" + MyApplication.getBaseUserBean().getPlatformName());
        ToastUtils.showShort(mContext, MyApplication.getBaseUserBean().getPlatformName() + " 分享成功啦");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtils.showShort(mContext, MyApplication.getBaseUserBean().getPlatformName() + " 分享失败啦");
        if (throwable != null) {
            Log.d("throw", "throw:" + throwable.getMessage());
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
//        ToastUtils.showShort(mContext, MyApplication.getBaseUserBean().getPlatformName() + " 分享取消了");
    }
}
