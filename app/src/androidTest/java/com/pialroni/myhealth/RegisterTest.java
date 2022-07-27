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
public class RegisterTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityScenarioRule<Register> activityRule =
            new ActivityScenarioRule<>(Register.class);


    @Before
    public void beforeClass() {

        ActivityScenario<Register> activityScenario = ActivityScenario.launch(Register.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<Register>() {
            @Override
            public void perform(Register activity) {
                mIdlingResource = activity.getIdlingResource();
                // To prove that the test fails, omit this call:
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });

    }

    @Test
    public void testAppName() {
        onView(allOf(withId(R.id.textView), withText("Register"))).check(matches(isDisplayed()));
    }

    @Test
    public void RegisterSuccessTest()  {
        onView(allOf(withId(R.id.email_register))).perform(ViewActions.typeText("p@gmail.com"));
        pressBack();
        onView(allOf(withId(R.id.password_register))).perform(ViewActions.typeText("123456"));
        pressBack();
        onView(allOf(withId(R.id.confirm_password_register))).perform(ViewActions.typeText("123456"));
        pressBack();

        onView(withId(R.id.signUpbtn))
                .perform(click());


        onView(allOf(withId(R.id.textView), withText("CheckHealth"))).check(matches(isDisplayed()));

    }
    @Test
    public void RegisterSuccessOrFailedWithSameEmailTest()  {
        onView(allOf(withId(R.id.email_register))).perform(ViewActions.typeText("p@gmail.com"));
        pressBack();
        onView(allOf(withId(R.id.password_register))).perform(ViewActions.typeText("123456"));
        pressBack();
        onView(allOf(withId(R.id.confirm_password_register))).perform(ViewActions.typeText("123456"));
        pressBack();

        onView(withId(R.id.signUpbtn))
                .perform(click());


        onView(allOf(withId(R.id.textView), withText("Register"))).check(matches(isDisplayed()));

    }

    @Test
    public void RegisterFailedTest() {

        onView(allOf(withId(R.id.email_register))).perform(ViewActions.typeText("test3@gmail.com"));
        pressBack();
        onView(allOf(withId(R.id.password_register))).perform(ViewActions.typeText("123456"));
        pressBack();
        // -- confirm password wrong -- //
        onView(allOf(withId(R.id.confirm_password_register))).perform(ViewActions.typeText("123456789"));
        pressBack();

        onView(withId(R.id.signUpbtn))
                .perform(click());

        onView(allOf(withId(R.id.textView), withText("Register"))).check(matches(isDisplayed()));


    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}