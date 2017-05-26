package wlad.com.netbeetest.pattern.presenters;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import wlad.com.netbeetest.events.ErrorResponse;
import wlad.com.netbeetest.events.ReceiverAfterNews;
import wlad.com.netbeetest.events.ReceiverNews;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.models.NewsList;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.models.NewsListModel;
import wlad.com.netbeetest.ui.activities.NewsActivity;

import static wlad.com.netbeetest.ui.activities.MainActivity.ERROR;

/**
 * Created by wlad on 24/05/17.
 */

public class MainPresenter implements Mvp.MainPresenterOperations {

    private WeakReference<Mvp.MainViewOperations> view;
    private Mvp.NewsModelOperations model;
    private NewsList newsList;

    public MainPresenter(Mvp.MainViewOperations view) {
        this.view = new WeakReference<>(view);
        this.model = new NewsListModel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onConfigurationChanged(Mvp.MainViewOperations view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDestroy(boolean isChangingConfig) {
        view.get().saveViewElements(newsList);
        view = null;
        if(!isChangingConfig) {
            model.onDestroy();
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void reloadSavedElements(Object element) {
        newsList = (NewsList) element;
        view.get().updateList(newsList.getNewsList());
    }

    @Override
    public void openRecycle() {
        view.get().showLoad();
        model.getNews();
    }

    @Override
    public void swipeToRefresh() {
        model.getNews();
    }

    @Override
    public void endingRecycle() {
        view.get().showListLoad();
        model.getNews(newsList.getNewsAfter());
    }

    @Override
    public void clickOnItemRecycle(Object o) {
        if(o instanceof NewsData){
            view.get().goToScreen(NewsActivity.class, ((NewsData) o));
        }
    }

    /* Event Model Listener */

    @SuppressWarnings("unchecked")
    @Subscribe
    public void onReceiverResponse(ReceiverNews event) {
        newsList = (NewsList) event.element;
        view.get().hideLoad();
        view.get().hideRefresh();
        view.get().updateList(newsList.getNewsList());

    }

    @SuppressWarnings("unchecked")
    @Subscribe
    public void onReceiverResponse(ReceiverAfterNews event){
        newsList = (NewsList) event.element;
        view.get().hideListLoad();
        view.get().addList(newsList.getNewsList());
    }

    @Subscribe
    public void onErrorResponse(ErrorResponse event) {
        view.get().hideLoad();
        view.get().hideRefresh();
        view.get().showAlert(ERROR);
    }
}
