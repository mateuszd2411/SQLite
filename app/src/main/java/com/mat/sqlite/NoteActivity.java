package com.mat.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mat.sqlite.models.Note;
import com.mat.sqlite.percistence.NoteRepository;
import com.mat.sqlite.util.Utility;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

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
    private NoteRepository mNoterepository;
    private Note mFinalNote;

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

        mNoterepository = new NoteRepository(this);

        if (getIncomingIntent()){
            // this is a new note, (EDIT MODE)
            setNewNoteProperties();
            enableEditMode();

        }else {
            // this is NOT a new note, (VIEW MODE)
            setNoteProperties();
            disableContentInteraction();
        }

        setListeners();

    }

    private void setListeners(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mTextView.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditText.addTextChangedListener(this);
    }


    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")){
            mInitialNote = getIntent().getParcelableExtra("selected_note");
            mFinalNote = getIntent().getParcelableExtra("selected_note");
//            Log.d(TAG, "getIncomingIntent: " + mInitialNote.toString());

            mMode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void saveChanges(){
        if (mIsNewNote){
            saveNewNote();
        }else {

        }
    }

    private void saveNewNote(){
        mNoterepository.insertNoteTask(mFinalNote);
    }

    private void disableContentInteraction(){
        mLineEditText.setKeyListener(null);
        mLineEditText.setFocusable(false);
        mLineEditText.setFocusableInTouchMode(false);
        mLineEditText.setCursorVisible(false);
        mLineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLineEditText.setKeyListener(new EditText(this).getKeyListener());
        mLineEditText.setFocusable(true);
        mLineEditText.setFocusableInTouchMode(true);
        mLineEditText.setCursorVisible(true);
        mLineEditText.requestFocus();
    }

    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mTextView.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode(){
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mTextView.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        String temp = mLineEditText.getText().toString();
        temp = temp.replace("\n","");
        temp = temp.replace(" ","");
        if (temp.length() > 0){
            mFinalNote.setTitle(mEditText.getText().toString());
            mFinalNote.setContent(mLineEditText.getText().toString());
            String timestamp = Utility.getCurrentTimestamp();
            mFinalNote.setTimestamp(timestamp);

            if (!mFinalNote.getContent().equals(mInitialNote.getContent())
                    || !mFinalNote.getTitle().equals(mInitialNote.getTitle())){
                saveChanges();
            }
        }

    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null){
            view = new View(this);
        }
    }

    private void setNoteProperties() {
        mTextView.setText(mInitialNote.getTitle());
        mEditText.setText(mInitialNote.getTitle());
        mLineEditText.setText(mInitialNote.getContent());
    }

    private void setNewNoteProperties(){
        mTextView.setText("Note Title");
        mEditText.setText("Note Title");

        mInitialNote = new Note();
        mFinalNote = new Note();
        mInitialNote.setTitle("Note Title");
        mFinalNote.setTitle("Note Title");
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
                hideSoftKeyboard();
                disableEditMode();
                break;
            }

            case R.id.note_text_title:{
                enableEditMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;
            }

            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED){
            onClick(mCheck);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode",mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if (mMode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mTextView.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
