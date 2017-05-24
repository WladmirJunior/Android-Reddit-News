package wlad.com.netbeetest.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.NewsRecyclerViewAdapter;
import wlad.com.netbeetest.databinding.ActivityMainBinding;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.pattern.StateMaintainer;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.presenters.ListItemModelPresenter;
import wlad.com.netbeetest.utils.VerticalSpaceItemDecoration;

public class MainActivity extends BaseActivity implements Mvp.ViewOperations, NewsRecyclerViewAdapter.NewsClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LIST_NEWS = "newsList";
    private static final String AFTER_NEWS = "afterNews";
    protected final String TAG = getClass().getSimpleName();

    ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    private NewsRecyclerViewAdapter adapter;

    List retainList;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private final StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG);

    private Mvp.PresenterOperations presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMvp();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        addToolbar(getString(R.string.main_title));
        progressDialog = new ProgressDialog(this);

        adapter = new NewsRecyclerViewAdapter(this, new ArrayList<NewsData>());
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.card_vertical_margin));
        binding.recyclerView.addItemDecoration(itemDecoration);
        adapter.setNewsClickListener(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        binding.swipeRefresh.setOnRefreshListener(this);

        if(stateMaintainer.firstTimeIn()){
            presenter.getItems();
        }
        else {
            presenter.updateRetainItems((List) stateMaintainer.get(LIST_NEWS), stateMaintainer.get(AFTER_NEWS));
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy(true);
        super.onDestroy();
    }

    /* Start Mvp and manager your state */

    private void startMvp() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize(Mvp.ViewOperations view) throws InstantiationException, IllegalAccessException {
        presenter = new ListItemModelPresenter(view);
        stateMaintainer.put(Mvp.PresenterOperations.class.getSimpleName(), presenter);
    }

    private void reinitialize(Mvp.ViewOperations view) throws InstantiationException, IllegalAccessException {
        presenter = stateMaintainer.get(Mvp.PresenterOperations.class.getSimpleName());
        if (presenter == null) {
            initialize(view);
        } else {
            presenter.onConfigurationChanged(view);
        }
    }

    /* View Contract */

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlert(String msg) {

    }

    @Override
    public void showLoad() {
        progressDialog = ProgressDialog.show(this, getString(R.string.load_title), getString(R.string.load_message), true);
    }

    @Override
    public void hideLoad() {
        progressDialog.dismiss();
    }

    @Override
    public void hideRefresh() {
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void updateList(List list) {
        retainList = list;
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void addList(Object element) {
        List<News> news = (List<News>) element;
        retainList.addAll(news);
        adapter.addAll(news);
        loading = true;
    }

    @Override
    public void saveViewElements(Object element){
        stateMaintainer.put(LIST_NEWS, retainList);
        stateMaintainer.put(AFTER_NEWS, element);
    }

    /* View Listeners */

    @Override
    public void onItemClick(NewsData newsData) {
        presenter.openItem(newsData);
    }

    @Override
    public void onRefresh() {
        presenter.updateItems();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) binding.recyclerView.getLayoutManager();

            if(dy > 0)
            {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (loading)
                {
                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                        loading = false;
                        presenter.getMoreItems();
                    }
                }
            }
        }
    };
}
