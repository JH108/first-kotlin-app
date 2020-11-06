package com.jh108.notekeeper

import android.arch.lifecycle.ViewModel
import android.os.Bundle

class ItemsActivityViewModel : ViewModel() {
    var isNewlyCreated = true
    var navDrawerDisplaySelection = R.id.nav_notes
    val maxRecentlyViewedNotes = 5
    val navDrawerDisplaySelectionStateName = "com.jh108.notekeeper.ItemsActivityViewModel.navDrawerDisplaySelectionStateName"
    val recentlyViewedNoteIdsName = "com.jh108.notekeeper.ItemsActivityModel.recentlyViewedNoteIdsName"

    val recentlyViewedNotes = ArrayList<NoteInfo>(maxRecentlyViewedNotes)

    fun addToRecentlyViewedNotes(note: NoteInfo) {
        val existingIndex = recentlyViewedNotes.indexOf(note)
        if (existingIndex == -1) {
            recentlyViewedNotes.add(0, note)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
        } else {
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
        }
    }

    fun saveState(outState: Bundle?) {
        outState?.putInt(navDrawerDisplaySelectionStateName, navDrawerDisplaySelection)
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState?.putIntArray(recentlyViewedNoteIdsName, noteIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState?.getInt(navDrawerDisplaySelectionStateName)
        val noteIds = savedInstanceState?.getIntArray(recentlyViewedNoteIdsName)
        val noteList = DataManager.loadNotes(*noteIds)
        recentlyViewedNotes.addAll(noteList)
    }
}