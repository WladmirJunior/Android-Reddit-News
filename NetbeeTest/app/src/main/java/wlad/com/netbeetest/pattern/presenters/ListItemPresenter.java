package wlad.com.netbeetest.pattern.presenters;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import java.lang.ref.WeakReference;
import java.util.List;

import wlad.com.netbeetest.helpers.CustomTabsHelper;
import wlad.com.netbeetest.models.NewsData;
import wlad.com.netbeetest.pattern.contracts.Mvp;
import wlad.com.netbeetest.pattern.models.NewsListModel;

/**
 * Created by wlad on 24/05/17.
 */

public class ListItemPresenter implements Mvp.RequiredPresenterOperations, Mvp.PresenterOperations {

    private WeakReference<Mvp.RequiredViewOperations> view;
    private Mvp.ModelOperations model;
    private List<NewsData> newsList;

    private boolean isChangeConfig;

    public ListItemPresenter(Mvp.RequiredViewOperations view) {
        this.view = new WeakReference<>(view);
        this.model = new NewsListModel(this);
    }

    @Override
    public void onConfigurationChanged(Mvp.RequiredViewOperations view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {
        view = null;
        this.isChangeConfig = isChangingConfig;
        if(!isChangingConfig) model.onDestroy();
    }

    @Override
    public void updateRetainItems(List list) {
        view.get().updateList(list);
    }

    @Override
    public void getItems() {
        view.get().showLoad();
        model.getResponse();
    }

    @Override
    public void updateItems() {
        model.getResponse();
    }

    @Override
    public void getMoreItems() {
        model.getResponse();
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
    public void onReceiverResponse(List list) {
        newsList = list;
        view.get().hideLoad();
        view.get().hideRefresh();
        view.get().updateList(newsList);
    }

    @Override
    public void onError(String error) {
        view.get().showAlert(error);
    }
}
