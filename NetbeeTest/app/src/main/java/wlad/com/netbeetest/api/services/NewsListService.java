package wlad.com.netbeetest.api.services;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wlad.com.netbeetest.api.NewsListServiceApi;
import wlad.com.netbeetest.helpers.RetrofitHelper;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsList;

import static android.content.ContentValues.TAG;

/**
 * Created by wlad on 23/05/17.
 */

public class NewsListService implements Callback<NewsList> {

    public interface NewsListServiceListener{
        public void onReceiverResponser(List<News> list, String error);
    }

    private NewsListServiceApi serviceApi;
    private NewsListServiceListener listener;

    public NewsListService(NewsListServiceListener listener) {
        this.listener = listener;
    }

    public void start(){
        serviceApi = RetrofitHelper.getInstance().create(NewsListServiceApi.class);
        getResponse();
    }

    public void getResponse(){
        Call<NewsList> call = serviceApi.getNews();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<NewsList> call, Response<NewsList> response) {
        if(response.isSuccessful()){
            List<News> newsList = response.body().getNewsList();
            listener.onReceiverResponser(newsList, "");
        }
        else {
            Log.d(TAG, "onResponse: "+response.body());
        }
    }

    @Override
    public void onFailure(Call<NewsList> call, Throwable t) {
        listener.onReceiverResponser(null, t.getMessage());
        t.printStackTrace();
    }
}
