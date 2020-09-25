package com.example.notekeeper

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.*

@RunWith(AndroidJUnit4::class)
class NextThroughNotesTest {
    @Rule @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun nextThroughNotes() {
        onData(allOf(instanceOf(NoteInfo::class.java), equalTo(DataManager.notes[0]))).perform(click())

        for (index in 0..DataManager.notes.lastIndex) {
            val note = DataManager.notes[index]

            onView(withId(R.id.courseListSpinner)).check(matches(withSpinnerText(note.course?.title)))
            onView(withId(R.id.noteTitleText)).check(matches(withText(note.title)))
            onView(withId(R.id.noteBodyText)).check(matches(withText(note.body)))

            if (index != DataManager.notes.lastIndex) {
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click())
            }
        }

        onView(withId(R.id.action_next)).check(matches(not(isEnabled())))
    }
}