package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity6 extends AppCompatActivity {
    // Toolbar elements
    ImageView backButton;

    // View elements
    TextView username;
    TextView bio;
    //Navigation to screen EditUserProfile
    Button navigation;

    // Database elements
    FirebaseDatabase database;
    DatabaseReference userRef;

    // Get current user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings6);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        // Get user id
        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        userRef = database.getReference().child("User").child(user.getUid());

        // variables, which refer to xml layout elements
        username = findViewById(R.id.OutputMailAdress6);
        bio =findViewById(R.id.OutputBiography);
        navigation= findViewById(R.id.ButtonChangeProfil6);

        navigation.setOnClickListener(view -> openProfile());
    }

    /*this will be executed when the Settings become visible again
    DO NOT DELETE, because if you do, the user changes the profile, but when they come back
    to settings the old data is shown even though the data was updated in the database> irritating for the user*/
    protected void onStart(){
        super.onStart();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);

                username.setText(userClass.getUsername());
                bio.setText(userClass.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // Methode to navigate to screen EditUserProfile
    private void openProfile() {
        Intent intent= new Intent(this, EditUserProfile.class);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}