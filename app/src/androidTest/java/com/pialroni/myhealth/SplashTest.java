package com.pialroni.myhealth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SplashTest {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Rule
    public ActivityScenarioRule<Splash> activityRule =
            new ActivityScenarioRule<>(Splash.class);


    @Test
    public void toLoginTest() {
        if(firebaseAuth.getCurrentUser()!=null)
        {
            onView(allOf(withId(R.id.textView), withText("CheckHealth")));
            return;
        }
        onView(withId(R.id.login_splash))
                .perform(click());

        onView(allOf(withId(R.id.textView), withText("Login")));
    }

    @Test
    public void toRegisterTest() {
        if(firebaseAuth.getCurrentUser()!=null)
        {
            onView(allOf(withId(R.id.textView), withText("CheckHealth")));
            return;
        }
        onView(withId(R.id.register_splash))
                .perform(click());

        onView(allOf(withId(R.id.textView), withText("Register")));
    }

}