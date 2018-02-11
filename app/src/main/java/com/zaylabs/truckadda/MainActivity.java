package com.zaylabs.truckadda;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;
    //FirebaseUI
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //FirebaseUI
           mFirebaseAuth = FirebaseAuth.getInstance();

        //End of on Create
       mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    //user signed in
                    Toast.makeText(MainActivity.this,"You Are Signed in",Toast.LENGTH_SHORT).show();
                }  else {
                    //user signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("pk").build()
                                    ))
                                    .setTheme(R.style.GreenTheme).build(),
                            RC_SIGN_IN);
                }

           }
        };
    }

    @Override
            protected void onPause(){
                super.onPause();
                //FirebaseUI
                  mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
            protected void onResume(){
                super.onResume();
                //FirebaseUI
                  mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}

