package wlad.com.netbeetest.ui.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import wlad.com.netbeetest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by wlad on 25/05/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recycleTesteCount() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(20));
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(20, click()));

        Assert.assertNotNull(withId(R.id.recycler_view));
        Espresso.pressBack();
    }

    @Test
    public void openUrl() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.card)).perform(click());
        Espresso.pressBack();
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

}