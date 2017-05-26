package wlad.com.netbeetest.pattern.models;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wlad.com.netbeetest.api.NewsListServiceApi;
import wlad.com.netbeetest.events.ErrorResponse;
import wlad.com.netbeetest.events.ReceiverResponse;
import wlad.com.netbeetest.helpers.RetrofitHelper;
import wlad.com.netbeetest.models.NewsList;
import wlad.com.netbeetest.pattern.contracts.Mvp;

import static android.content.ContentValues.TAG;

/**
 * Created by wlad on 24/05/17.
 */

public class NewsListModel implements Mvp.ModelOperations, Callback<NewsList> {

    private NewsListServiceApi serviceApi;

    public NewsListModel() {
        serviceApi = RetrofitHelper.getInstance().create(NewsListServiceApi.class);
    }

    @Override
    public void getResponse() {
        Call<NewsList> call = serviceApi.getNews();
        call.enqueue(this);
    }

    @Override
    public void getResponse(String path) {
        Call<NewsList> call = serviceApi.getNews(path);
        call.enqueue(this);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy ");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onResponse(Call<NewsList> call, Response<NewsList> response) {
        if(response.isSuccessful()){
            EventBus.getDefault().post(new ReceiverResponse(response.body()));
        }
        else {
            Log.d(TAG, "onResponse: noSuccessful "+response.body());
            EventBus.getDefault().post(new ErrorResponse(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Call<NewsList> call, Throwable t) {
        EventBus.getDefault().post(new ErrorResponse(t.getMessage()));
    }
}
