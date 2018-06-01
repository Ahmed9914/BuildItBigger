package nanodegree.udacity.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import static android.support.test.espresso.Espresso.onView;

import com.udacity.gradle.builditbigger.EspressoIdlingResouce;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.MainActivityFragment;
import com.udacity.gradle.builditbigger.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nanodegree.udacity.jokedisplaylibrary.JokeDisplayActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.intent.Intents.intended;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskConnectedTested {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        Fragment fragment = new MainActivityFragment();
        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().attach(fragment);
        IdlingRegistry.getInstance().register(EspressoIdlingResouce.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResouce.getIdlingResource());
    }

    @Test
    public void testFetchedJoke() {
        onView(withId(R.id.joke_button)).perform(click());

        onView(withId(R.id.joke_tv)).check(matches(not(withText(""))));
    }

}
