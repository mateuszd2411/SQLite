package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mat.sqlite.models.Note;

public class NotesListActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Note note = new Note("some title","some cement","timestamp");

        Log.d(TAG,"onCreate: my note:" + note.toString());


    }
}
