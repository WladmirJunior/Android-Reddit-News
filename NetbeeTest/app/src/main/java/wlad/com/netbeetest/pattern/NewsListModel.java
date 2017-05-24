package wlad.com.netbeetest.pattern;

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
 * Created by wlad on 24/05/17.
 */

public class NewsListModel implements Mvp.ModelOperations, Callback<NewsList> {

    private Mvp.RequiredPresenterOperations presenter;
    private NewsListServiceApi serviceApi;

    public NewsListModel(Mvp.RequiredPresenterOperations presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getResponse() {
        serviceApi = RetrofitHelper.getInstance().create(NewsListServiceApi.class);
        Call<NewsList> call = serviceApi.getNews();
        call.enqueue(this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onResponse(Call<NewsList> call, Response<NewsList> response) {
        if(response.isSuccessful()){
            List<News> newsList = response.body().getNewsList();
            presenter.onReceiverResponse(newsList);
        }
        else {
            Log.d(TAG, "onResponse: noSuccessful "+response.body());
        }
    }

    @Override
    public void onFailure(Call<NewsList> call, Throwable t) {
        presenter.onError(t.getMessage());
    }
}
