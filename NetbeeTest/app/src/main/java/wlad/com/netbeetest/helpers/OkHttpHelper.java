package wlad.com.netbeetest.helpers;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import wlad.com.netbeetest.BuildConfig;

/**
 * Created by wlad on 23/05/17.
 */

public class OkHttpHelper {

    public static OkHttpClient getClient() {
        return getClient(15, TimeUnit.SECONDS);
    }

    public static OkHttpClient getClient(int timeout, TimeUnit timeUnit) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(loggingInterceptor);
        }

        return builder
                .connectTimeout(timeout, timeUnit)
                .readTimeout(timeout, timeUnit)
                .writeTimeout(timeout, timeUnit)
                .build();
    }
}
