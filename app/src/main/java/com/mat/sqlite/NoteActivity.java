package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mat.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private static final String TAG = "selected";

    // ui component
    private LineEditText mLineEditText;
    private EditText mEditText;
    private TextView mTextView;

    // vars
    private boolean mIsNewNote;
    private Note mInitialNote;
    private GestureDetector mGestureDetector;

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

        setListeners();

    }

    private void setListeners(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        return mGestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: double");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
}
