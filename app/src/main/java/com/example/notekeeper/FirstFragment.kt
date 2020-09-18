package com.example.notekeeper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_NOTE_POSITION, notePosition)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterCourses = ArrayAdapter<CourseInfo>(
            activity as Context,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        courseListSpinner.adapter = adapterCourses

        view.findViewById<Button>(R.id.buttonAddNote).setOnClickListener { view ->
            addNote()
            Snackbar.make(view, "Added a note!", Snackbar.LENGTH_LONG).show()
            val activityIntent = Intent(activity as Context, NoteListActivity::class.java)
            startActivity(activityIntent)
        }

        notePosition = savedInstanceState?.getInt(EXTRA_NOTE_POSITION, POSITION_NOT_SET) ?:
            activity!!.intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)


        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            val newNote = NoteInfo()
            DataManager.notes.add(newNote);
            notePosition = DataManager.notes.lastIndex
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu.findItem(R.id.action_next)

            menuItem.setIcon(R.drawable.ic_baseline_stop_24)
            menuItem.isEnabled = false
        } else if (notePosition == 0) {
            val menuItem = menu.findItem(R.id.action_settings)
            menuItem.setIcon(R.drawable.ic_baseline_stop_24)
            menuItem.isEnabled = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                moveBack()
                true
            }
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveBack() {
        if (notePosition > 0)
            --notePosition
            displayNote()
            activity?.invalidateOptionsMenu()

    }

    private fun moveNext() {
        if (notePosition < DataManager.notes.lastIndex)
            ++notePosition
            displayNote()
            activity?.invalidateOptionsMenu()
    }

    private fun addNote() {
        val selectedCourse = courseListSpinner.selectedItem
        val newNote = NoteInfo(
            course = selectedCourse as CourseInfo,
            title = noteTitleText.text.toString(),
            body = noteBodyText.text.toString()
        )

        DataManager.notes.add(newNote)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        noteTitleText.setText(note.title)
        noteBodyText.setText(note.body)

        val coursePosition = DataManager.courses.values.indexOf(note.course)

        courseListSpinner.setSelection(coursePosition)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        val selectedCourse = courseListSpinner.selectedItem

        note.title = noteTitleText.text.toString()
        note.body = noteBodyText.text.toString()
        note.course = selectedCourse as CourseInfo
    }
}