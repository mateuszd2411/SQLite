package com.mat.sqlitetut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.mat.sqlitetut.Utils.UniversalImageLoader;
import com.mat.sqlitetut.models.Contact;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements ViewContactsFragment.OnContactSelectedListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 1;

    @Override
    public void OnContactSelected(Contact contact) {
        Log.d(TAG, "OnContactSelected: contact selected from "
                + getString(R.string.view_contacts_fragment)
                + " " + contact.getName());

        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact),contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(getString(R.string.contact_fragment));
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        InitImageLoader();

        init();
    }
    /**
     * initialize the first fragment
    */

    private void init(){
        ViewContactsFragment fragment = new ViewContactsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //replace whatever is in the fragment container view with this fragment,
        // and the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void InitImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    // Generalized method for asking permission. Can pass any array of permissions
    public void veryfyPermisions(String[] permisions){
        Log.d(TAG, "veryfyPermisions: asking user for permissions");
        ActivityCompat.requestPermissions(
                MainActivity.this,
                permisions,
                REQUEST_CODE
        );
    }

    // Check to see if permission was granted for the passed parameters
    // ONLY ONE PERMISSION MAY TO BE CHECKED AD A TIME
    public boolean checkPermission(String[] permission){
        Log.d(TAG, "checkPermission: checking permissions for: " + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(
                MainActivity.this,
                permission[0]);

        if (permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: \n Permission was not granted for " + permission[0]);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch (requestCode){
            case REQUEST_CODE:
                for (int i = 0; i < permissions.length; i++){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[i]);
                    }else {
                        break;
                    }
                }
                break;
        }
    }
}
