package wlad.com.netbeetest.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
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
import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.NewsRecyclerViewAdapter;
import wlad.com.netbeetest.api.services.NewsListService;
import wlad.com.netbeetest.helpers.CustomTabsHelper;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsData;

import static android.content.ContentValues.TAG;

/**
 * Created by wlad on 22/05/17.
 */

//TODO Criar o contrato para esse fragment não ter de cuidar dos listeners abaixo

public class ListItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsListService.NewsListServiceListener, NewsRecyclerViewAdapter.NewsClickListener{

    @BindView(R.id.recycler_fragment)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private NewsRecyclerViewAdapter adapter;
    private NewsListService newsListService;

    public static ListItemFragment newInstance(){
        return new ListItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        newsListService = new NewsListService(this);
        newsListService.start();

        adapter = new NewsRecyclerViewAdapter(getActivity(), new ArrayList<NewsData>());
        adapter.setNewsClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        newsListService.getResponse();
    }

    @Override
    public void onReceiverResponser(List<News> list, String error) {
        if(list != null){
            adapter.clear();
            adapter.addAll(list);
        }
        else {
            Log.d(TAG, "onReceiverResponser Error: "+error);
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(NewsData newsData) {
        CustomTabsIntent customTabsIntent = CustomTabsHelper.getInstance(getActivity());
        customTabsIntent.launchUrl(getActivity(), Uri.parse(newsData.url));
    }
}
