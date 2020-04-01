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
public class EggWiseAddEggBatch_Teals1_Test {

    @Rule
    public ActivityTestRule<EggWiseMainActivity> mActivityTestRule = new ActivityTestRule<>(EggWiseMainActivity.class);

    @Test
    public void eggWiseAddEggBatch_Teals1_Test() {
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
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("teal1"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.et_batch_label), withText("teal1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_egg_batch_label),
                                        0),
                                0)));
        appCompatAutoCompleteTextView2.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.et_batch_label), withText("teal1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_egg_batch_label),
                                        0),
                                0)));
        appCompatAutoCompleteTextView3.perform(scrollTo(), replaceText("batchteal1"));

        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                allOf(withId(R.id.et_batch_label), withText("batchteal1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_egg_batch_label),
                                        0),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView4.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Previous month"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.DayPickerView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.DialogViewAnimator")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Previous month"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.DayPickerView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.DialogViewAnimator")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView5 = onView(
                allOf(withId(R.id.et_number_of_eggs),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_number_of_eggs),
                                        0),
                                0)));
        appCompatAutoCompleteTextView5.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb_track_specific_eggs), withText("Track Specific Eggs"),
                        childAtPosition(
                                allOf(withId(R.id.rg_track_weight_loss),
                                        childAtPosition(
                                                withClassName(is("com.google.android.flexbox.FlexboxLayout")),
                                                3)),
                                2)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView6 = onView(
                allOf(withId(R.id.et_species_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_species_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView6.perform(scrollTo(), replaceText("teal"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView7 = onView(
                allOf(withId(R.id.et_common_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_common_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView7.perform(scrollTo(), replaceText("teal"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView8 = onView(
                allOf(withId(R.id.et_incubator_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_incubator_name),
                                        0),
                                0)));
        appCompatAutoCompleteTextView8.perform(scrollTo(), replaceText("Advance"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView9 = onView(
                allOf(withId(R.id.et_incubator_settings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_incubator_settings),
                                        0),
                                0)));
        appCompatAutoCompleteTextView9.perform(scrollTo(), replaceText("wet"), closeSoftKeyboard());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatTextView2.perform(click());

        ViewInteraction appCompatAutoCompleteTextView10 = onView(
                allOf(withId(R.id.et_temperature),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_temperature),
                                        0),
                                0)));
        appCompatAutoCompleteTextView10.perform(scrollTo(), replaceText("37.6"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView11 = onView(
                allOf(withId(R.id.et_location),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_location),
                                        0),
                                0)));
        appCompatAutoCompleteTextView11.perform(scrollTo(), replaceText("Yard"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView12 = onView(
                allOf(withId(R.id.et_incubation_days),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_incubation_days),
                                        0),
                                0)));
        appCompatAutoCompleteTextView12.perform(scrollTo(), replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView13 = onView(
                allOf(withId(R.id.et_target_weight_loss),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_target_weight_loss),
                                        0),
                                0)));
        appCompatAutoCompleteTextView13.perform(scrollTo(), replaceText("13"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView14 = onView(
                allOf(withId(R.id.et_number_of_eggs_hatched_rl),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_number_of_eggs_hatched_rl),
                                        0),
                                0)));
        appCompatAutoCompleteTextView14.perform(scrollTo(), replaceText("0"), closeSoftKeyboard());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab_add_save_egg_batch),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());
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
