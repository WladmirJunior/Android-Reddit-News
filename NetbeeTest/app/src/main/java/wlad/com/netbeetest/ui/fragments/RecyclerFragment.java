package wlad.com.netbeetest.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.NewsRecyclerViewAdapter;
import wlad.com.netbeetest.databinding.FragmentListBinding;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.pattern.ListItemPresenter;
import wlad.com.netbeetest.pattern.Mvp;
import wlad.com.netbeetest.utils.VerticalSpaceItemDecoration;

/**
 * Created by wlad on 24/05/17.
 */

public class RecyclerFragment extends BaseFragment implements Mvp.RequiredViewOperations, NewsRecyclerViewAdapter.NewsClickListener, SwipeRefreshLayout.OnRefreshListener {

    private FragmentListBinding binding;
    private NewsRecyclerViewAdapter adapter;
    private ProgressDialog progressDialog;

    private Mvp.PresenterOperations presenter;

    public static RecyclerFragment newInstance(){
        return new RecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        progressDialog = new ProgressDialog(getContext());

        adapter = new NewsRecyclerViewAdapter(getActivity(), new ArrayList<NewsData>());
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.card_vertical_margin));
        binding.recyclerFragment.addItemDecoration(itemDecoration);
        adapter.setNewsClickListener(this);
        binding.recyclerFragment.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(this);

//        binding.recyclerFragment.addOnScrollListener(getScrollListener());

        presenter = new ListItemPresenter(this);
        presenter.getItems();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showAlert(String msg) {

    }

    @Override
    public void showLoad() {
        progressDialog.show();
    }

    @Override
    public void hideLoad() {
        progressDialog.hide();
    }

    @Override
    public void hideRefresh() {
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void updateList(List list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onItemClick(NewsData newsData) {
        presenter.openItem(newsData);
    }

    @Override
    public void onRefresh() {
        presenter.updateItems();
    }
}
