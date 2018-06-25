package com.jinke.community.utils;

import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.OwnerBean;
import com.jinke.community.bean.PublicHouseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-8-19.
 */

public class BeanUtis {
    List<OwnerBean> ownerBeanList = new ArrayList<>();

    public static List<OwnerBean> getOwnerList(PublicHouseBean.ListBean bean) {
        List<OwnerBean> ownerBeanList = new ArrayList<>();
        if (bean.getOwners() != null) {
            for (PublicHouseBean.ListBean.OwnersBean ownersBean : bean.getOwners()) {
                OwnerBean ownerBean = new OwnerBean(ownersBean.getOwnerName(), ownersBean.getPhone());
                ownerBeanList.add(ownerBean);
            }
        }
        return ownerBeanList;
    }

    public static List<OwnerBean> getDefaultOwnerList(DefaultHouseBean defaultHouseBean) {
        List<OwnerBean> ownerBeanList = new ArrayList<>();
        if (defaultHouseBean.getOwners() != null && defaultHouseBean.getOwners().get(0).getOwnerName() != null) {
            for (DefaultHouseBean.OwnersBean bean : defaultHouseBean.getOwners()) {
                OwnerBean ownerBean = new OwnerBean(bean.getOwnerName(), bean.getPhone());
                ownerBeanList.add(ownerBean);
            }
        }
        return ownerBeanList;
    }
}
