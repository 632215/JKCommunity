package com.jinke.community.utils;

import com.jinke.community.http.main.HttpMethods;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by root on 17-8-10.
 */

public class UpLoadFile {

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 上传多张图片及参数
     * @param params  参数
     * @param pic_key 上传图片的关键字
     */
    public static Observable<String> sendMultipart(final Map<String, String> params, final String pic_key, final List<File> files) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(final Subscriber<? super String> subscriber) {
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                multipartBodyBuilder.setType(MultipartBody.FORM);
                //遍历map中所有参数到builder
                if (params != null) {
                    for (String key : params.keySet()) {
                        multipartBodyBuilder.addFormDataPart(key, params.get(key));
                    }
                }
                //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
                if (files != null) {
                    for (File file : files) {
                        multipartBodyBuilder.addFormDataPart(pic_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                    }
                }
                //构建请求体
                RequestBody requestBody = multipartBodyBuilder.build();

                Request.Builder RequestBuilder = new Request.Builder();
                RequestBuilder.url(HttpMethods.BASE_FILE_UPLOAD);// 添加URL地址
                RequestBuilder.post(requestBody);
                Request request = RequestBuilder.build();

                okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
                OkHttpClient okHttpClient = httpBuilder
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(150, TimeUnit.SECONDS)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        subscriber.onNext(str);
                        subscriber.onCompleted();
                        call.cancel();
                    }
                });
            }
        });
    }
}
