package com.bibliotek.ui.splashscreen;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.bibliotek.R;
import com.bibliotek.ui.buku.addBuku;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddBukuTest {

    @Rule
    public ActivityTestRule<addBuku> mActivityTestRule = new ActivityTestRule<>(addBuku.class);

    @Test
    public void addBukuTest() {
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.edtTambahJudulBuku),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Marmut Merah Jambu"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.edtTambahJudulBuku), withText("Marmut Merah Jambu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(""));

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.edtTambahJudulBuku),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.edtTambahPengarang),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("Raditya Dika"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.edtTambahPengarang), withText("Raditya Dika"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText(""));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.edtTambahPengarang),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.edtTambahPenerbit),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("Bukune"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.edtTambahPenerbit), withText("Bukune"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(replaceText(""));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.edtTambahPenerbit),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.edtTambahJudulBuku),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("Marmut Merah Jambu"), closeSoftKeyboard());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.edtTambahPengarang),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(replaceText("Raditya Dika"), closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.edtTambahPengarang), withText("Raditya Dika"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText12.perform(replaceText(""));

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.edtTambahPengarang),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText13.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.edtTambahPenerbit),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText14.perform(replaceText("Bukune"), closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.edtTambahJudulBuku), withText("Marmut Merah Jambu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText15.perform(replaceText(""));

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.edtTambahJudulBuku),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText16.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.edtTambahPengarang),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText17.perform(replaceText("Raditya Dika"), closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton8.perform(scrollTo(), click());

        ViewInteraction textInputEditText18 = onView(
                allOf(withId(R.id.edtTambahJudulBuku),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judullayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText18.perform(replaceText("Marmut Merah Jambu"), closeSoftKeyboard());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.btnSimpangBuku), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6)));
        materialButton9.perform(scrollTo(), click());
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
