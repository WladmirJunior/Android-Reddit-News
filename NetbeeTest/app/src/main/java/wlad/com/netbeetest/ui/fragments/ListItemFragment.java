package wlad.com.netbeetest.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.NewsRecyclerViewAdapter;
import wlad.com.netbeetest.api.services.NewsListService;
import wlad.com.netbeetest.databinding.FragmentListBinding;
import wlad.com.netbeetest.helpers.CustomTabsHelper;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.utils.VerticalSpaceItemDecoration;

import static android.content.ContentValues.TAG;

/**
 * Created by wlad on 22/05/17.
 */

//TODO Criar o contrato para esse fragment

public class ListItemFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        NewsListService.NewsListServiceListener, NewsRecyclerViewAdapter.NewsClickListener {

    private FragmentListBinding binding;
    private NewsRecyclerViewAdapter adapter;
    private NewsListService newsListService;

    public static ListItemFragment newInstance(){
        return new ListItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        newsListService = new NewsListService(this);
        newsListService.start();

        adapter = new NewsRecyclerViewAdapter(getActivity(), new ArrayList<NewsData>());
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.card_vertical_margin));
        binding.recyclerFragment.addItemDecoration(itemDecoration);
        binding.recyclerFragment.addOnScrollListener(getScrollListener());
        binding.recyclerFragment.setAdapter(adapter);

        adapter.setNewsClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);

        return binding.getRoot();
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
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(NewsData newsData) {
        CustomTabsIntent customTabsIntent = CustomTabsHelper.getInstance(getActivity());
        customTabsIntent.launchUrl(getActivity(), Uri.parse(newsData.getUrl()));
    }

    public RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.d(TAG, "onScrollStateChanged: ");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d(TAG, "onScrolled: ");
            }
        };
    }
}
