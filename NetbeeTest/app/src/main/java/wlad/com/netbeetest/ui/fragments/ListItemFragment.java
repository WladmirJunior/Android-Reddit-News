package wlad.com.netbeetest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.NewsRecyclerViewAdapter;
import wlad.com.netbeetest.api.NewsListServiceApi;
import wlad.com.netbeetest.model.NewsData;
import wlad.com.netbeetest.model.NewsList;

import static android.content.ContentValues.TAG;

/**
 * Created by wlad on 22/05/17.
 */

public class ListItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recycler_fragment)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<NewsData> newsDataList;
    private NewsRecyclerViewAdapter adapter;

    public static ListItemFragment newInstance(){
        return new ListItemFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/r/Android/new/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        NewsListServiceApi service = retrofit.create(NewsListServiceApi.class);

        //TODO Criar uma classe para encapsular isso

        Call<NewsList> callNewsList = service.getNews();
        callNewsList.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                Log.d(TAG, "onResponse: "+response);
                if(response.code()==200){
                    adapter.clear();
                    adapter.addAll(response.body().newsListData.getNewsList());
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+call);
            }
        });

        newsDataList = new ArrayList<>();

        adapter = new NewsRecyclerViewAdapter(newsDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        //adapter.addAll();
        refreshLayout.setRefreshing(false);
    }
}
