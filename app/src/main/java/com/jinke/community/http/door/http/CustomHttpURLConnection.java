package com.jinke.community.http.door.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class CustomHttpURLConnection {

    public static String requestGet(String strUrl, HashMap<String, String> paramsMap) {
        String result = "";
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = strUrl + "/?" + tempParams.toString();
            // 新建一个URL对象
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(9 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(9 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                InputStream inputStream = urlConn.getInputStream();
                InputStreamReader inStream = new InputStreamReader(inputStream);
                BufferedReader buffer = new BufferedReader(inStream);
                String strLine = null;
                while ((strLine = buffer.readLine()) != null) {
                    result += strLine;
                }
                Utils.loge("GET 方式请求成功，result--->" + result);
            } else {
                Utils.loge("GET 方式请求失败");
                result = "";
            }
            // 关闭连接
            urlConn.disconnect();
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return "";
        }
    }

    public static String requestPost(String strUrl, String params) {
        String result = "";
        try {
            String requestUrl = strUrl;
            // 新建一个URL对象
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(6 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(6 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Content-Type", "application/json;charset=gb18030;");
            // 开始连接
            urlConn.connect();

            //POST方法时使用
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
            out.writeBytes(params);
            out.flush();
            out.close();

            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                InputStream inputStream = urlConn.getInputStream();
                InputStreamReader inStream = new InputStreamReader(inputStream);
                BufferedReader buffer = new BufferedReader(inStream);
                String strLine = null;
                while ((strLine = buffer.readLine()) != null) {
                    result += strLine;
                }
                Utils.loge("Post方式请求成功，result--->" + result);
            } else {
                Utils.loge("Post方式请求失败");
                result = "";
            }
            // 关闭连接
            urlConn.disconnect();
            return result;
        } catch (Exception e) {
            Utils.loge(e.toString());
            return "";
        }
    }
}
