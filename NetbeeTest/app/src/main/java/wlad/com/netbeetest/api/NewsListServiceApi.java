package wlad.com.netbeetest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import wlad.com.netbeetest.models.NewsList;

/**
 * Created by wlad on 22/05/17.
 */

public interface NewsListServiceApi {

    @GET(".json")
    Call<NewsList> getNews();

    @GET(".json")
    Call<NewsList> getNews(@Query("after") String after);
}
