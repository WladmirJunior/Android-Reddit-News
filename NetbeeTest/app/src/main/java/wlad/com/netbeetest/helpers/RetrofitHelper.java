package wlad.com.netbeetest.helpers;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by wlad on 23/05/17.
 */

public class RetrofitHelper {

    private static final String BASE_URL = "https://www.reddit.com/r/Android/new/";

    public static Retrofit getInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHelper.getClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
