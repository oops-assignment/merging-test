package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    private LinearLayout logout,share;
    private Intent Intent;
    private TextView name,email,phno,prof;

    private FirebaseAuth fAuth;
    private FirebaseFirestore firebaseFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.pname);
        phno=findViewById(R.id.pno);
        email=findViewById(R.id.pemail);
        prof=findViewById(R.id.ppr);
        logout=findViewById(R.id.btnlogout);
        share=findViewById(R.id.pshare);


        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                email.setText(value.getString("email"));
                name.setText(value.getString("userName"));
                phno.setText(value.getString("PhoneNumber"));
                prof.setText(value.getString("Profession"));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, com.example.myapp.logout.class));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent= new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody="url";
                myIntent.putExtra(Intent.EXTRA_TEXT,"Url: "+shareBody+"\n");
                startActivity(Intent.createChooser(myIntent,"Share using"));
                Log.d("share","clicked");
            }
        });









    }
}