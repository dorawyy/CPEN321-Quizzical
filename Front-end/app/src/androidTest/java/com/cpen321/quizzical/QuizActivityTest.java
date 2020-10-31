package com.cpen321.quizzical;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.cpen321.quizzical.quizactivities.QuizActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
    @Rule
    public ActivityTestRule<QuizActivity> activityTestRule = new ActivityTestRule<>(QuizActivity.class);
    private boolean is_instructor = false;

    @Before
    public void setupTest() {
        Activity activity = activityTestRule.getActivity();
        SharedPreferences sp = activity.getSharedPreferences("curr_login_user", Context.MODE_PRIVATE);
        is_instructor = sp.getBoolean("IS_INSTRUCTOR", false);
    }

    @Test
    public void testResponseNotSelected() {
        Espresso.onView(ViewMatchers.withText(R.string.UI_submit)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Please answer the question before submission."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testResponseAllCorrect() {

        for (int i = 0; i < 3; i++) {
            Espresso.onView(ViewMatchers.withId(-100)).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withText(R.string.UI_submit)).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withText(String.format("You are correct. You got %d/%d correct.", i + 1, 3)))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            if (i != 2) {
                Espresso.onView(ViewMatchers.withText(R.string.UI_next)).perform(ViewActions.click());
            } else {
                Espresso.onView(ViewMatchers.withText(R.string.UI_finish)).perform(ViewActions.click());
            }
        }

        Espresso.onView(ViewMatchers.withId(R.id.quiz_finished_page_response))
                .check(ViewAssertions.matches(ViewMatchers.withText("You got 3/3 correct. Congratulations!")));

        if (!is_instructor)
            Espresso.onView(ViewMatchers.withId(R.id.quiz_finished_page_exp_earned))
                    .check(ViewAssertions.matches(ViewMatchers.withText("You got 15 EXP!")));
    }

    @Test
    public void testResponseOneWrong() {

        //first question wrong answer
        Espresso.onView(ViewMatchers.withId(-98)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.UI_submit)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("You are wrong. You got 0/3 correct."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.UI_next)).perform(ViewActions.click());

        for (int i = 0; i < 2; i++) {
            Espresso.onView(ViewMatchers.withId(-100)).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withText(R.string.UI_submit)).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withText(String.format("You are correct. You got %d/%d correct.", i + 1, 3)))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            if (i != 1) {
                Espresso.onView(ViewMatchers.withText(R.string.UI_next)).perform(ViewActions.click());
            } else {
                Espresso.onView(ViewMatchers.withText(R.string.UI_finish)).perform(ViewActions.click());
            }
        }

        Espresso.onView(ViewMatchers.withId(R.id.quiz_finished_page_response))
                .check(ViewAssertions.matches(ViewMatchers.withText("You got 2/3 correct. Great work!")));

    }
}
