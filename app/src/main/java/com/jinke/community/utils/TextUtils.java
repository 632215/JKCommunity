package com.jinke.community.utils;

import android.text.Html;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/8/3.
 */

public class TextUtils {

    /**
     * 一个字符串strings：字体大小/颜色/字符串
     *
     * @param text
     * @param strings
     */
    public static void setText(TextView text, String... strings) {
        switch (strings.length) {
            case 3: //当Strings的长度为3时：大字体 +【1】代表颜色 +【2】代表文字
                text.setText(Html.fromHtml(text.getText() + "<font 'color='" + strings[1] + "'><big>" + strings[2] + "</big></font>"));

                break;
            case 2: //当Strings的长度为2时：【0】代表颜色 + 【1】代表文字
                text.setText(Html.fromHtml(text.getText() + "<font color='" + strings[0] + "'>" + strings[1] + "</font>"));
                break;
        }
    }

    //暂不使用
    public static String getStringToDate(String time) {
        String stringDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        Date oldDate = getFormatDate(time);
        Date currentDate = getFormatDate(sdf.format(currentTime));
        String timeHours = time.substring(time.indexOf(" ") + 1, time.lastIndexOf(":"));
        int day = (int) ((currentDate.getTime() - oldDate.getTime()) / (1 * 24 * 60 * 60 * 1000));
        switch (day) {
            case 0:
                stringDay = "今天  " + timeHours;
                break;
            case 1:
                stringDay = "昨天  " + timeHours;
                break;
            case 2:
                stringDay = "前天  " + timeHours;
                break;
            default:
                stringDay = day + "天以前";
                break;
        }
        return stringDay;
    }

    private static Date getFormatDate(String time) {
        int timeYear = Integer.parseInt(time.substring(0, time.indexOf("-")));
        int timeMonth = Integer.parseInt(time.substring(time.indexOf("-") + 1, time.lastIndexOf("-")));
        int timeDay = Integer.parseInt(time.substring(time.lastIndexOf("-") + 1, time.indexOf(" ")));
        return new Date(timeYear - 1900, timeMonth - 1, timeDay);//记录时间：yyyy-MM-dd
    }

    public static String changTelNum(String num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            if (i >= 3 && i <= 6) {
                sb.append('*');
            } else {
                sb.append(c);
            }
        }
        return String.valueOf(sb);
    }

    /**
     * 管家页所有、通知公告、爆料台
     * 1小时内的，显示  N分钟前
     * 1天内的，显示  N小时前
     * 1天以上，显示日期+时间，空格链接，如 10-22  12:15
     *
     * @param time
     * @return
     */
    public static String timeChangeBreakStage(String time) throws Exception {
        String stringDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        Date oldDate = sdf.parse(time);
        int day = (int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 24 * 60 * 60 * 1000));
        switch (day) {
            case 0:// 1天内
                if ((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 60 * 1000)) < 1) {// 1小时内
                    switch ((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 1000))) {
                        case 0:
                        case 1:
                            stringDay = "1分钟前";
                            break;
                        default:
                            stringDay = String.valueOf((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 1000))) + "分钟前";
                            break;
                    }
                } else {
                    stringDay = String.valueOf((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 60 * 1000))) + "小时前";
                }
                break;
            default:
//                time = time.substring(time.indexOf("-") + 1, time.lastIndexOf(":"));
//                stringDay = time.replace("-", "/");
                stringDay = time.substring(time.indexOf("-") + 1, time.lastIndexOf(":"));
                break;
        }
        return stringDay;
    }

    /**
     * 个人爆料记录/报事记录
     * 1小时内的，显示  N分钟前
     * 1天内的，显示  N小时前
     * 1天以上，显示日期+时间，空格链接，如 2017/10/22  12:15
     *
     * @param time
     * @return
     */
    public static String timeChangeBreakPerson(String time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDay = "";
        if (!StringUtils.isEmpty(time) && !time.contains(" ")) {
            Date date = new Date();
            date.setTime(Long.parseLong(time) * 1000);
            time = sdf.format(date);
        }
        Date currentTime = new Date();
        Date oldDate = new Date();
        oldDate = sdf.parse(time);
        int day = (int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 24 * 60 * 60 * 1000));
        switch (day) {
            case 0:// 1天内
                if ((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 60 * 1000)) < 1) {// 1小时内
                    switch ((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 1000))) {
                        case 0:
                        case 1:
                            stringDay = "1分钟前";
                            break;
                        default:
                            stringDay = String.valueOf((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 1000))) + "分钟前";
                            break;
                    }
                } else {
                    stringDay = String.valueOf((int) ((currentTime.getTime() - oldDate.getTime()) / (1 * 60 * 60 * 1000))) + "小时前";
                }
                break;
            default:
                time = time.substring(0, time.lastIndexOf(":"));
                stringDay = time.replace("-", "/");
                break;
        }
        return stringDay;
    }

    /**
     * 将秒化为时间格式
     *
     * @param time
     * @return
     */
    public static String intToString(String time, String format) {
        Date date;
        if (String.valueOf(time).length() <= 10)
            date = new Date(Long.parseLong((time + "000")));
        else
            date = new Date(Long.parseLong((time)));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
