package wlad.com.netbeetest.pattern.presenters;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.List;

import wlad.com.netbeetest.events.ErrorResponse;
import wlad.com.netbeetest.events.ReceiverResponse;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.models.NewsList;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.models.NewsListModel;

import static wlad.com.netbeetest.ui.activities.MainActivity.ERROR;

/**
 * Created by wlad on 24/05/17.
 */

public class ListItemModelPresenter implements Mvp.PresenterOperations {

    private WeakReference<Mvp.ViewOperations> view;
    private Mvp.ModelOperations model;
    private NewsList newsList;

    private boolean loadMore;

    public ListItemModelPresenter(Mvp.ViewOperations view) {
        this.view = new WeakReference<>(view);
        this.model = new NewsListModel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onConfigurationChanged(Mvp.ViewOperations view) {
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
    public void reloadSavedElements(List list, Object element) {
        view.get().updateList(list);
        newsList = (NewsList) element;
    }

    @Override
    public void openRecycle() {
        view.get().showLoad();
        model.getResponse();
    }

    @Override
    public void swipeToRefresh() {
        loadMore = false;
        model.getResponse();
    }

    @Override
    public void endingRecycle() {
        loadMore = true;
        model.getResponse(newsList.getNewsAfter());
    }

    @Override
    public void clickOnItemRecycle(Object o) {
        if(o instanceof NewsData){
            view.get().openUrl(((NewsData) o).getUrl());
        }
    }

    /* NewsListModel Operations */

    @SuppressWarnings("unchecked")
    @Subscribe
    public void onReceiverResponse(ReceiverResponse event) {
        newsList = (NewsList) event.element;
        if(!loadMore){
            view.get().hideLoad();
            view.get().hideRefresh();
            view.get().updateList(newsList.getNewsList());
        }
        else {
            view.get().addList(newsList.getNewsList());
        }
    }

    @Subscribe
    public void onErrorResponse(ErrorResponse event) {
        view.get().hideLoad();
        view.get().hideRefresh();
        view.get().showAlert(ERROR);
    }
}
