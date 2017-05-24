package wlad.com.netbeetest.pattern.presenters;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import java.lang.ref.WeakReference;
import java.util.List;

import wlad.com.netbeetest.helpers.CustomTabsHelper;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.models.NewsList;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.models.NewsListModel;

/**
 * Created by wlad on 24/05/17.
 */

public class ListItemModelPresenter implements Mvp.RequiredModelPresenterOperations, Mvp.PresenterOperations {

    private WeakReference<Mvp.ViewOperations> view;
    private Mvp.ModelOperations model;
    private NewsList newsList;

    private boolean loadMore;
    private boolean isChangeConfig;

    public ListItemModelPresenter(Mvp.ViewOperations view) {
        this.view = new WeakReference<>(view);
        this.model = new NewsListModel(this);
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
        this.isChangeConfig = isChangingConfig;
        if(!isChangingConfig) model.onDestroy();
    }

    @Override
    public void updateRetainItems(List list, Object element) {
        view.get().updateList(list);
        newsList = (NewsList) element;
    }

    @Override
    public void getItems() {
        view.get().showLoad();
        model.getResponse();
    }

    @Override
    public void updateItems() {
        loadMore = false;
        model.getResponse();
    }

    @Override
    public void getMoreItems() {
        loadMore = true;
        model.getResponse(newsList.getNewsAfter());
    }

    @Override
    public void openItem(Object o) {
        if(o instanceof NewsData){
            NewsData newsData = (NewsData) o;
            CustomTabsIntent customTabsIntent = CustomTabsHelper.getInstance(view.get().getViewContext());
            customTabsIntent.launchUrl(view.get().getViewContext(), Uri.parse(newsData.getUrl()));
        }
    }

    /* NewsListModel Operations */

    @Override
    @SuppressWarnings("unchecked")
    public void onReceiverResponse(Object element) {
        newsList = (NewsList) element;
        if(!loadMore){
            view.get().hideLoad();
            view.get().hideRefresh();
            view.get().updateList(newsList.getNewsList());
        }
        else {
            view.get().addList(newsList.getNewsList());
        }
    }

    @Override
    public void onError(String error) {
        view.get().showAlert(error);
    }
}
