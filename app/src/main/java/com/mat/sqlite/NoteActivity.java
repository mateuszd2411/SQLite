package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.mat.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "selected";

    // ui component
    private LineEditText mLineEditText;
    private EditText mEditText;
    private TextView mTextView;

    // vars
    private boolean mIsNewNote;
    private Note mInitialNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLineEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_title);
        mTextView = findViewById(R.id.note_text_title);

        if (getIncomingIntent()){
            // this is a new note, (EDIT MODE)
            setNewNoteProperties();

        }else {
            // this is NOT a new note, (VIEW MODE)
            setNoteProperties();
        }

    }


    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")){
            mInitialNote = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "getIncomingIntent: " + mInitialNote.toString());

            mIsNewNote = false;
            return false;
        }
        mIsNewNote = true;
        return true;
    }

    private void setNoteProperties() {
        mTextView.setText(mInitialNote.getTitle());
        mEditText.setText(mInitialNote.getTitle());
        mLineEditText.setText(mInitialNote.getContent());
    }

    private void setNewNoteProperties(){
        mTextView.setText("Note Title");
        mEditText.setText("Note Title");
    }
}
