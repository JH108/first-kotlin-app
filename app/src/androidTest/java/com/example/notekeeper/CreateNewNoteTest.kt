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

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest {
    @Rule @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun createNewNote() {
        val course = DataManager.courses["android_async"]
        val noteTitle = "Title from test!"
        val noteText = "The body of our test text stuff!!"

        onView(withId(R.id.buttonGotoAddNote)).perform(click())

        onView(withId(R.id.courseListSpinner)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())

        onView(withId(R.id.noteTitleText)).perform(typeText(noteTitle))
        onView(withId(R.id.noteBodyText)).perform(typeText(noteText), closeSoftKeyboard())

        pressBack()

        val newNote = DataManager.notes.last()

        assertEquals(newNote.course, course)
        assertEquals(newNote.title, noteTitle)
        assertEquals(newNote.body, noteText)
    }
}