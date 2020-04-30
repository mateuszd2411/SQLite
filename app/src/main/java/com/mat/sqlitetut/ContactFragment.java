package com.mat.sqlitetut;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mat.sqlitetut.Utils.ContactPropertyListAdapter;
import com.mat.sqlitetut.Utils.UniversalImageLoader;
import com.mat.sqlitetut.models.Contact;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactFragment extends Fragment{
    private static final String TAG = "ContactFragment";

    public interface OnEditContactListenner{
        public void onEditContactSelected(Contact contact);
    }

    OnEditContactListenner mOnEditContactListenner;

    //This will evade the nullpointer exception whena adding to a new bundle from MainActivity
    public ContactFragment(){
        super();
        setArguments(new Bundle());
    }

    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.contactToolbar);
        mContactName = (TextView) view.findViewById(R.id.contactName);
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        mListView = (ListView) view.findViewById(R.id.lvContactProperties);
        Log.d(TAG, "onCreateView: started.");
        mContact = getContactFromBundle();

        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();

        //navigation for the backarrow
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                //remove previous fragment from the backstack (therefore navigating back)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // navigate to the edit contact fragment to edit the contact selected
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked the edit icon.");
                mOnEditContactListenner.onEditContactSelected(mContact);

            }
        });

        return view;
    }

    private void init(){
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "");

        ArrayList<String> properties = new ArrayList<>();
        properties.add(mContact.getPhoneNumber());
        properties.add(mContact.getEmail());
        ContactPropertyListAdapter adapter = new ContactPropertyListAdapter(getActivity(), R.layout.layout_cardview, properties);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting contact.");
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    Retries the selected contact from the bundle(coming from MainActivity
     */
    private Contact getContactFromBundle(){
        Log.d(TAG, "getContactFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable(getString(R.string.contact));
        }else{
            return null;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mOnEditContactListenner = (OnEditContactListenner) getActivity();
        }catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}





