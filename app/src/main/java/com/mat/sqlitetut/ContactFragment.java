package com.mat.sqlitetut;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mat.sqlitetut.Utils.UniversalImageLoader;
import com.mat.sqlitetut.models.Contact;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactFragment extends Fragment {
    private static final String TAG = "ContactFragment";

    // This will evade the nullpointer exception when adding to a new bundle from MainActivity
    public ContactFragment(){
        super();
        setArguments(new Bundle());
    }

    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.contactToolbar);
        mContactName = (TextView) view.findViewById(R.id.contactName);
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        Log.d(TAG, "onCreateView: started");
        mContact = getContactFromBundle();


        // required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();

        ///navigation for the back arrow
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked back arrow");
                //remove previous fragment from the backstack (therefore navigation back)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //navigate to the edit contact fragment to edit the contact selected
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked the edit icon");

            }
        });
        return view;
    }

    private void init(){
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(),mContactImage,null,"https://");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting contact ");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Contact getContactFromBundle(){
        Log.d(TAG, "getContactFromBundle: arguments" + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null){
            return bundle.getParcelable(getString(R.string.contact));
        }else {
            return null;
        }
    }
}
