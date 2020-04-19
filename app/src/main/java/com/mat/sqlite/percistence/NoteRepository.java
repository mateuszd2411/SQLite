package com.mat.sqlite.percistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mat.sqlite.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){

    }

    public void updateNote(Note note){

    }

    public LiveData<List<Note>> retrieveNotesTask(){

        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(){

    }

}
