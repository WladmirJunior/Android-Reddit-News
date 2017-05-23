package wlad.com.netbeetest.ui.activities;

import android.os.Bundle;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.ui.fragments.ListItemFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment(ListItemFragment.newInstance());
    }
}
