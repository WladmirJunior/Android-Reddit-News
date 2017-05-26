package wlad.com.netbeetest.pattern.models;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wlad.com.netbeetest.api.NewsListServiceApi;
import wlad.com.netbeetest.events.ErrorResponse;
import wlad.com.netbeetest.events.ReceiverAfterNews;
import wlad.com.netbeetest.events.ReceiverComments;
import wlad.com.netbeetest.events.ReceiverNews;
import wlad.com.netbeetest.helpers.RetrofitHelper;
import wlad.com.netbeetest.models.NewsList;
import wlad.com.netbeetest.pattern.contracts.Mvp;

import static android.content.ContentValues.TAG;

/**
 * Created by wlad on 24/05/17.
 */

public class NewsListModel implements Mvp.NewsModelOperations{

    private NewsListServiceApi serviceApi;

    public NewsListModel() {
        serviceApi = RetrofitHelper.getInstance().create(NewsListServiceApi.class);
    }

    @Override
    public void getNews() {
        Call<NewsList> call = serviceApi.getNews();
        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if(response.isSuccessful()){
                    EventBus.getDefault().post(new ReceiverNews(response.body()));
                }
                else {
                    Log.d(TAG, "onResponse: noSuccessful "+response.body());
                    EventBus.getDefault().post(new ErrorResponse(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                failure(t);
            }
        });
    }

    @Override
    public void getNews(String query) {
        Call<NewsList> call = serviceApi.getNews(query);
        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if(response.isSuccessful()){
                    EventBus.getDefault().post(new ReceiverAfterNews(response.body()));
                }
                else {
                    Log.d(TAG, "onResponse: noSuccessful "+response.body());
                    EventBus.getDefault().post(new ErrorResponse(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                failure(t);
            }
        });
    }

    @Override
    public void getComments(String path) {
        Call<List<NewsList>> call = serviceApi.getNewsComments(path);
        call.enqueue(new Callback<List<NewsList>>() {
            @Override
            public void onResponse(Call<List<NewsList>> call, Response<List<NewsList>> response) {
                EventBus.getDefault().post(new ReceiverComments(response.body()));
            }

            @Override
            public void onFailure(Call<List<NewsList>> call, Throwable t) {
                failure(t);
            }
        });

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy ");
    }

    void failure(Throwable t) {
        EventBus.getDefault().post(new ErrorResponse(t.getMessage()));
    }
}
