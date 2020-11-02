package com.jh108.notekeeper

import android.arch.lifecycle.ViewModel

class ItemsActivityViewModel : ViewModel() {
    var navDrawerDisplaySelection = R.id.nav_notes
    val maxRecentlyViewedNotes = 5

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
}