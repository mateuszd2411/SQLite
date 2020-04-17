package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mat.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getIntent().hasExtra("selected_note")){
            Note note = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: selected");
        }

        //View

        //Edit


    }
}
