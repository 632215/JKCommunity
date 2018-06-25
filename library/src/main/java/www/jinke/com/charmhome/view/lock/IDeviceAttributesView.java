package www.jinke.com.charmhome.view.lock;

import java.util.List;

import www.jinke.com.charmhome.bean.DeviceListBean;
import www.jinke.com.charmhome.view.BaseView;

/**
 * Created by root on 17-12-12.
 */

public interface IDeviceAttributesView extends BaseView{
    void onUpDeviceName();

    void showMsg(String s);

    void onMainDeviceListNext(List<DeviceListBean> list);
}
