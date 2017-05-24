package wlad.com.netbeetest.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.databinding.ActivityMainBinding;
import wlad.com.netbeetest.ui.fragments.ListItemFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment(ListItemFragment.newInstance());
    }

    void initFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
