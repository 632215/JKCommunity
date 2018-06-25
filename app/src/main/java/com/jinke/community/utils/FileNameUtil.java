package com.jinke.community.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/11/7.
 */

public class FileNameUtil {
    public static String getrandom() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().trim().replace("-", "");
    }
}
