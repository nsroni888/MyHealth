package com.pialroni.myhealth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityScenarioRule<Login> activityRule =
            new ActivityScenarioRule<>(Login.class);


    @Before
    public void beforeClass() {

        ActivityScenario<Login> activityScenario = ActivityScenario.launch(Login.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<Login>() {
            @Override
            public void perform(Login activity) {
                mIdlingResource = activity.getIdlingResource();
                // To prove that the test fails, omit this call:
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });

    }

    @Test
    public void testAppName() {
        onView(allOf(withId(R.id.textView), withText("Login")));
    }

    @Test
    public void loginSuccessTest()  {
        onView(allOf(withId(R.id.email_login))).perform(ViewActions.typeText("p@gmail.com"));
        pressBack();
        onView(allOf(withId(R.id.password_login))).perform(ViewActions.typeText("123456"));

        pressBack();

        onView(withId(R.id.loginBtn))
                .perform(click());


        onView(allOf(withId(R.id.textView), withText("CheckHealth"))).check(matches(isDisplayed()));

    }

    @Test
    public void loginFailedTest() {
        onView(allOf(withId(R.id.email_login))).perform(ViewActions.typeText("p@gmail.com"));
        pressBack();
        // -- password is given wrong -- //
        onView(allOf(withId(R.id.password_login))).perform(ViewActions.typeText("1234567"));
        pressBack();

        onView(withId(R.id.loginBtn))
                .perform(click());

        onView(allOf(withId(R.id.textView), withText("Login"))).check(matches(isDisplayed()));


    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }


}