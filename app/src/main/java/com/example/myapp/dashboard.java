package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Locale;

public class dashboard extends AppCompatActivity {
    private RelativeLayout inv,doc,profile,share,rem,todo;
    private Intent intent;
    //private FirebaseAuth fAuth;
    private FirebaseFirestore firebaseFirestore;
    String userID;
    String USERID;
    private com.example.myapp.category cat1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private dashAdapter dashAdapter;
    private FirebaseAuth fAuth;
    String email;


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
                startActivity(intent = new Intent(com.example.myapp.dashboard.this,ListR.class));

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
    private void defaultvalues(){
//        categories.add("Groceries");
//        categories.add("Books");
//        categories.add("Misc");
//        images.add("https://i.imgur.com/XhTjOMu.png");
//        images.add("https://i.imgur.com/U9tdGo6_d.webp?maxwidth=760&fidelity=grand");
//        images.add("https://i.imgur.com/53QR2iB_d.webp?maxwidth=760&fidelity=grand");
        cat1 = new com.example.myapp.category();
        cat1.setCategory("Groceries");
        cat1.setImage("https://i.imgur.com/XhTjOMu.png");
        databaseReference.child("Groceries").setValue(cat1);
        cat1 = new com.example.myapp.category();
        cat1.setCategory("Books");
        cat1.setImage("https://i.imgur.com/U9tdGo6_d.webp?maxwidth=760&fidelity=grand");
        databaseReference.child("Books").setValue(cat1);
        cat1 = new com.example.myapp.category();
        cat1.setCategory("Furniture");
        cat1.setImage("https://i.imgur.com/X0qnvfp.png");
        databaseReference.child("Furniture").setValue(cat1);
        cat1 = new com.example.myapp.category();
        cat1.setCategory("Appliances");
        cat1.setImage("https://i.imgur.com/UpUzKwA.png");
        databaseReference.child("Appliances").setValue(cat1);
        cat1 = new com.example.myapp.category();

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
        //fAuth = FirebaseAuth.getInstance();


        email = fAuth.getCurrentUser().getEmail();
        databaseReference=firebaseDatabase.getInstance().getReference().child(email.substring(0,2)).child("inventory");
        defaultvalues();

        setUI();


    }
}