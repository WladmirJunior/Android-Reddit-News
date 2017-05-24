package wlad.com.netbeetest.pattern;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import java.lang.ref.WeakReference;
import java.util.List;

import wlad.com.netbeetest.helpers.CustomTabsHelper;
import wlad.com.netbeetest.models.NewsData;

/**
 * Created by wlad on 24/05/17.
 */

public class ListItemPresenter implements Mvp.RequiredPresenterOperations, Mvp.PresenterOperations {

    private WeakReference<Mvp.RequiredViewOperations> view;
    private Mvp.ModelOperations model;

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
            CustomTabsIntent customTabsIntent = CustomTabsHelper.getInstance(view.get().getContext());
            customTabsIntent.launchUrl(view.get().getContext(), Uri.parse(newsData.getUrl()));
        }
    }

    /***
     * NewsListModel Operations
     */
    @Override
    public void onReceiverResponse(List list) {
        view.get().hideLoad();
        view.get().hideRefresh();
        view.get().updateList(list);
    }

    @Override
    public void onError(String error) {
        view.get().showAlert(error);
    }
}
