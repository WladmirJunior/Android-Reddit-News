package wlad.com.netbeetest.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import wlad.com.netbeetest.models.NewsList;

/**
 * Created by wlad on 22/05/17.
 */

public interface NewsListServiceApi {

    @GET("/r/Android/new/.json")
    Call<NewsList> getNews();

    @GET("/r/Android/new/.json")
    Call<NewsList> getNews(@Query("after") String after);

    @GET
    Call<List<NewsList>> getNewsComments(@Url String path);
}
