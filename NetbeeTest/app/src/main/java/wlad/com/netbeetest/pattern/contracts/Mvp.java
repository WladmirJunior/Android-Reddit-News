package wlad.com.netbeetest.pattern.contracts;

import android.content.Context;

import java.util.List;

/**
 * Created by wlad on 24/05/17.
 */

public interface Mvp {

    interface RequiredViewOperations{
        Context getViewContext();
        void showToast(String msg);
        void showAlert(String msg);
        void showLoad();
        void hideLoad();
        void hideRefresh();
        void updateList(List list);
    }

    interface PresenterOperations{
        void onConfigurationChanged(RequiredViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void updateRetainItems(List list);
        void getItems();
        void updateItems();
        void getMoreItems();
        void openItem(Object item);
    }

    interface RequiredPresenterOperations{
        void onReceiverResponse(List list);
        void onError(String error);
    }

    interface ModelOperations{
        void getResponse();
        void onDestroy();
    }
}
