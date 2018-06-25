package www.jinke.com.charmhome.impl;

import com.blankj.utilcode.util.LogUtils;
import com.dsm.xiaodi.biz.sdk.servercore.ServerUnit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import www.jinke.com.charmhome.bean.LockMainDeviceBean;
import www.jinke.com.charmhome.listener.lock.IDeviceAttributesBiz;
import www.jinke.com.charmhome.listener.lock.IDeviceAttributesListener;
import www.jinke.com.charmhome.presenter.lock.DeviceAttributesPresenter;

/**
 * Created by root on 17-12-12.
 */

public class DeviceAttributesImpl implements IDeviceAttributesBiz {
    @Override
    public void updateDeviceName(String lockmac, String deviceNewName, final IDeviceAttributesListener listener) {
        ServerUnit.getInstance().updateDeviceName(lockmac, deviceNewName, new ServerUnit.OnServerUnitListener() {
            @Override
            public void success(List list, String s) {
                LogUtils.i("更新设备名:" + s);
                listener.onUpDeviceName();
                listener.showMsg("设备名更新成功!");
            }

            @Override
            public void failure(String s, int i) {
                LogUtils.i("更新设备名:" + s);
                listener.showMsg("设备名更新失败!");

            }
        });
    }

    public void getLoadMainDeviceList(String mobile, final IDeviceAttributesListener listener) {
        ServerUnit.getInstance().loadMainDeviceList(mobile, null, new ServerUnit.OnServerUnitListener() {
            @Override
            public void success(List list, String s) {
                LogUtils.i("设备列表:" + list.toString());
                Type type = new TypeToken<List<List<LockMainDeviceBean>>>() {
                }.getType();
                List<List<LockMainDeviceBean>> userBean = new Gson().fromJson(list.toString(), type);
                if (userBean != null && userBean != null) {
                    listener.onMainDeviceList(userBean.get(0).get(0).getLock());
                }
            }

            @Override
            public void failure(String s, int i) {
                listener.showMsg(s);
            }
        });
    }
}
