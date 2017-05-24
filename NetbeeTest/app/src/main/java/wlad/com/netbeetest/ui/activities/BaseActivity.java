package wlad.com.netbeetest.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import wlad.com.netbeetest.R;

/**
 * Created by wlad on 23/05/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected void addToolbar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }
}
