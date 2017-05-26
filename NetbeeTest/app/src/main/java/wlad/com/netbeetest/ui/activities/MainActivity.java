package wlad.com.netbeetest.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.NewsRecyclerViewAdapter;
import wlad.com.netbeetest.databinding.ActivityMainBinding;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.pattern.StateMaintainer;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.presenters.MainPresenter;
import wlad.com.netbeetest.utils.VerticalSpaceItemDecoration;

public class MainActivity extends BaseActivity implements Mvp.MainViewOperations, NewsRecyclerViewAdapter.NewsClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String DATA_SCREEN = "data";
    public static final String ERROR = "GenericError";

    private static final String LIST_NEWS = "newsList";
    private final String TAG = getClass().getSimpleName();

    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    private NewsRecyclerViewAdapter adapter;

    private List<News> newsList;

    private boolean loading = true;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;

    private final StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG);

    private Mvp.MainPresenterOperations presenter;

    @SuppressWarnings("unchecked")
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
        binding.recyclerView.addOnScrollListener(getOnScrollListener());
        binding.swipeRefresh.setOnRefreshListener(this);

        if(stateMaintainer.firstTimeIn()){
            presenter.openRecycle();
        }
        else {
            presenter.reloadSavedElements(stateMaintainer.get(LIST_NEWS));
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

    private void initialize(Mvp.MainViewOperations view) {
        presenter = new MainPresenter(view);
        stateMaintainer.put(Mvp.MainPresenterOperations.class.getSimpleName(), presenter);
    }

    private void reinitialize(Mvp.MainViewOperations view) throws InstantiationException, IllegalAccessException {
        presenter = stateMaintainer.get(Mvp.MainPresenterOperations.class.getSimpleName());
        if (presenter == null) {
            initialize(view);
        } else {
            presenter.onConfigurationChanged(view);
        }
    }

    /* View Contract */

    @Override
    public void goToScreen(Class screen, NewsData data) {
        Intent intent = new Intent(this, screen);
        intent.putExtra(DATA_SCREEN, data);
        startActivity(intent);
    }

    @Override
    public void showAlert(String msg) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        if(msg.equals(ERROR)){
            builder.content(R.string.message_error_dialog).positiveText(R.string.agree).show();
            isEmptyList(newsList);
        }
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
    public void showListLoad() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListLoad() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideRefresh() {
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void updateList(Object element) {
        newsList = (List<News>) element;
        if (!isEmptyList(newsList)) {
            adapter.clear();
            adapter.addAll(newsList);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addList(Object element) {
        List<News> list = (List<News>) element;
        newsList.addAll(list);
        adapter.addAll(list);
        loading = true;
    }

    @Override
    public void saveViewElements(Object element){
        stateMaintainer.put(LIST_NEWS, element);
    }

    private boolean isEmptyList(List list){
        if(list == null || list.size() == 0) {
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            return true;
        }
        else{
            binding.emptyView.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            return false;
        }
    }

    /* View Listeners */

    @Override
    public void onItemClick(NewsData newsData) {
        presenter.clickOnItemRecycle(newsData);
    }

    @Override
    public void onRefresh() {
        presenter.swipeToRefresh();
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {

        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) binding.recyclerView.getLayoutManager();

                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            presenter.endingRecycle();
                        }
                    }
                }
            }
        };
    }
}
