package wlad.com.netbeetest.helpers;

import android.content.Context;
import android.support.customtabs.CustomTabsIntent;

import wlad.com.netbeetest.R;

/**
 * Created by wlad on 23/05/17.
 */

public class CustomTabsHelper {

    private static CustomTabsIntent customTabsIntent;

    public static CustomTabsIntent getInstance(Context context){
        if(customTabsIntent==null){
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
            builder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
            builder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            customTabsIntent = builder.build();
        }
        return customTabsIntent;
    }
}
