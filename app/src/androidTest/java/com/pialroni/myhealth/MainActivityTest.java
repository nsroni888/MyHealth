package com.pialroni.myhealth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
public class MainActivityTest {



    private IdlingResource mIdlingResource;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Before

    public void beforeClass() {


        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                mIdlingResource = activity.getIdlingResource();
                // To prove that the test fails, omit this call:
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });

    }


    /**
     * Main activity app name test
     */

    @Test
    public void testAppName() {
        onView(withText("CheckHealth")).check(matches(isDisplayed())); //Check the name on the screen
    }

    /**
     * floating button click test
     */
    @Test
    public void onClickFloatingButton() {
        onView(withId(R.id.floating_id)).perform(click());
        onView(allOf(withId(R.id.insetDataText), withText("Insert Data")));
    }

    @Test
    public void UpdateTest() {

        onView(withText("CheckHealth")).check(matches(isDisplayed())); //Check the name on the screen

        onView(withId(R.id.user_data_recycler)).perform(actionOnItemAtPosition(0, click()));

        // -- Details Activity -- Update data //
        onView(allOf(withId(R.id.heart_rate_details))).perform(ViewActions.scrollTo()).perform(ViewActions.clearText());
        onView(allOf(withId(R.id.heart_rate_details))).perform(ViewActions.scrollTo()).perform(ViewActions.typeText(String.valueOf(100)));

        pressBack();
        onView(allOf(withId(R.id.systolic_rate_details))).perform(ViewActions.scrollTo()).perform(ViewActions.clearText());
        onView(allOf(withId(R.id.systolic_rate_details))).perform(ViewActions.scrollTo()).perform(ViewActions.typeText(String.valueOf(95)));
        pressBack();
        onView(allOf(withId(R.id.diastolic_rate_details))).perform(ViewActions.scrollTo()).perform(ViewActions.clearText());
        onView(allOf(withId(R.id.diastolic_rate_details))).perform(ViewActions.scrollTo()).perform(ViewActions.typeText(String.valueOf(97)));
        pressBack();

        onView(withId(R.id.edit_save))
                .perform(ViewActions.scrollTo())
                .perform(click());


        onView(withText("CheckHealth")).check(matches(isDisplayed()));


        // -- TO Detail Activity // checking -- //


        onView(withId(R.id.user_data_recycler)).perform(actionOnItemAtPosition(0, click()));


        onView(allOf(withId(R.id.systolic_rate_details), withText("95"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.diastolic_rate_details), withText("97"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.heart_rate_details), withText("100"))).check(matches(isDisplayed()));

    }

    /**
     * Inserting Data and saving Data to database test
     */
    @Test
    public void InsertDataAndCheckInAdapter() {
        onView(withId(R.id.floating_id)).perform(click());
        onView(allOf(withId(R.id.insetDataText), withText("Insert Data")));


        onView(allOf(withId(R.id.date_edit_text_id))).perform(ViewActions.typeText("12/07/22"));
        pressBack();
        onView(allOf(withId(R.id.time_edit_text))).perform(ViewActions.typeText("22:21"));

        pressBack();
        onView(allOf(withId(R.id.systolic_edit_text))).perform(ViewActions.scrollTo()).perform(ViewActions.typeText(String.valueOf(80)));
        pressBack();
        onView(allOf(withId(R.id.diastolic_edit_text))).perform(ViewActions.scrollTo()).perform(ViewActions.typeText(String.valueOf(80)));
        pressBack();
        ViewActions.scrollTo();
        onView(allOf(withId(R.id.heartRate_edit_text))).perform(ViewActions.scrollTo())
                .perform(ViewActions.typeText(String.valueOf(80)));
        pressBack();

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .perform(click());


        onView(withText("CheckHealth")).check(matches(isDisplayed()));


        onView(withId(R.id.user_data_recycler)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(withId(R.id.date_details), withText("12/07/22"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.systolic_rate_details), withText("80"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.diastolic_rate_details), withText("80"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.heart_rate_details), withText("80"))).check(matches(isDisplayed()));

        pressBack();


    }

}