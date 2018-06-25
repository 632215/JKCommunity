package www.jinke.com.charmhome.listener.lock;

import java.util.List;

import www.jinke.com.charmhome.bean.LockMainDeviceBean;

/**
 * Created by root on 17-12-12.
 */

public interface IDeviceAttributesListener {
    void onUpDeviceName();

    void showMsg(String s);

    void onMainDeviceList(List<LockMainDeviceBean.LockBean> lock);
}
