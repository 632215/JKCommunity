package www.jinke.com.charmhome.presenter.lock;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.charmhome.bean.DeviceListBean;
import www.jinke.com.charmhome.bean.LockMainDeviceBean;
import www.jinke.com.charmhome.impl.DeviceAttributesImpl;
import www.jinke.com.charmhome.listener.lock.IDeviceAttributesListener;
import www.jinke.com.charmhome.utils.ACache;
import www.jinke.com.charmhome.view.lock.IDeviceAttributesView;

/**
 * Created by root on 17-12-12.
 */

public class DeviceAttributesPresenter implements IDeviceAttributesListener {
    private ACache aCache;
    private Context mContext;
    private IDeviceAttributesView attributesView;
    private DeviceAttributesImpl attributes;

    public DeviceAttributesPresenter(Context mContext, IDeviceAttributesView attributesView) {
        this.mContext = mContext;
        this.attributesView = attributesView;
        aCache = ACache.get(mContext);
        attributes = new DeviceAttributesImpl();
    }

    public void updateDeviceName(String lockmac, String deviceNewName) {
        if (attributesView != null) {
            attributes.updateDeviceName(lockmac, deviceNewName, this);
            attributesView.showLoading("正在更新设备名");
        }
    }

    @Override
    public void onUpDeviceName() {
        if (attributesView != null) {
            attributesView.hideLoading();
            attributesView.onUpDeviceName();
        }
    }

    @Override
    public void showMsg(String s) {
        if (attributesView != null) {
            attributesView.showMsg(s);
            attributesView.hideLoading();
        }
    }

    /**
     * 获取设备信息列表
     * @param account
     */
    public void getLoadMainDeviceList(String account) {
        if (attributes != null) {
            attributes.getLoadMainDeviceList(account, this);
        }
    }

    @Override
    public void onMainDeviceList(List<LockMainDeviceBean.LockBean> lock) {
        if (attributesView != null) {
            if (lock.size() > 0) {
                aCache.put("MainDeviceBean", lock.get(0), ACache.MAX_SIZE);
                List<DeviceListBean> list = new ArrayList<>();
                list.clear();
                for (LockMainDeviceBean.LockBean lockBean : lock) {
                    DeviceListBean deviceListBean = new DeviceListBean();
                    deviceListBean.setChannelpassword(lockBean.getChannelpassword());
                    deviceListBean.setLockmac(lockBean.getLockmac());
                    deviceListBean.setLockname(lockBean.getLockname());
                    deviceListBean.setSoftwareVersion(lockBean.getSoftwareVersion());
                    deviceListBean.setFingerunuserdnum(lockBean.getFingerunuserdnum());
                    deviceListBean.setFingeruserdnum(lockBean.getFingeruserdnum());
                    deviceListBean.setManageaccount(lockBean.getManageaccount());
                    deviceListBean.setMetertype(lockBean.getMetertype());
                    deviceListBean.setLockPasswordstate(lockBean.getLockPasswordstate());
                    list.add(deviceListBean);
                }
                attributesView.onMainDeviceListNext(list);
            } else {
            }
            attributesView.hideLoading();
        }
    }
}
