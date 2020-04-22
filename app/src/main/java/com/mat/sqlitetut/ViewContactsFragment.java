package com.mat.sqlitetut;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewContactsFragment extends Fragment {

    private static final String TAG = "ViewContactsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcontacts,container,false);

        //navigation to add contacts fragment
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked fab");
            }
        });

        ImageView ivSearchContact = (ImageView) view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked search");
            }
        });

        return view;
    }
}
