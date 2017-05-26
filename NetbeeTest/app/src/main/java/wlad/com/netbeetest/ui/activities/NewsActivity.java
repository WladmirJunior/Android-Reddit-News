package wlad.com.netbeetest.ui.activities;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.adpters.CommentsRecyclerViewAdapter;
import wlad.com.netbeetest.databinding.ActivityNewsBinding;
import wlad.com.netbeetest.helpers.CustomTabsHelper;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.pattern.StateMaintainer;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.presenters.NewsPresenter;
import wlad.com.netbeetest.utils.VerticalSpaceItemDecoration;

public class NewsActivity extends BaseActivity implements Mvp.NewsViewOperations, View.OnClickListener {

    private static final String LIST_COMMENTS = "comments";
    private static final String NEWS_DATA = "newsData";
    private final String TAG = getClass().getSimpleName();
    private ActivityNewsBinding binding;

    private Mvp.NewsPresenterOperations presenter;

    private List retainList;
    private NewsData newsData;

    private CommentsRecyclerViewAdapter adapter;

    private final StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMvp();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        addToolbar(getString(R.string.news_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.card.textTitle.setText(newsData.getTitle());
        binding.card.textAuthor.setText(String.format("Submitted by %s", newsData.getAuthor()));
        Glide.with(this)
                .load(newsData.getThumbnail())
                .placeholder(R.drawable.default_image)
                .into(binding.card.imageThumb);

        binding.card.getRoot().setOnClickListener(this);

        adapter = new CommentsRecyclerViewAdapter(this, new ArrayList<NewsData>());
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.card_vertical_margin));
        binding.recyclerView.addItemDecoration(itemDecoration);
        binding.recyclerView.setAdapter(adapter);

        if(stateMaintainer.firstTimeIn()){
            presenter.openRecycle();
        }
        else {
            presenter.reloadSavedElements((List) stateMaintainer.get(LIST_COMMENTS), stateMaintainer.get(NEWS_DATA));
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
                newsData = (NewsData) getIntent().getSerializableExtra(MainActivity.DATA_SCREEN);
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

    private void initialize(Mvp.NewsViewOperations view) {
        presenter = new NewsPresenter(view, newsData);
        stateMaintainer.put(Mvp.NewsPresenterOperations.class.getSimpleName(), presenter);
    }

    private void reinitialize(Mvp.NewsViewOperations view) throws InstantiationException, IllegalAccessException {
        newsData = stateMaintainer.get(NEWS_DATA);
        presenter = stateMaintainer.get(Mvp.NewsPresenterOperations.class.getSimpleName());
        if (presenter == null) {
            initialize(view);
        } else {
            presenter.onConfigurationChanged(view);
        }
    }


    /* View Contract */

    @Override
    public void showAlert(String msg) {

    }

    @Override
    public void showLoad() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        binding.progressBar.setVisibility(View.GONE);
    }
    
    @Override
    public void updateList(List list) {
        if (!isEmptyList(list)) {
            retainList = list;
            adapter.clear();
            adapter.addAll(list);
        }
    }
    
    @Override
    public void saveViewElements() {
        stateMaintainer.put(LIST_COMMENTS, retainList);
        stateMaintainer.put(NEWS_DATA, newsData);
    }

    @Override
    public void openUrl(String url) {
        CustomTabsIntent customTabsIntent = CustomTabsHelper.getInstance(this);
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private boolean isEmptyList(List list) {
        if (list == null || list.size() == 0) {
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            return true;
        } else {
            binding.emptyView.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            return false;
        }
    }

    /* View Click Listener */

    @Override
    public void onClick(View v) {
        if (v.equals(binding.card.getRoot())) {
            presenter.clickItem(newsData);
        }
    }
}
