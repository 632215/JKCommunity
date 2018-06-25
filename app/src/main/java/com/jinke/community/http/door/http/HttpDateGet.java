package com.jinke.community.http.door.http;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * function:
 * author: hank
 * date: 2017/8/16
 */

public class HttpDateGet {

    private Context context;
    private String AccessToken;
    private String Token;
    private DateCallBackInterface dateCallBackInterface;

    public HttpDateGet(Context context, DateCallBackInterface dateCallBackInterface) {
        this.context = context;
        this.dateCallBackInterface = dateCallBackInterface;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            AccessToken = appInfo.metaData.getString("com.jinke.api.v2.API_KEY");
            if (null != AccessToken) {
                Utils.loge("------ 鉴权成功！--------");
            } else {
                Utils.loge("------ 鉴权失败，请输入正确的accessKey！--------");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Utils.loge("------ 鉴权失败，请输入正确的accessKey！--------");
            e.printStackTrace();
        }
    }

    /**
     * 查询个人账号token密码
     *
     * @param account 个人账号即手机号
     */
    public void getEmployees(String account) {
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("app_account", account);
        new GetEmployeesAsyncTask().execute(stringHashMap);
    }


    /**
     * 查询个人账号token密码
     */
    class GetEmployeesAsyncTask extends AsyncTask<HashMap<String, String>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(HashMap<String, String>... params) {
            String url = Account.regularUrl;
            try {
                JSONObject object = new JSONObject();
                for (String key : params[0].keySet()) {
                    object.put(key, URLEncoder.encode(params[0].get(key)));
                }
                String paramsString = "{\"access_token\":\"" + AccessToken + "\",\"operation\": \"GET\",\"data\":{\"app_account\":\"" + URLEncoder.encode(params[0].get("app_account")) + "\"}}";
                String result = CustomHttpURLConnection.requestPost(url + "doormaster/server/employees", paramsString);
                if (result != null && !result.equals("")) {
                    JSONObject jsonObject = new JSONObject(result);
                    if (0 == jsonObject.getInt("ret")) {
                        Utils.loge("----------" + result);
                        JSONObject dateObject = new JSONArray(jsonObject.getString("data")).getJSONObject(0);
                        Token = dateObject.getString("account_token_pwd");
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dateCallBackInterface.getEmployeesCallBack(Token);
                Utils.loge("------ 鉴权成功！--------");
            } else {
                dateCallBackInterface.getEmployeesCallBack(null);
                Utils.loge("------ 鉴权失败！--------");
            }
        }
    }

}
