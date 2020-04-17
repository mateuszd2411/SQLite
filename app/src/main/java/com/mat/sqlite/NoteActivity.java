package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mat.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {

    private static final String TAG = "selected";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // ui component
    private LineEditText mLineEditText;
    private EditText mEditText;
    private TextView mTextView;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;

    // vars
    private boolean mIsNewNote;
    private Note mInitialNote;
    private GestureDetector mGestureDetector;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLineEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_title);
        mTextView = findViewById(R.id.note_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        if (getIncomingIntent()){
            // this is a new note, (EDIT MODE)
            setNewNoteProperties();
            enableEditMode();

        }else {
            // this is NOT a new note, (VIEW MODE)
            setNoteProperties();
        }

        setListeners();

    }

    private void setListeners(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mTextView.setOnClickListener(this);
        mCheck.setOnClickListener(this);
    }


    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")){
            mInitialNote = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "getIncomingIntent: " + mInitialNote.toString());

            mMode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mTextView.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;
    }

    private void disableEditMode(){
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mTextView.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
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
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.toolbar_check:{
                disableEditMode();
                break;
            }

            case R.id.note_text_title:{
                enableEditMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;
            }

        }
    }
}
