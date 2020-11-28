package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class dashboard extends AppCompatActivity {
    private RelativeLayout inv,doc,profile,share,rem,todo;
    private Intent intent;
    private FirebaseAuth fAuth;
    private FirebaseFirestore firebaseFirestore;
    String userID;
    String USERID;



    private void setUI(){
        inv=findViewById(R.id.inv);
        doc=findViewById(R.id.doc);
        profile=findViewById(R.id.profile);
        //share=findViewById(R.id.share);
        rem=findViewById(R.id.rem);
        todo=findViewById(R.id.todo);

        inv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.dashboard.this, com.example.myapp.inventoryMain.class));

            }
        });
        rem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.dashboard.this,remainder.class));

            }
        });
        todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.dashboard.this, com.example.myapp.to_do.class));

            }
        });
        doc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.dashboard.this, com.example.myapp.ListN.class));

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.dashboard.this, com.example.myapp.Profile.class));

            }
        });

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                //Usermailid = value.getString("email");
                USERID = value.getString("userName");

            }
        });

        setUI();


    }
}