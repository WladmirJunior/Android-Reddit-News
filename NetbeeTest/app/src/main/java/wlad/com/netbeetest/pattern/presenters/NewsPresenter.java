package wlad.com.netbeetest.pattern.presenters;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.List;

import wlad.com.netbeetest.events.ErrorResponse;
import wlad.com.netbeetest.events.ReceiverComments;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.models.NewsList;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.models.NewsListModel;

import static android.content.ContentValues.TAG;
import static wlad.com.netbeetest.ui.activities.MainActivity.ERROR;

/**
 * Created by wlad on 24/05/17.
 */

public class NewsPresenter implements Mvp.NewsPresenterOperations {

    private WeakReference<Mvp.NewsViewOperations> view;
    private Mvp.NewsModelOperations model;
    private List<News> commentList;
    private NewsData newsData;

    public NewsPresenter(Mvp.NewsViewOperations view, NewsData newsData) {
        this.view = new WeakReference<>(view);
        this.model = new NewsListModel();
        this.newsData = newsData;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onConfigurationChanged(Mvp.NewsViewOperations view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDestroy(boolean isChangingConfig) {
        view.get().saveViewElements();
        view = null;
        EventBus.getDefault().unregister(this);
        if(!isChangingConfig) {
            model.onDestroy();
        }
    }

    @Override
    public void reloadSavedElements(List list, Object element) {
        view.get().updateList(list);
        commentList = (List<News>) list;
        newsData = (NewsData) element;
    }

    @Override
    public void openRecycle() {
        view.get().showLoad();
        model.getComments(String.format("%s.json", newsData.getPermalink()));
        Log.d(TAG, "openRecycle: "+newsData.getPermalink());
    }

    @Override
    public void clickItem(Object item) {
        if(item instanceof NewsData){
            view.get().openUrl(((NewsData) item).getUrl());
        }
    }

    /* Event Model Listeners */

    @SuppressWarnings("unchecked")
    @Subscribe
    public void onReceiverResponse(ReceiverComments event){
        List<NewsList> newsListList = (List<NewsList>) event.element;
        commentList = newsListList.get(1).getNewsList();
        view.get().hideLoad();
        view.get().updateList(commentList);
    }

    @Subscribe
    public void onErrorResponse(ErrorResponse event) {
        view.get().hideLoad();
        view.get().showAlert(ERROR);
    }
}
