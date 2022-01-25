package com.techand.sampletest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.techand.sampletest.di.idlingResource
import com.techand.sampletest.ui.MainActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserInfoTest : BaseUiTest() {

    @Test
    fun displayScreenTitle() {
        assertDisplayed(R.string.user_info)
    }

    @Test
    fun displayAlbumList() {

        assertRecyclerViewItemCount(R.id.recyclerlist, 10)

        onView(allOf(withId(R.id.user_name), isDescendantOfA(nthChildOf(withId(R.id.recyclerlist), 0)))).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.user_email), isDescendantOfA(nthChildOf(withId(R.id.recyclerlist), 0)))).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.user_phone), isDescendantOfA(nthChildOf(withId(R.id.recyclerlist), 0)))).check(matches(isDisplayed()))
    }

    @Test
    fun displayLoaderWhileFetchingUsers() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.progress_bar)
    }

    @Test
    fun hideLoader() {

        assertNotDisplayed(R.id.progress_bar)
    }

    @Test
    fun navigateToDetailScreen(){

        onView(allOf(withId(R.id.user_name), isDescendantOfA(nthChildOf(withId(R.id.recyclerlist), 0)))).perform(click())
        assertDisplayed(R.id.album_root)

    }
}