package com.casualapps.mynotes.views.fragments

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.casualapps.mynotes.data.entities.Note
import com.casualapps.mynotes.data.entities.NoteArguments
import com.casualapps.mynotes.compose.layout.H6
import com.casualapps.mynotes.compose.layout.NoteCard
import com.casualapps.mynotes.compose.layout.WithState
import com.casualapps.mynotes.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : FragmentBase() {
    private lateinit var noteArguments: NoteArguments

    private val viewModel: NoteViewModel by viewModels()
    private val animateState = mutableStateOf(2)

    override fun onArgumentsReady(bundle: Bundle) {
        noteArguments = NoteFragmentArgs.fromBundle(bundle).note
    }

    override fun onStop() {
        super.onStop()
        exitFullScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
    }

    override fun initViews() {
        viewModel.setNote(noteArguments.categoryId, noteArguments.note)
        ioScope.launch {
            var plus = true
            while (isActive) {
                delay(32)
                animateState.value = animateState.value + 1 * if (plus) 1 else -1
                plus = !plus
            }
        }
    }

    @Composable
    override fun setContent() {
        val note = viewModel.noteLiveData.observeAsState(initial = Note("", "", "", false, 0, "")).value
        Column {
            H6(text = "Note", modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
            Divider(modifier = Modifier
                .preferredHeight((0.8).dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3F))
            WithState(contentState = viewModel.contentState) {
                NoteCard(title = note.title, contents = note.contents) {

                }
            }
        }
    }
}
