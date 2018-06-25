package www.jinke.com.charmhome.listener.lock;

import java.util.List;

import www.jinke.com.charmhome.bean.CharmHomeLoginBean;
import www.jinke.com.charmhome.bean.LockMainDeviceBean;
import www.jinke.com.charmhome.bean.LockUserBean;

/**
 * Created by root on 17-12-14.
 */

public interface IStartPageListener {
    /**
     * 用户登陆成功
     *
     * @param lockUserBean
     */
    void onLoginSuccess(LockUserBean lockUserBean);



    /**
     * 提示消息
     *
     * @param s
     */
    void showMsg(String s);


    void onInputPassWord(String account,String pwd);

    void onCharmRegister(String msg);

    void onCharmLoginSuccess(CharmHomeLoginBean bean);

    void CharmHomeRegisterSuccess(CharmHomeLoginBean data);

    void onUserEmpty(String account,String pwd);

    void onRegisterFail(String s);

    void onRegisterSuccess(String s);
}
