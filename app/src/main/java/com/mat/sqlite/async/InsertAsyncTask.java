package com.mat.sqlite.async;

import android.os.AsyncTask;
import android.util.Log;

import com.mat.sqlite.models.Note;
import com.mat.sqlite.percistence.NoteDao;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private static final String TAG = "InsertAsyncTask";

    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }


    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread" + Thread.currentThread().getName());
        mNoteDao.insertNotes(notes);
        return null;
    }
}
