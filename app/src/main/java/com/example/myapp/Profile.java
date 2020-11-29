package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FloatingActionButton back;

    private FirebaseAuth fAuth;
    private FirebaseFirestore firebaseFirestore;
    String userID;
    private Intent intent;

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
        back=findViewById(R.id.pback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.Profile.this, com.example.myapp.dashboard.class));

            }
        });


        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, com.example.myapp.login.class));
                Toast.makeText(com.example.myapp.Profile.this, "Logged ot successfully", Toast.LENGTH_SHORT).show();
            }
        });

            DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value.getString("userName") != null){
                        email.setText(value.getString("email"));
                        name.setText(value.getString("userName"));
                        phno.setText(value.getString("PhoneNumber"));
                        prof.setText(value.getString("Profession"));


                    }

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