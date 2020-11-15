package com.cpen321.quizzical;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.cpen321.quizzical.data.Classes;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class instructorCreateClassTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private static final String testClassName = "CLASSINTEST";

    @Before
    public void cleanUp() {
        Activity activity = activityTestRule.getActivity();
        SharedPreferences sp = activity.getSharedPreferences(activity.getString(R.string.curr_login_user), Context.MODE_PRIVATE);
        String classListString = sp.getString(activity.getString(R.string.CLASS_LIST), "");
        String[] classes = classListString.split(";");
        for (String c : classes) {
            Classes classes1 = new Classes(c);
            if (classes1.getClassName().equals(testClassName)) {
                deleteClass();
                break;
            }
        }
    }

    private void deleteClass() {
        Espresso.onView(ViewMatchers.withId(R.id.class_switch_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(testClassName))
                .perform(ViewActions.scrollTo(), ViewActions.longClick());

        Espresso.onView(ViewMatchers.withText(R.string.YES))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.class_switch_fab))
                .perform(ViewActions.click());
    }

    @Test
    public void checkClassNameInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.class_switch_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.add_class_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_creating_class_msg))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withHint(R.string.UI_example_class_name))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.typeText("a"));

        Espresso.onView(ViewMatchers.withText(R.string.UI_submit))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_class_name_invalid_msg))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addNewClassTest() {
        createClass();

        deleteClass();
    }

    private void createClass() {
        Espresso.onView(ViewMatchers.withId(R.id.class_switch_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.add_class_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_creating_class_msg))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withHint(R.string.UI_example_class_name))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.typeText(testClassName));

        Espresso.onView(ViewMatchers.withText(R.string.UI_submit))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_create_class_success_title))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(R.string.OK))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.class_switch_fab))
                .perform(ViewActions.click());
    }

    @Test
    public void addDuplicateClassTest() {
        createClass();

        Espresso.onView(ViewMatchers.withId(R.id.class_switch_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.add_class_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_creating_class_msg))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withHint(R.string.UI_example_class_name))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.typeText(testClassName));

        Espresso.onView(ViewMatchers.withText(R.string.UI_submit))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_class_name_invalid_msg))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addQuizNoModuleTest() {
        createClass();
        Espresso.onView(ViewMatchers.withId(R.id.fab))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_create_new_module))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addModuleTest() {
        createClass();
        Espresso.onView(ViewMatchers.withId(R.id.fab))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.module_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_create_new_module))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withHint(R.string.UI_example_module_name))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.typeText("module1"));

        Espresso.onView(ViewMatchers.withText(R.string.UI_submit))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        checkEachQuizModuleButtonNoData(R.string.UI_wrong_questions);
        checkEachQuizModuleButtonNoData(R.string.UI_notes);
        checkEachQuizModuleButtonNoData(R.string.UI_stats);

        testDeleteModule();
    }

    private void checkEachQuizModuleButtonNoData(int textId) {
        Espresso.onView(ViewMatchers.withText(textId))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_no_data))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(R.string.OK))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());
    }

    private void testDeleteModule() {
        Espresso.onView(ViewMatchers.withId(R.id.delete_module_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_warning))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(R.string.NO))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.delete_module_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_warning))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(R.string.YES))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

    }

    @Test
    public void addQuizAfterCreateModule() {
        createClass();
        Espresso.onView(ViewMatchers.withId(R.id.fab))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.module_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_create_new_module))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withHint(R.string.UI_example_module_name))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.typeText("module1"));

        Espresso.onView(ViewMatchers.withText(R.string.UI_submit))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.edit_fab))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(R.string.UI_enter_module_name))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
