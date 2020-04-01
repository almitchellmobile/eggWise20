package net.almitchellmobile.eggwise20;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EggWiseMainActivity_AddBatchTest2 {

    @Rule
    public ActivityTestRule<EggWiseMainActivity> mActivityTestRule = new ActivityTestRule<>(EggWiseMainActivity.class);

    @Test
    public void eggWiseMainActivity_AddBatchTest2() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_egg_wise_main),
                                        0),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Egg Batch Management"),
                        childAtPosition(
                                allOf(withId(R.id.content),
                                        childAtPosition(
                                                withClassName(is("androidx.appcompat.view.menu.ListMenuItemView")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_egg_batch_list),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.et_batch_label),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_egg_batch_label),
                                        0),
                                0)));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("b2"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.et_number_of_eggs),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_number_of_eggs),
                                        0),
                                0)));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.et_species_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_species_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView3.perform(scrollTo(), replaceText("ringet"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                allOf(withId(R.id.et_species_name), withText("ringet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_species_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView4.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView5 = onView(
                allOf(withId(R.id.et_species_name), withText("ringet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_species_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView5.perform(scrollTo(), replaceText("ringed teals"));

        ViewInteraction appCompatAutoCompleteTextView6 = onView(
                allOf(withId(R.id.et_species_name), withText("ringed teals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_species_name),
                                        0),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView6.perform(closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView7 = onView(
                allOf(withId(R.id.et_common_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_common_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView7.perform(scrollTo(), replaceText("ringte"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView8 = onView(
                allOf(withId(R.id.et_common_name), withText("ringte"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_common_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView8.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView9 = onView(
                allOf(withId(R.id.et_common_name), withText("ringte"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_common_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView9.perform(scrollTo(), replaceText("ringted teals"));

        ViewInteraction appCompatAutoCompleteTextView10 = onView(
                allOf(withId(R.id.et_common_name), withText("ringted teals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_common_name),
                                        0),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView10.perform(closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView11 = onView(
                allOf(withId(R.id.et_incubator_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_incubator_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView11.perform(scrollTo(), replaceText("Advance"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView12 = onView(
                allOf(withId(R.id.et_incubator_settings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_incubator_settings),
                                        0),
                                0)));
        appCompatAutoCompleteTextView12.perform(scrollTo(), replaceText("h"), closeSoftKeyboard());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatTextView2.perform(click());

        ViewInteraction appCompatAutoCompleteTextView13 = onView(
                allOf(withId(R.id.et_temperature),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_temperature),
                                        0),
                                0)));
        appCompatAutoCompleteTextView13.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView14 = onView(
                allOf(withId(R.id.et_temperature),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_temperature),
                                        0),
                                0)));
        appCompatAutoCompleteTextView14.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView15 = onView(
                allOf(withId(R.id.et_temperature),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_temperature),
                                        0),
                                0)));
        appCompatAutoCompleteTextView15.perform(scrollTo(), replaceText("37.6"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView16 = onView(
                allOf(withId(R.id.et_location),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_location),
                                        0),
                                0)));
        appCompatAutoCompleteTextView16.perform(scrollTo(), replaceText("Yard"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView17 = onView(
                allOf(withId(R.id.et_incubation_days),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_incubation_days),
                                        0),
                                0)));
        appCompatAutoCompleteTextView17.perform(scrollTo(), replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView18 = onView(
                allOf(withId(R.id.et_target_weight_loss),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_target_weight_loss),
                                        0),
                                0)));
        appCompatAutoCompleteTextView18.perform(scrollTo(), replaceText("13"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView19 = onView(
                allOf(withId(R.id.et_number_of_eggs_hatched_rl),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_number_of_eggs_hatched_rl),
                                        0),
                                0)));
        appCompatAutoCompleteTextView19.perform(scrollTo(), replaceText("0"), closeSoftKeyboard());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab_add_save_egg_batch),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.ll_egg_batch_list),
                        childAtPosition(
                                allOf(withId(R.id.recycler_view_egg_batch_list),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
