package wlad.com.netbeetest.pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class StateMaintainer {

    protected final String TAG = getClass().getSimpleName();

    private final String stateMaintenerTag;
    private final WeakReference<FragmentManager> fragmentManager;
    private StateManagerFragment stateMaintainerFrag;

    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTAG) {
        this.fragmentManager = new WeakReference<>(fragmentManager);
        stateMaintenerTag = stateMaintainerTAG;
    }

    public boolean firstTimeIn() {
        try {
            stateMaintainerFrag = (StateManagerFragment) fragmentManager.get().findFragmentByTag(stateMaintenerTag);

            if (stateMaintainerFrag == null) {
                stateMaintainerFrag = new StateManagerFragment();
                fragmentManager.get()
                        .beginTransaction()
                        .add(stateMaintainerFrag, stateMaintenerTag)
                        .commit();
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "Error firstTimeIn");
            return false;
        }
    }

    public void put(String key, Object obj) {
        stateMaintainerFrag.put(key, obj);
    }

    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key)  {
        return stateMaintainerFrag.get(key);

    }

    public boolean hasKey(String key) {
        return stateMaintainerFrag.get(key) != null;
    }

    public static class StateManagerFragment extends Fragment {

        private HashMap<String, Object> map = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void put(String key, Object obj) {
            map.put(key, obj);
        }

        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) map.get(key);
        }
    }

}