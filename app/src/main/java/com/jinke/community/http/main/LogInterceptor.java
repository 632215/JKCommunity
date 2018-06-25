package com.jinke.community.http.main;

import java.io.IOException;

import okhttp3.Interceptor;
import www.jinke.com.library.utils.commont.LogUtils;

/**
 * 拦截器
 */
public class LogInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string().trim();
//        LogUtils.i("response------" + content);
        LogUtils.i("code:" + response.code() + "-------request content:" + "api:" + response.request().url());
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
    }
}
